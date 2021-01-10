package ru.skillbranch.skillarticles.ui.bookmarks

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.skillarticles.data.models.ArticleItemData
import ru.skillbranch.skillarticles.ui.custom.ArticleItemView

class BookmarkAdapter(
    private val listener: (ArticleItemData) -> Unit,
    private val toggleBookmarkListener: (String, Boolean) -> Unit
) :
    PagedListAdapter<ArticleItemData, BookmarkArticleVH>(BookmarkArticleDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkArticleVH {
        val view = ArticleItemView(parent.context)
        return BookmarkArticleVH(view)
    }

    override fun onBindViewHolder(holder: BookmarkArticleVH, position: Int) {
        holder.bind(getItem(position), listener, toggleBookmarkListener)
    }
}

class BookmarkArticleDiffCallback : DiffUtil.ItemCallback<ArticleItemData>() {
    override fun areItemsTheSame(oldItem: ArticleItemData, newItem: ArticleItemData): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ArticleItemData, newItem: ArticleItemData): Boolean =
        oldItem == newItem
}

class BookmarkArticleVH(private val containerView: View) : RecyclerView.ViewHolder(containerView) {
    fun bind(
        item: ArticleItemData?,
        listener: (ArticleItemData) -> Unit,
        toggleBookmarkListener: (String, Boolean) -> Unit
    ) {
        // if use placeholder item may be null
        (containerView as ArticleItemView).bind(
            item!!,
            toggleBookmarkListener
        )
        itemView.setOnClickListener { listener(item) }
    }
}