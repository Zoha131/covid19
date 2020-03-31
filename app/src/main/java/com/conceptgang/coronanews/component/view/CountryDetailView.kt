package com.conceptgang.coronanews.component.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.conceptgang.coronanews.R
import com.conceptgang.coronanews.databinding.ViewCountryBinding
import com.conceptgang.coronanews.databinding.ViewCountryExpandedBinding
import com.conceptgang.coronanews.utils.toHumanFriendly
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.EntryXComparator
import com.google.android.material.card.MaterialCardView

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CountryDetailView: MaterialCardView {

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
    lateinit var zhViewData: CountryDetailViewData

    private val data get() = zhViewData.data
    private val historyData get() = zhViewData.history

    @ModelProp
    @JvmField
    var zhViewIndex: Int = -1

    @CallbackProp
    @JvmField
    @Nullable
    var zhViewCallback: ZHViewCallback? = null

    private lateinit var binding: ViewCountryExpandedBinding

    private fun init(attr: AttributeSet?) {
        val inflater = LayoutInflater.from(context)

        binding = ViewCountryExpandedBinding.inflate(inflater, this, true)

        radius = resources.getDimensionPixelSize(R.dimen.world_radius).toFloat()
        cardElevation = 6.toFloat()
    }


    @AfterPropsSet
    fun setup(){
        binding.nameCountry.text = data.country
        binding.totalCaseDigit.text = "${data.cases}".toHumanFriendly()
        binding.todayCasesDigit.text = "${data.todayCases}".toHumanFriendly()
        binding.deathDigit.text = "${data.deaths}".toHumanFriendly()
        binding.todayDeathsDigit.text = "${data.todayDeaths}".toHumanFriendly()
        binding.recoverDigit.text = "${data.recovered}".toHumanFriendly()
        binding.activeCaseDigit.text = "${data.active}".toHumanFriendly()
        binding.criticalCaseDigit.text = "${data.critical}".toHumanFriendly()
        binding.caseMillionDigit.text = "${data.casesPerOneMillion}".toHumanFriendly()
        binding.deathMillionDigit.text = "${data.deathsPerOneMillion}".toHumanFriendly()


        val entries = historyData.values.mapIndexed { index, i ->   Entry(index.toFloat(), i.toFloat() )}
        val dataSet = LineDataSet(entries, "History")
        dataSet.setColor(ContextCompat.getColor(context, R.color.deathColor))

        val lineData = LineData(dataSet)
        binding.chart.data = lineData
        binding.chart.invalidate()

        binding.chart.description.isEnabled = false


    }
}