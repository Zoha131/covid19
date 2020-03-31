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

    override val bottomNavId: Int = R.id.navigation_home
    override val zhViewCallback: ZHViewCallback = { i, zhViewData ->

    }


    override fun invalidate() = withState(homeViewModel) { homeState ->

        when (homeState.isLoading) {
            is Loading -> {
                setEpoxyData(homeState.views)
                rotateTopIcon()
            }
            is Uninitialized -> {
                download()
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
