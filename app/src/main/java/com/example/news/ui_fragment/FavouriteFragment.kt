package com.example.news.ui_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.*
import com.example.news.databinding.FragmentBookmarkBinding

class FavouriteFragment : Fragment() {
    lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentBookmarkBinding =
            FragmentBookmarkBinding.inflate(inflater, container, false)
        newsViewModel = (activity as MainActivity).newsViewModel
        val newsAdapter = NewsAdapter()
        newsViewModel.articleMutableLiveData.observe(viewLifecycleOwner) {
            newsAdapter.differ.submitList(it)
        }
        handleClicks(newsAdapter)
        newsViewModel.getSavedArticle()
        binding.favRecycler.adapter = newsAdapter
        binding.favRecycler.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    private fun handleClicks(newsAdapter: NewsAdapter) {
        newsAdapter.setOnShowMoreListener {
            val displayFragment = DisplayNewsFragment()
            val bundle = Bundle()
            bundle.putSerializable("article", it)
            displayFragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()
                ?.addToBackStack("")
                ?.replace(R.id.main_FrameL, displayFragment)?.commit()
        }

        newsAdapter.onSaveNewsClickListener {
            context?.let { it1 -> newsViewModel.insertArticle(it) }
        }
        newsAdapter.onDeleteClickListener {
            it.url?.let { it1 -> newsViewModel.deleteArticle(it1) }
        }


    }
}