package com.sonusourav.pullr.presentation.ui.pullRequest

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sonusourav.pullr.R
import com.sonusourav.pullr.data.models.PullRequest
import com.sonusourav.pullr.presentation.ui.base.BaseDiffAdapter
import com.sonusourav.pullr.presentation.ui.base.VIEW_TYPE_NORMAL
import kotlinx.android.synthetic.main.item_pr.view.closed_on_textview
import kotlinx.android.synthetic.main.item_pr.view.created_on_textview
import kotlinx.android.synthetic.main.item_pr.view.pr_number
import kotlinx.android.synthetic.main.item_pr.view.pr_title
import kotlinx.android.synthetic.main.item_pr.view.pr_user_image
import kotlinx.android.synthetic.main.item_pr.view.pr_username

class PullRequestAdapter(private val context: Context, var listener: ItemClickListener) :
    BaseDiffAdapter<PullRequest, RecyclerView.ViewHolder>() {
    interface ItemClickListener {
        fun onItemClicked(pr: PullRequest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_NORMAL)
            PrViewModel(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_pr, parent, false)
            )
        else LoadingViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loading, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_NORMAL) {
            val closedPr = getItem(position)
            val viewHolder = holder as PrViewModel
            viewHolder.userName.text = closedPr?.user?.prOwner
            viewHolder.prNumber.text = "#${closedPr?.pullRequestId}"
            viewHolder.createdOn.text = closedPr?.createdDate?.split("T")?.get(0) ?: ""
            viewHolder.closedOn.text = closedPr?.closedDate?.split("T")?.get(0) ?: ""
            viewHolder.prTitle.text = closedPr?.title
            closedPr?.let { repo ->
                viewHolder.itemView.setOnClickListener { listener.onItemClicked(repo) }
            }
            closedPr?.user?.profileUrl?.let {
                Glide.with(context).load(it)
                    .placeholder(R.drawable.ic_user)
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.userImage)
            }
        }
    }

    class PrViewModel(view: View) : RecyclerView.ViewHolder(view) {
        val userName: TextView = view.pr_username
        val prNumber: TextView = view.pr_number
        val createdOn: TextView = view.created_on_textview
        val closedOn: TextView = view.closed_on_textview
        val prTitle: TextView = view.pr_title
        val userImage: ImageView = view.pr_user_image
    }

}