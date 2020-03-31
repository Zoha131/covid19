package com.conceptgang.coronanews.ui.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.Toast
import com.airbnb.mvrx.*
import com.conceptgang.coronanews.base.BaseEpoxyFragment
import com.conceptgang.coronanews.component.view.ZHViewCallback
import com.conceptgang.coronanews.ui.viewmodel.HomeViewModel
import com.conceptgang.coronanews.utils.exhaustive
import com.conceptgang.coronanews.R
import com.conceptgang.coronanews.ui.dialog.ZHDialog
import com.conceptgang.coronanews.ui.dialog.ZHDialogType


class HomeFragment : BaseEpoxyFragment() {

    override val zhViewCallback: ZHViewCallback = { i, zhViewData ->

    }


    private val viewModel by activityViewModel (HomeViewModel::class)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseBinding.endLayout.setOnClickListener {
            viewModel.update()
        }

        baseBinding.bottomNavigation.setOnNavigationItemSelectedListener { item: MenuItem ->
            when(item.itemId){
                R.id.home -> {
                    true
                }
                else -> {
                    Toast.makeText(requireContext(), "This feature is under development", Toast.LENGTH_SHORT).show()
                    false
                }
            }

        }
    }

    override fun invalidate() = withState(viewModel) { homeState ->

        when (homeState.isLoading) {
            is Loading -> {
                setEpoxyData(homeState.views)

                val rotate = RotateAnimation(
                    11*360f,
                    0f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f
                )
                rotate.duration = 33000
                rotate.setInterpolator(LinearInterpolator())

                baseBinding.endIcon.startAnimation(rotate)

            }
            is Uninitialized -> {
                viewModel.getViews()
                viewModel.update()
            }
            is Success -> {
                setEpoxyData(homeState.views)
                baseBinding.endIcon.clearAnimation()
            }
            is Fail -> {
                baseBinding.endIcon.clearAnimation()

                val dialog = ZHDialog(R.drawable.question, getString(R.string.opps), getString(R.string.somethingWrong), ZHDialogType.Normal)

                dialog.show(childFragmentManager, "FAILED")

            }

        }.exhaustive

    }


}