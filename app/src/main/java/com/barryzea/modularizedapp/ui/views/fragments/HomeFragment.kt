package com.barryzea.modularizedapp.ui.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.barryzea.models.model.Note
import com.barryzea.models.model.NoteAndTag
import com.barryzea.modularizedapp.databinding.FragmentHomeBinding
import com.barryzea.modularizedapp.ui.adapter.MainAdapter
import com.barryzea.modularizedapp.ui.viewmodel.MainViewModel
import com.barryzea.modularizedapp.ui.views.NewRegisterDialog
import dagger.hilt.android.AndroidEntryPoint


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private  var _bind:FragmentHomeBinding? = null
    private val viewModel:MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter
    private lateinit var staggeredGrid: StaggeredGridLayoutManager
    private lateinit var entity: NoteAndTag
    private val bind:FragmentHomeBinding get() = _bind!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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
        setUpAdapter()
        setUpViewModel()
        setUpListeners()
    }
    private fun setUpViewModel(){
        viewModel.fetchAllRegisters()
        viewModel.allRegisters.observe(viewLifecycleOwner){
            it?.let {
                Log.e("TAG", it.toString())
                adapter.addAll(it!!)
                if(it.isNotEmpty()) {
                    Log.e("TAGS", it[0].tags[0].description)
                }
            }
        }
        viewModel.registerId.observe(viewLifecycleOwner){
            viewModel.getRegisterById(it!!)
        }
        viewModel.entity.observe(viewLifecycleOwner){
            adapter.addItem(it!!)
        }
        viewModel.deletedRegisterRow.observe(viewLifecycleOwner){
            adapter.removeItem(entity)
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
        NewRegisterDialog.newInstance(entity).show(childFragmentManager.beginTransaction(),
            NewRegisterDialog::class.java.simpleName)
    }
    private fun onItemDelete(entity: NoteAndTag){
        this.entity=entity
        viewModel.deleteRegister(entity.note.idNote)
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