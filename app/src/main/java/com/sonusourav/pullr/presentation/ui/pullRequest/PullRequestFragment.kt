package com.sonusourav.pullr.presentation.ui.pullRequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.androchef.githubsampleapp.extensions.toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sonusourav.pullr.R
import com.sonusourav.pullr.data.models.PullRequest
import com.sonusourav.pullr.presentation.ui.HomeActivity
import com.sonusourav.pullr.presentation.utils.Event
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.empty_view.empty_view_imageView
import kotlinx.android.synthetic.main.fragment_pull_request.pr_emptyView
import kotlinx.android.synthetic.main.fragment_pull_request.pr_recyclerView

class PullRequestFragment : Fragment() {
    private lateinit var viewModel: PullRequestViewModel
    private lateinit var adapter: PullRequestAdapter
    private lateinit var userName: String
    private lateinit var repo: String
    private val fetchDataDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(PullRequestViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mView = inflater.inflate(R.layout.fragment_pull_request, container, false)
        return mView.rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViews()
        registerObservables()
        arguments?.getString(ARGS_REPO)?.let {
            repo = it
        }
        arguments?.getString(ARGS_USERNAME)?.let {
            userName = it
            viewModel.fetchClosedPullRequest(it, repo)
        }
    }

    private fun setupViews() {
        (requireActivity() as HomeActivity).supportActionBar?.title = "Your closed PRs"

        Glide.with(this).asGif().load(R.raw.gif_search_folder)
            .apply(RequestOptions().override(300, 300))
            .into(empty_view_imageView)

        adapter = PullRequestAdapter(requireContext(),
            object : PullRequestAdapter.ItemClickListener {
                override fun onItemClicked(pr: PullRequest) {

                }
            }
        )
        pr_recyclerView.layoutManager = LinearLayoutManager(requireContext())
        pr_recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fetchDataDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        fetchDataDisposable.dispose()
    }

    private fun registerObservables() {
        // We start by submitting the list to the adapter initially
        submitItems()

        // Toast for API Errors
        viewModel.errorEvent.observe(
            this
        ) {
            onError()
        }

        // Clearing the data of the adapter when doing a new search
        viewModel.clearDataEvents.observe(
            this
        ) {
            viewModel.clearDataSource()
            submitItems()
            adapter.notifyDataSetChanged()
        }

        // Showing an empty view on the screen
        viewModel.emptyVisibilityEvents.observe(
            this
        ) {
            onNoDataFound(it)
        }

        // Display the recyclerview loading item
        viewModel.recyclerViewLoadingEvents.observe(
            this
        ) { event ->
            event.peek().let { adapter.loading = it }
        }
    }

    private fun onError() {
        Glide.with(this).load(R.drawable.ic_error)
            .apply(RequestOptions().override(800, 500))
            .into(empty_view_imageView)
        toast(getString(R.string.err_search))
    }

    private fun onNoDataFound(show: Event<Boolean?>) {
        this.pr_emptyView.visibility = if (show.peek() == true) View.VISIBLE else View.GONE

        Glide.with(this).load(R.drawable.ic_no_data)
            .into(empty_view_imageView)
    }

    // Submits the list (with the pagination) to the adapter
    private fun submitItems() {
        viewModel.getItems()!!
            .subscribeOn(Schedulers.io())
            .subscribe(
                { items -> adapter.submitList(items) },
                {
                    pr_emptyView.visibility = View.VISIBLE
                    onError()
                }
            ).also {
                fetchDataDisposable.add(it)
            }
    }

    companion object {
        const val TAG = "UserPullRequestFragment"
        private const val ARGS_USERNAME = "args_username"
        private const val ARGS_REPO = "args_repo"
        fun newInstance(userName: String, repo: String): PullRequestFragment {
            val bundle = Bundle()
            bundle.putString(ARGS_USERNAME, userName)
            bundle.putString(ARGS_REPO, repo)
            val fragment = PullRequestFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
