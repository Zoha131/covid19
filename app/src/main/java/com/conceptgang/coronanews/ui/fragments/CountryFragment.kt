package com.conceptgang.coronanews.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.airbnb.mvrx.*
import com.conceptgang.coronanews.R
import com.conceptgang.coronanews.base.BaseEpoxyFragment
import com.conceptgang.coronanews.component.view.ZHViewCallback
import com.conceptgang.coronanews.ui.dialog.ZHDialog
import com.conceptgang.coronanews.ui.dialog.ZHDialogType
import com.conceptgang.coronanews.utils.asFlow
import com.conceptgang.coronanews.utils.exhaustive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class CountryFragment : BaseEpoxyFragment(){

    override val bottomNavId: Int = R.id.navigation_globe
    override val zhViewCallback: ZHViewCallback = {i, zhViewData ->

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            baseBinding.searchTxtWhite.asFlow().debounce(250).collect {
                countryViewModel.searchByCountryName(it)
            }
        }
    }

    override fun invalidate() = withState(countryViewModel) { countryState ->

        when (countryState.isLoading) {
            is Loading -> {
                setEpoxyData(countryState.views)
                rotateTopIcon()
            }
            is Uninitialized -> {
                download()
            }
            is Success -> {
                setEpoxyData(countryState.views)
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