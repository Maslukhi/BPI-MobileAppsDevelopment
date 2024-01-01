package com.example.news.ui_fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.*
import com.example.news.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    lateinit var fragmentName: String
    lateinit var binding: FragmentHomeBinding
    var pageNo: Int = 0
    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var newsAdapter2: NewsAdapter2

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.shimmerLayout.startShimmer()
        binding.shimmerLayout2.startShimmer()
        fragmentName =
            arguments?.getString("fragmentName", resources.getString(R.string.discover)).toString()
        pageNo = arguments?.getInt("sortedBy", 0) ?: 0
        loadNews()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun loadNews() {
        newsViewModel = (activity as MainActivity).newsViewModel
        newsAdapter = NewsAdapter()
        newsAdapter2 = NewsAdapter2()
        binding.newsFragmentRecycler.layoutManager = LinearLayoutManager(context)
        binding.newsFragmentRecycler.adapter = newsAdapter
        binding.rv2.layoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)
        binding.rv2.adapter = newsAdapter2

        newsViewModel.mutableLiveData.observe(viewLifecycleOwner) { t ->
            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout2.stopShimmer()
            binding.shimmerLayout.visibility = View.GONE
            binding.shimmerLayout2.visibility = View.GONE
            binding.newsFragmentRecycler.visibility = View.VISIBLE
            binding.rv2.visibility = View.VISIBLE
            GlobalScope.launch(Dispatchers.IO) {
                val db = newsViewModel.newsRepository.getSavedArticle()
                here@ for (dArticle in db) {
                    for (aArticle in t.articles) {
                        Log.d("compare ", "${dArticle.url}  || ${aArticle.url} ")
                        if (dArticle.url == aArticle.url) {
                            aArticle.isFavourite = true
                            continue@here
                        }
                    }
                }
            }
            if (t.articles.size > 0) {
                newsAdapter.differ.submitList(t.articles)
                newsAdapter2.differ.submitList(t.articles)
                binding.newsFragmentRecycler.setItemViewCacheSize(t.articles.size)
            }
        }

        when (fragmentName) {
            resources.getString(R.string.discover) -> {
                binding.categoryText.visibility = View.GONE
                if (context?.let { isOnline(it) } == true) {
                        newsViewModel.getNews("us", pageNo + 1)
                } else {
                    Toast.makeText(
                        context,
                        resources.getString(R.string.no_internet_connection),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        newsViewModel.getSavedArticle()
        handleClicks(newsAdapter)
        handleClicks(newsAdapter2)
    }

    private fun handleClicks(newsAdapter: NewsAdapter2) {
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