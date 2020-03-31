package com.conceptgang.coronanews.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.*
import com.conceptgang.coronanews.CoronaApp
import com.conceptgang.coronanews.base.BaseViewModel
import com.conceptgang.coronanews.component.view.*
import com.conceptgang.coronanews.model.CountryData
import com.conceptgang.coronanews.ui.repository.CoronaRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

data class CountryState(
    val views: List<ZHViewData> = emptyList(),
    val isLoading: Async<Boolean> = Uninitialized
) : MvRxState


class CountryViewModel(state: CountryState, val repository: CoronaRepository) : BaseViewModel<CountryState>(state) {


    companion object : MvRxViewModelFactory<CountryViewModel, CountryState> {
        override fun create(viewModelContext: ViewModelContext, state: CountryState): CountryViewModel {

            val app = viewModelContext.app<CoronaApp>()

            return CountryViewModel(state, app.repository)

        }
    }

    private var rawData: List<CountryData> = emptyList()
    private var rawHistory:  Map<String, Map<String, Int>> = emptyMap()

    fun update() {

        setState {
            copy(
                views = listOf(
                    CountryShimmerData,
                    CountryShimmerData,
                    CountryShimmerData,
                    CountryShimmerData,
                    CountryShimmerData,
                    CountryShimmerData,
                    CountryShimmerData
                ),
                isLoading = Loading()
            )
        }

        viewModelScope.launch {

            try {

                val countryData = async { repository.getCountryData() }
                val historyData = async { repository.getHistoryData() }


                rawData = countryData.await()
                rawHistory = historyData.await()


                setState {
                    copy(
                        views = getDetailViews(rawData),
                        isLoading = Success(true)
                    )
                }


            } catch (ex: Exception) {
                setState {
                    copy(
                        isLoading = Fail(ex)
                    )
                }

                Timber.tag("HISTORYDATA").d("${ex.message}")

            }

        }

    }

    fun getDetailViews(countryData: List<CountryData>) = countryData.map {entryData ->


        val map = if(entryData.country.contains("bosnia", true)){

            rawHistory.get("bosnia")
        } else{
            rawHistory.get(entryData.country.toLowerCase())

        }

        CountryDetailViewData(entryData, map?: emptyMap())


    }

    fun searchByCountryName(key: CharSequence){
        val searchResult = rawData.filter { it.country.contains(key, true) }
        val searchViews = searchResult.map { CountryViewData(it) }

        setState { copy(views = getDetailViews(searchResult)) }
    }


}