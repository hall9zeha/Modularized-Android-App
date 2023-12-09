package com.barryzea.bookmark.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.barryzea.bookmark.databinding.FragmentBookmarkBinding

import com.barryzea.bookmark.ui.adapter.BookmarkAdapter
import com.barryzea.bookmark.ui.viewModel.BookmarkViewModel
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
        viewModel.bookmarks.observe(viewLifecycleOwner){
            it?.let {bookmarks->
                bookmarkAdapter.addAll(bookmarks)
            }
        }
        viewModel.tagRowDeleted.observe(viewLifecycleOwner){

        }
    }
    private fun setUpAdapter(){
        bookmarkAdapter = BookmarkAdapter()
        bind.rvBookmark.apply {
            layoutManager = this@BookmarkFragment.layoutManager
            setHasFixedSize(true)
            adapter= bookmarkAdapter
        }
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