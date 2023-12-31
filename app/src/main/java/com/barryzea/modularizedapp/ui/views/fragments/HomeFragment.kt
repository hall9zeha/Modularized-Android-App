package com.barryzea.modularizedapp.ui.views.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.barryzea.bookmark.ui.view.BottomSheetBookmarks
import com.barryzea.bookmark.ui.viewModel.BookmarkViewModel
import com.barryzea.models.model.Note
import com.barryzea.models.model.NoteAndTag
import com.barryzea.core.R
import com.barryzea.models.model.Tag
import com.barryzea.modularizedapp.databinding.FragmentHomeBinding
import com.barryzea.modularizedapp.ui.adapter.MainAdapter
import com.barryzea.modularizedapp.ui.viewmodel.MainViewModel
import com.barryzea.modularizedapp.ui.views.MainActivity
import com.barryzea.modularizedapp.ui.views.NewRegisterDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private  var _bind:FragmentHomeBinding? = null
    private val viewModel:MainViewModel by viewModels()
    private val bookmarkViewModel:BookmarkViewModel by viewModels()
    private lateinit var adapter: MainAdapter
    private lateinit var staggeredGrid: StaggeredGridLayoutManager
    private lateinit var entity: NoteAndTag
    private var isExpanded = false
    private var idItemSelected:Long?=null
    private val bind:FragmentHomeBinding get() = _bind!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }
    //Para manejar el evento onBackpressed del mainActivity desde aquí, pero ya no es necesario, solo lo mantenemos como ejemplo
    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(isExpanded){
                    bottomSheetBehavior.state=BottomSheetBehavior.STATE_COLLAPSED
                }else{
                    (activity as? MainActivity)?.handleBackPressed()
                }
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(onBackPressedCallback)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let{
            _bind = FragmentHomeBinding.inflate(inflater,container,false)
            _bind?.let{bindView->
                return bindView.root
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpActionBarMenu()
        setUpAdapter()
        setUpViewModel()
        setUpListeners()

    }
    private fun setUpActionBarMenu(){
        val menuHost:MenuHost = requireActivity()
        menuHost.addMenuProvider(object:MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu,menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId){
                    R.id.filterItem->{
                        BottomSheetBookmarks().show(childFragmentManager.beginTransaction(),BottomSheetBookmarks::class.java.simpleName)
                    }
                    R.id.refreshItem->{
                        adapter.clear()
                        viewModel.fetchAllRegisters()
                    }
                }
                return false //al retornar true inhabilitaba el evento del ícono de navegación del toolbar principal
            }
        },viewLifecycleOwner,Lifecycle.State.RESUMED)
    }
    private fun setUpViewModel(){
        viewModel.fetchAllRegisters()

        viewModel.allRegisters.observe(viewLifecycleOwner){
            it?.let {
                adapter.addAll(it!!)
            }
        }
        viewModel.registerId.observe(viewLifecycleOwner){
            it?.let{viewModel.getRegisterById(it!!)}

        }
        viewModel.entity.observe(viewLifecycleOwner){
            it?.let{ adapter.addItem(it)
                //Solo si es un nuevo registro que será diferente del id si este fuera seleccionado al hacer click para abrir su detalle
                //se moverá al primer índice de la lista donde será insertado
                idItemSelected?.let {idItemSelected->
                    if(idItemSelected != it.note.idNote)bind.rvNotes.smoothScrollToPosition(0)
                }
            }
        }
        viewModel.deletedRegisterRow.observe(viewLifecycleOwner){
            it?.let{adapter.removeItem(entity)}
        }
        viewModel.noteAndTagByTagId.observe(viewLifecycleOwner){
            it?.let{
                adapter.addAll(it)
            }
        }
        bookmarkViewModel.bookmarkIdSelectedFilter.observe(viewLifecycleOwner){
            it?.let{
                adapter.clear()
                viewModel.getNoteAndTagByTagId(it)
            }
        }

    }
    private fun setUpAdapter(){
        staggeredGrid = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        adapter = MainAdapter(::onItemClick,::onItemDelete)
        bind.rvNotes.apply {
            setHasFixedSize(true)
            layoutManager=staggeredGrid
            adapter=this@HomeFragment.adapter
        }
        setupScrollListener()
    }
    private fun setupScrollListener(){
        bind.rvNotes.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy>0){
                    if(bind.addFab.isShown)bind.addFab.hide()
                }else if (dy<0){
                    if(!bind.addFab.isShown)bind.addFab.show()
                }
            }
        })
    }
    private fun setUpListeners(){
        bind.addFab.setOnClickListener {
            //Usamos childFragmentManager para obtener el mismo propietario del viewmodel desde el fragmentdialog de detalle
            //y así observar los cambios realizados allí.
            //Si fuera una activity usaríamos supportFragmentManager
            NewRegisterDialog().show(childFragmentManager.beginTransaction(), NewRegisterDialog::class.java.simpleName)
        }
    }
    private fun onItemClick(entity:NoteAndTag){
        idItemSelected=entity.note.idNote
        NewRegisterDialog.newInstance(entity).show(childFragmentManager.beginTransaction(),
            NewRegisterDialog::class.java.simpleName)
    }
    private fun onItemDelete(entity: NoteAndTag){
        this.entity=entity
        viewModel.deleteRegister(entity.note.idNote)
    }

    override fun onResume() {
        super.onResume()
        bookmarkViewModel.fetchAllTags()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}