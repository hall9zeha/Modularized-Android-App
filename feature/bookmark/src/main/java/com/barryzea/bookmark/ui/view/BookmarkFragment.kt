package com.barryzea.bookmark.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.barryzea.bookmark.R
import com.barryzea.core.R as core_res
import com.barryzea.bookmark.databinding.FragmentBookmarkBinding

import com.barryzea.bookmark.ui.adapter.BookmarkAdapter
import com.barryzea.bookmark.ui.viewModel.BookmarkViewModel
import com.barryzea.models.model.Tag
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var _bind: FragmentBookmarkBinding? = null
    private val  viewModel:BookmarkViewModel by viewModels()
    private val bind: FragmentBookmarkBinding get() = _bind!!
    private lateinit var bookmarkAdapter:BookmarkAdapter
    private val layoutManager by lazy { LinearLayoutManager(context,RecyclerView.VERTICAL, false) }
    private var bookmark: Tag?=null
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
            _bind = FragmentBookmarkBinding.inflate(inflater, container, false)
            _bind?.let{
                return it.root
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpAdapter()
        setUpObservers()

    }
    private fun setUpListeners(){
        bind.fabBookmark.setOnClickListener {
            AddBookmarkDialog().show(childFragmentManager.beginTransaction(),AddBookmarkDialog::class.simpleName)
        }
    }
    private fun setUpObservers(){
        viewModel.fetchAllTags()
        viewModel.bookmarks.observe(viewLifecycleOwner){
           it?.let {bookmarks->
                bookmarkAdapter.addAll(bookmarks)
            }
        }
        viewModel.bookmarkById.observe(viewLifecycleOwner){
            it?.let{bookmarkAdapter.add(it!!)}
        }
        viewModel.tagRowDeleted.observe(viewLifecycleOwner){
            it?.let{bookmarkAdapter.remove(bookmark!!)}
        }
    }
    private fun setUpAdapter(){
        bookmarkAdapter = BookmarkAdapter(::removeItem,::onClickItem)
        bind.rvBookmark.apply {
            layoutManager = this@BookmarkFragment.layoutManager
            setHasFixedSize(true)
            adapter= bookmarkAdapter
        }
    }
    private fun removeItem(bookmark:Tag){
        viewModel.deleteTag(bookmark)
        this@BookmarkFragment.bookmark = bookmark
    }
    private fun onClickItem(){
        Snackbar.make(bind.root, getString(core_res.string.long_press_msg),Snackbar.LENGTH_SHORT).show()
    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookmarkFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}