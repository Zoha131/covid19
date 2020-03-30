package com.conceptgang.coronanews.component.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.conceptgang.coronanews.component.view.ZHViewCallback
import com.conceptgang.coronanews.component.view.ZHViewData

class ZHViewController(

    private val callback: ZHViewCallback

) : TypedEpoxyController<List<ZHViewData>>() {
    override fun buildModels(data: List<ZHViewData>) {

        data.forEachIndexed { index, zhViewData ->

//            when(zhViewData){
//
//
//
//
//            }.exhaustive

        }


    }

}