package com.conceptgang.coronanews.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.conceptgang.coronanews.component.epoxy.BaseItemDecoration
import com.conceptgang.coronanews.component.epoxy.ZHViewController
import com.conceptgang.coronanews.component.view.ZHViewCallback
import com.conceptgang.coronanews.component.view.ZHViewData
import com.conceptgang.coronanews.databinding.FragmentEpoxyBaseBinding
import com.conceptgang.coronanews.utils.getDimenFromStyleAttr
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback

import kotlinx.android.synthetic.main.fragment_epoxy_base.view.*

abstract class BaseEpoxyFragment : BaseFragment() {

    protected lateinit var baseBinding: FragmentEpoxyBaseBinding

    protected open var zhItemDecoration: BaseItemDecoration = BaseItemDecoration()
    protected abstract val zhViewCallback: ZHViewCallback


    private val zhViewController: ZHViewController by lazy {
        ZHViewController(zhViewCallback)
    }


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

            val toolbarHeight = requireContext().getDimenFromStyleAttr(android.R.attr.actionBarSize) + insets.systemWindowInsetTop

            baseBinding.appbarLayout.run {
                updateLayoutParams<ViewGroup.LayoutParams> {
                    height = toolbarHeight
                }

                updatePadding(top = insets.systemWindowInsetTop)
            }

            insets
        }

        baseBinding.recyclerView.setController(zhViewController)
        baseBinding.recyclerView.addItemDecoration(zhItemDecoration)

    }


    protected fun setEpoxyData(data: List<ZHViewData>) {
        zhItemDecoration.views = data
        zhViewController.setData(data)
    }


}
