package com.conceptgang.coronanews.component.view

import com.conceptgang.coronanews.model.CountryData


typealias ZHViewCallback = (Int, ZHViewData)->Unit

data class EpoxyData(
    val paddingLeftDp: Int,
    val paddingTopDp: Int,
    val paddingRightDp: Int,
    val paddingBottomDp: Int,
    val hasDividerBottom:Boolean = false,
    val hasDividerTop:Boolean = false
) {
    constructor(
        horizontal: Int,
        vertical: Int,
        hasDividerBottom:Boolean = false,
        hasDividerTop:Boolean = false
    ): this(horizontal, vertical, horizontal, vertical, hasDividerBottom, hasDividerTop)

    constructor(
        padding: Int,
        hasDividerBottom:Boolean = false,
        hasDividerTop:Boolean = false
    ): this(padding, padding, padding, padding, hasDividerBottom, hasDividerTop)

    companion object {
        val SMALL = EpoxyData(8,0)
        val MEDIUM = EpoxyData(16, 8)
    }
}

sealed class ZHViewData{
    abstract val tag: String
    abstract val isDarkMode: Boolean
    abstract val epoxyData: EpoxyData
}

data class WorldViewData(
    val data: CountryData,
    val type: WorldViewType,
    override val tag: String = "",
    override val isDarkMode: Boolean = true,
    override val epoxyData: EpoxyData = EpoxyData.MEDIUM
): ZHViewData()

object WorldShimmerData: ZHViewData() {
    override val tag: String = "Shimmer"
    override val isDarkMode: Boolean = true
    override val epoxyData: EpoxyData = EpoxyData.MEDIUM
}

