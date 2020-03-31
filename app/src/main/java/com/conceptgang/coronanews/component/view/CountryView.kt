package com.conceptgang.coronanews.component.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.Nullable
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.conceptgang.coronanews.R
import com.conceptgang.coronanews.databinding.ShimmerCountryBinding
import com.conceptgang.coronanews.databinding.ShimmerWorldBinding
import com.conceptgang.coronanews.databinding.ViewCountryBinding
import com.conceptgang.coronanews.databinding.ViewWorldBinding
import com.conceptgang.coronanews.model.CountryData
import com.conceptgang.coronanews.utils.toHumanFriendly
import com.google.android.material.card.MaterialCardView

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CountryView: MaterialCardView {

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        init(attr)
    }

    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int) : super(
        context,
        attr,
        defStyleAttr
    ) {
        init(attr)
    }


    @ModelProp
    lateinit var zhViewData: CountryViewData

    private val data get() = zhViewData.data

    @ModelProp
    @JvmField
    var zhViewIndex: Int = -1

    @CallbackProp
    @JvmField
    @Nullable
    var zhViewCallback: ZHViewCallback? = null

    private lateinit var binding: ViewCountryBinding

    private fun init(attr: AttributeSet?) {
        val inflater = LayoutInflater.from(context)

        binding = ViewCountryBinding.inflate(inflater, this, true)

        radius = resources.getDimensionPixelSize(R.dimen.world_radius).toFloat()
        cardElevation = 6.toFloat()
    }


    @AfterPropsSet
    fun setup(){
        binding.countryName.text = data.country
        binding.totalDigit.text = "${data.cases}".toHumanFriendly()
        binding.deathDigit.text = "${data.deaths}".toHumanFriendly()
    }
}


@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CountryShimmer: MaterialCardView {

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        init(attr)
    }

    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int) : super(context, attr, defStyleAttr) {
        init(attr)
    }



    private fun init(attr: AttributeSet?) {
        val inflater = LayoutInflater.from(context)

        ShimmerCountryBinding.inflate(inflater, this, true)

        radius = resources.getDimensionPixelSize(R.dimen.world_radius).toFloat()
        cardElevation = 6.toFloat()


    }
}
