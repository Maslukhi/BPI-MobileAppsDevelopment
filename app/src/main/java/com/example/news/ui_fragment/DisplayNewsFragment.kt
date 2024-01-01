package com.example.news.ui_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.news.model.ArticlesItem
import com.example.news.databinding.FragmentDetailNewsBinding

class DisplayNewsFragment : Fragment() {

    lateinit var binding: FragmentDetailNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailNewsBinding.inflate(inflater, container, false)
        displayNews(arguments)
        return binding.root
    }

    private fun displayNews(arguments: Bundle?) {
        if (arguments != null) {
            val news: ArticlesItem = arguments.getSerializable("article") as ArticlesItem
            binding.webView.apply {
                webViewClient = WebViewClient()
                news.url?.let { loadUrl(it) }
            }
        }
    }
}