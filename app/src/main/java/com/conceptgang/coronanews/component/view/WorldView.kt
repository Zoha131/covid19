package com.conceptgang.coronanews.component.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.conceptgang.coronanews.R
import com.conceptgang.coronanews.databinding.ViewWorldBinding
import com.conceptgang.coronanews.utils.exhaustive
import com.conceptgang.coronanews.utils.toHumanFriendly
import com.google.android.material.card.MaterialCardView

sealed class WorldViewType{
    object TodayTotal: WorldViewType()
    object ActiveClosed: WorldViewType()
}

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class WorldView: MaterialCardView {

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        init(attr)
    }

    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int) : super(context, attr, defStyleAttr) {
        init(attr)
    }


    @ModelProp
    lateinit var zhViewData: WorldViewData

    private val data get() = zhViewData.data

    @ModelProp
    @JvmField
    var zhViewIndex: Int = -1

    @CallbackProp
    @JvmField
    @Nullable
    var zhViewCallback: ZHViewCallback? = null


    private lateinit var binding: ViewWorldBinding

    private fun init(attr: AttributeSet?) {
        val inflater = LayoutInflater.from(context)

        binding = ViewWorldBinding.inflate(inflater, this, true)

        radius = resources.getDimensionPixelSize(R.dimen.world_radius).toFloat()
        cardElevation = 6.toFloat()
    }


    @AfterPropsSet
    fun setup(){

        when(zhViewData.type){
            WorldViewType.TodayTotal -> setupForTotayTotal()
            WorldViewType.ActiveClosed -> setupForActiveClosed()
        }.exhaustive


    }


    private fun setupForTotayTotal(){

        binding.btnStart.setText(R.string.total)
        binding.btnEnd.setText(R.string.today)

        setupForTotal()

        binding.btnStart.setOnClickListener {
            setupForTotal()
        }

        binding.btnEnd.setOnClickListener {
            setupForToday()
        }

    }

    private fun setupForActiveClosed(){

        binding.btnStart.setText(R.string.active)
        binding.btnEnd.setText(R.string.closed)

        setupForActive()

        binding.btnStart.setOnClickListener {
            setupForActive()
        }

        binding.btnEnd.setOnClickListener {
            setupForClosed()
        }

    }

    private fun setStartBtnActive(){
        binding.btnStartLayout.setBackgroundResource(R.drawable.left_active)
        binding.btnStart.setTextColor(ContextCompat.getColor(context, R.color.textAccent))

        binding.btnEndLayout.setBackgroundResource(R.drawable.right_inactive)
        binding.btnEnd.setTextColor(ContextCompat.getColor(context, R.color.textPrimary))
    }

    private fun setEndBtnActive(){
        binding.btnStartLayout.setBackgroundResource(R.drawable.left_inactive)
        binding.btnStart.setTextColor(ContextCompat.getColor(context, R.color.textPrimary))

        binding.btnEndLayout.setBackgroundResource(R.drawable.right_active)
        binding.btnEnd.setTextColor(ContextCompat.getColor(context, R.color.textAccent))
    }

    private fun setupForTotal(){

        setStartBtnActive()
        moveToMiddle(false)

        binding.largeTitle.setText(R.string.corona_cases)
        binding.largeNumber.text = "${data.cases}".toHumanFriendly()

        binding.startTitle.setText(R.string.deaths)
        binding.startDigit.text = "${data.deaths}".toHumanFriendly()


        binding.endTitle.setText(R.string.recovered)
        binding.endDigit.text = "${data.recovered}".toHumanFriendly()
    }

    private fun setupForToday(){

        setEndBtnActive()
        moveToMiddle(true)

        binding.largeTitle.setText(R.string.corona_cases_today)
        binding.largeNumber.text = "${data.todayCases}".toHumanFriendly()

        binding.startTitle.setText(R.string.deaths)
        binding.startDigit.text = "${data.todayDeaths}".toHumanFriendly()


        binding.endTitle.setText(R.string.recovered)
        binding.endDigit.text = "-"
    }

    private fun setupForActive(){

        setStartBtnActive()
        moveToMiddle(false)

        binding.largeTitle.setText(R.string.currently_infected_patients)
        binding.largeNumber.text = "${data.active}".toHumanFriendly()

        binding.startTitle.setText(R.string.critical)
        binding.startDigit.text = "${data.critical}".toHumanFriendly()


        binding.endTitle.setText(R.string.mild)
        binding.endDigit.text = "${data.active - data.critical}".toHumanFriendly()
    }

    private fun setupForClosed(){

        setEndBtnActive()
        moveToMiddle(false)

        binding.largeTitle.setText(R.string.cases_outcome)
        binding.largeNumber.text = "${data.deaths + data.recovered}".toHumanFriendly()

        binding.startTitle.setText(R.string.deaths)
        binding.startDigit.text = "${data.deaths}".toHumanFriendly()

        binding.endTitle.setText(R.string.recovered)
        binding.endDigit.text = "${data.recovered}".toHumanFriendly()
    }


    private fun moveToMiddle(flag: Boolean):Unit = when(flag){
        true -> {
            binding.endDigit.visibility = View.GONE
            binding.endTitle.visibility = View.GONE
            binding.divider.visibility = View.GONE

            binding.guideline.setGuidelinePercent(1f)
        }
        false -> {
            binding.endDigit.visibility = View.VISIBLE
            binding.endTitle.visibility = View.VISIBLE
            binding.divider.visibility = View.VISIBLE

            binding.guideline.setGuidelinePercent(0.5f)


        }
    }


}
