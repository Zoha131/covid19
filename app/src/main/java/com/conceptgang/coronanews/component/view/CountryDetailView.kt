package com.conceptgang.coronanews.component.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import coil.api.load
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.conceptgang.coronanews.R
import com.conceptgang.coronanews.databinding.ViewCountryExpandedBinding
import com.conceptgang.coronanews.utils.toHumanFriendly
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
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

        binding.flagImage.load(data.flag)


        val entries = historyData.values.mapIndexed { index, i ->   Entry(index.toFloat(), i.toFloat() ) }


        // create a dataset and give it a type
        val set1 = LineDataSet(entries, "DataSet 1")

        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER)
        set1.setCubicIntensity(0.2f)
        set1.setDrawFilled(true)
        set1.setDrawCircles(false)
        set1.setLineWidth(1.8f)
        set1.setCircleRadius(4f)
        set1.setCircleColor(Color.WHITE)
        set1.setHighLightColor(Color.rgb(244, 117, 117))
        set1.setColor(ContextCompat.getColor(context, R.color.deathColor))
        set1.setFillColor(ContextCompat.getColor(context, R.color.deathColor))
        set1.setFillAlpha(80)
        set1.setDrawHorizontalHighlightIndicator(false)
        set1.setFillFormatter(IFillFormatter { dataSet, dataProvider ->
            binding.chart.getAxisLeft().getAxisMinimum()
        })

        // create a data object with the data sets

        // create a data object with the data sets
        val data = LineData(set1)
        data.setValueTextSize(9f)
        data.setDrawValues(false)

        binding.chart.data = data


        binding.chart.setViewPortOffsets(0f, 10f, 0f, 0f)
        //binding.chart.setBackgroundColor(Color.rgb(104, 241, 175))

        // no description text

        // no description text
        binding.chart.getDescription().setEnabled(false)

        // enable touch gestures

        // enable touch gestures
        binding.chart.setTouchEnabled(false)

        // enable scaling and dragging

        // enable scaling and dragging
        binding.chart.setDragEnabled(false)
        binding.chart.setScaleEnabled(false)

        // if disabled, scaling can be done on x- and y-axis separately

        // if disabled, scaling can be done on x- and y-axis separately
        binding.chart.setPinchZoom(false)

        binding.chart.setDrawGridBackground(false)
        binding.chart.setMaxHighlightDistance(300f)

        val x: XAxis = binding.chart.getXAxis()
        x.isEnabled = false

        val y: YAxis = binding.chart.getAxisLeft()
        //y.typeface = tfLight
        y.setLabelCount(6, false)
        y.textColor = ContextCompat.getColor(context, R.color.textPrimary)
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        y.setDrawGridLines(false)
        y.axisLineColor = ContextCompat.getColor(context, R.color.window_bg_6dp)

        binding.chart.axisRight.setEnabled(false)



        binding.chart.getLegend().setEnabled(false)

        binding.chart.animateXY(2000, 2000)

        // don't forget to refresh the drawing

        // don't forget to refresh the drawing
        binding.chart.invalidate()






    }
}