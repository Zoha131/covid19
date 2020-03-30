package com.conceptgang.coronanews.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.conceptgang.coronanews.CoronaApp
import com.conceptgang.coronanews.R
import com.conceptgang.coronanews.base.BaseEpoxyFragment
import com.conceptgang.coronanews.component.view.ZHViewCallback
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception


class HomeFragment : BaseEpoxyFragment() {

    override val zhViewCallback: ZHViewCallback = { i, zhViewData ->

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val app = requireActivity().application as CoronaApp

        lifecycleScope.launch {
            try {
                app.repository.update()
            } catch (ex: Exception){
                Timber.tag("fatal").d(ex.message)
            }
        }
    }

    override fun invalidate() {

    }


}
