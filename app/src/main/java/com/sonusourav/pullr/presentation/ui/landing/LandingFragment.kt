package com.sonusourav.pullr.presentation.ui.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androchef.githubsampleapp.extensions.isValid
import com.androchef.githubsampleapp.extensions.toast
import com.sonusourav.pullr.R
import com.sonusourav.pullr.utils.extensions.addFragmentBackStack
import com.sonusourav.pullr.presentation.ui.HomeActivity
import com.sonusourav.pullr.presentation.ui.repo.RepoFragment
import kotlinx.android.synthetic.main.fragment_landing.view.btnContinue
import kotlinx.android.synthetic.main.fragment_landing.view.edtUserName

class LandingFragment : Fragment() {

    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_landing, container, false)
        onClicks()
        (requireActivity() as HomeActivity).supportActionBar?.title =
            getString(R.string.app_name)
        return mView.rootView
    }

    private fun onClicks() {
        mView.btnContinue.setOnClickListener {
            val userName = mView.edtUserName.editableText.toString()
            if (userName.isValid()) {
                goToPullRequestFragment(userName)
            } else {
                toast(getString(com.sonusourav.pullr.R.string.info_valid_username))
            }
        }
    }

    private fun goToPullRequestFragment(userName: String) {
        addFragmentBackStack(
            R.id.mainFragmentContainer, RepoFragment.newInstance(userName), null
        )
    }

    companion object {
        const val TAG = "LandingFragment"
        fun newInstance(): LandingFragment {
            return LandingFragment()
        }
    }
}