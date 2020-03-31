package com.conceptgang.coronanews.component.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.Nullable
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.conceptgang.coronanews.R
import com.conceptgang.coronanews.databinding.ShimmerWorldBinding
import com.conceptgang.coronanews.databinding.ViewWorldBinding
import com.google.android.material.card.MaterialCardView

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class WorldShimmer: MaterialCardView {

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

         ShimmerWorldBinding.inflate(inflater, this, true)

        radius = resources.getDimensionPixelSize(R.dimen.world_radius).toFloat()
        cardElevation = 6.toFloat()


    }
}
