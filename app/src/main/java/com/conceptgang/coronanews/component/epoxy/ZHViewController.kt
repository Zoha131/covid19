package com.conceptgang.coronanews.component.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.conceptgang.coronanews.component.view.*
import com.conceptgang.coronanews.utils.exhaustive

class ZHViewController(

    private val callback: ZHViewCallback

) : TypedEpoxyController<List<ZHViewData>>() {
    override fun buildModels(data: List<ZHViewData>) {

        data.forEachIndexed { index, zhViewData ->

            when(zhViewData){

                is WorldViewData -> {
                    worldView {
                        id("world $index")
                        zhViewData(zhViewData)
                        zhViewCallback(callback)
                    }
                }

                is WorldShimmerData -> {
                    worldShimmer {
                        id("Shimmer $index")
                    }
                }

                is CountryViewData -> {
                    countryView {
                        id("country $index")
                        zhViewData(zhViewData)
                        zhViewCallback(callback)
                    }
                }

                is CountryShimmerData -> {
                    countryShimmer {
                        id("Shimmer $index")
                    }
                }

            }.exhaustive

        }


    }

}