package com.example.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.ListItem2Binding
import com.example.news.databinding.ListItemBinding
import com.example.news.model.ArticlesItem

class NewsAdapter2 :RecyclerView.Adapter<NewsAdapter2.ViewHolder>() {

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

    class ViewHolder(var view: ListItem2Binding) : RecyclerView.ViewHolder(view.root)

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter2.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ListItem2Binding>(
            inflater,
            R.layout.list_item2,
            parent,
            false
        )
        return NewsAdapter2.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsAdapter2.ViewHolder, position: Int) {
        val news: ArticlesItem = differ.currentList[position]
        holder.view.article = news
        holder.view.li2.setOnClickListener {
            onShowMoreClick?.let {
                it(news)
            }
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