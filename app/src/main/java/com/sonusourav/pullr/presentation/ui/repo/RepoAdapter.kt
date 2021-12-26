package com.sonusourav.pullr.presentation.ui.repo

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sonusourav.pullr.R
import com.sonusourav.pullr.data.models.Repo
import com.sonusourav.pullr.presentation.ui.base.BaseDiffAdapter
import com.sonusourav.pullr.presentation.ui.base.VIEW_TYPE_NORMAL
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoAdapter(var listener: ItemClickListener) :
    BaseDiffAdapter<Repo, RecyclerView.ViewHolder>() {
    interface ItemClickListener {
        fun onItemClicked(repos: Repo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_NORMAL)
            MainViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_repo, parent, false)
            )
        else LoadingViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loading, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_NORMAL) {
            val repos = getItem(position)
            val viewHolder = holder as MainViewHolder
            viewHolder.titleTextView.text = repos?.name
            viewHolder.descriptionTextView.text = repos?.description
            viewHolder.watchersTextView.text = repos?.watchersCount.toString()
            viewHolder.languageTextView.text = repos?.language
            viewHolder.starsTextView.text = repos?.startCount.toString()
            repos?.let { repo ->
                viewHolder.itemView.setOnClickListener { listener.onItemClicked(repo) }
            }
        }
    }

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.list_item_title_textView
        val descriptionTextView: TextView = view.list_item_description_textView
        val watchersTextView: TextView = view.list_item_watchers_textView
        val languageTextView: TextView = view.list_item_language_textView
        val starsTextView: TextView = view.list_item_stars_textView
    }

}