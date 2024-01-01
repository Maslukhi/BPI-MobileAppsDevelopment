package com.example.news

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.ListItemBinding
import com.example.news.model.ArticlesItem

class NewsAdapter :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(var view: ListItemBinding) : RecyclerView.ViewHolder(view.root)

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ListItemBinding>(
            inflater,
            R.layout.list_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news: ArticlesItem = differ.currentList[position]
        holder.view.article = news
        holder.view.li1.setOnClickListener {
            onShowMoreClick?.let {
                it(news)
            }
        }


        holder.view.sparkButton1.setEventListener { button, buttonState ->
            if (buttonState) {
                news.isFavourite = true
                onSaveNewsClick?.let {
                    Log.d("saved", news.toString())
                    it(news)
                }
            } else {
                // Button is inactive
                onArticleDeleteClick?.let {
                    it(news)
                }
            }
        }
        if (news.isFavourite) {
            holder.view.sparkButton1.isChecked = true
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemId(position: Int) = position.toLong()

    private var onShowMoreClick: ((ArticlesItem) -> Unit)? = null
    private var onSaveNewsClick: ((ArticlesItem) -> Unit)? = null
    private var onArticleDeleteClick: ((ArticlesItem) -> Unit)? = null

    fun setOnShowMoreListener(listener: ((ArticlesItem) -> Unit)) {
        onShowMoreClick = listener
    }

    fun onSaveNewsClickListener(listener: (ArticlesItem) -> Unit) {
        onSaveNewsClick = listener
    }

    fun onDeleteClickListener(listener: (ArticlesItem) -> Unit) {
        onArticleDeleteClick = listener
    }

}