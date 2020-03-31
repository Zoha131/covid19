package com.conceptgang.coronanews.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.core.view.*
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.activityViewModel
import com.conceptgang.coronanews.R
import com.conceptgang.coronanews.component.epoxy.BaseItemDecoration
import com.conceptgang.coronanews.component.epoxy.ZHViewController
import com.conceptgang.coronanews.component.view.ZHViewCallback
import com.conceptgang.coronanews.component.view.ZHViewData
import com.conceptgang.coronanews.databinding.FragmentEpoxyBaseBinding
import com.conceptgang.coronanews.ui.viewmodel.CountryViewModel
import com.conceptgang.coronanews.ui.viewmodel.HomeViewModel
import com.conceptgang.coronanews.utils.getDimenFromStyleAttr

abstract class BaseEpoxyFragment : BaseFragment() {

    protected lateinit var baseBinding: FragmentEpoxyBaseBinding

    protected open var zhItemDecoration: BaseItemDecoration = BaseItemDecoration()
    protected abstract val zhViewCallback: ZHViewCallback
    protected abstract val bottomNavId: Int


    private val zhViewController: ZHViewController by lazy {
        ZHViewController(zhViewCallback)
    }

    protected val homeViewModel by activityViewModel(HomeViewModel::class)
    protected val countryViewModel by activityViewModel(CountryViewModel::class)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        baseBinding = FragmentEpoxyBaseBinding.inflate(inflater, container, false)

        return baseBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(baseBinding.appbarLayout) { _, insets ->

            val toolbarHeight =
                requireContext().getDimenFromStyleAttr(android.R.attr.actionBarSize) + insets.systemWindowInsetTop

            baseBinding.appbarLayout.run {
                updateLayoutParams<ViewGroup.LayoutParams> {
                    height = toolbarHeight
                }

                updatePadding(top = insets.systemWindowInsetTop)
            }

            insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(baseBinding.bottomNavigation) { _, insets ->

            val toolbarHeight =
                requireContext().getDimenFromStyleAttr(android.R.attr.actionBarSize) + insets.systemWindowInsetBottom
            baseBinding.bottomNavigation.run {
                updateLayoutParams<ViewGroup.LayoutParams> {
                    height = toolbarHeight
                }

                updatePadding(bottom = insets.systemWindowInsetBottom)
            }

            baseBinding.recyclerView.run {
                updatePadding(bottom = toolbarHeight)
            }

            insets
        }

        baseBinding.recyclerView.setController(zhViewController)
        baseBinding.recyclerView.addItemDecoration(zhItemDecoration)

        setupUiVisibility()

        baseBinding.bottomNavigation.selectedItemId = bottomNavId
        baseBinding.bottomNavigation.setOnNavigationItemSelectedListener { item: MenuItem ->

            if (item.itemId == bottomNavId) return@setOnNavigationItemSelectedListener true

            when (item.itemId) {

                R.id.navigation_home -> {
                    findNavController().navigate(R.id.action_to_homeFragment)
                }


                R.id.navigation_globe -> {
                    findNavController().navigate(R.id.action_to_countryFragment)
                }

                else -> {
                    Toast.makeText(
                        requireContext(),
                        "This feature is under development",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setOnNavigationItemSelectedListener false
                }
            }

            true


        }

        baseBinding.endLayout.setOnClickListener {
            download()
        }

    }

    private fun setupUiVisibility(){

        when(bottomNavId){
            R.id.navigation_globe -> {
                baseBinding.searchCard.visibility = View.VISIBLE
            }

            else -> {
                baseBinding.searchCard.visibility = View.GONE
            }
        }

    }

    protected fun download() {
        homeViewModel.update()
        countryViewModel.update()
    }

    protected fun rotateTopIcon() {
        val rotate = RotateAnimation(
            11 * 360f,
            0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotate.duration = 33000
        rotate.interpolator = LinearInterpolator()

        baseBinding.endIcon.startAnimation(rotate)
    }

    protected fun setEpoxyData(data: List<ZHViewData>) {
        zhItemDecoration.views = data
        zhViewController.setData(data)
    }


}
