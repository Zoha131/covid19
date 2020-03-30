package com.conceptgang.coronanews.base

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.BuildConfig
import com.airbnb.mvrx.MvRxState


abstract class BaseViewModel<S: MvRxState>(state: S) : BaseMvRxViewModel<S>(state, BuildConfig.DEBUG)