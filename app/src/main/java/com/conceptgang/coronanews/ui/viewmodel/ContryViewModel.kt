package com.conceptgang.coronanews.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.*
import com.conceptgang.coronanews.CoronaApp
import com.conceptgang.coronanews.base.BaseViewModel
import com.conceptgang.coronanews.component.view.*
import com.conceptgang.coronanews.model.CountryData
import com.conceptgang.coronanews.ui.repository.CoronaRepository
import kotlinx.coroutines.launch

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

                rawData = repository.getCountryData()

                val mViews = rawData.map { CountryDetailViewData(it) }

                setState {
                    copy(
                        views = mViews,
                        isLoading = Success(true)
                    )
                }


            } catch (ex: Exception) {
                setState {
                    copy(
                        isLoading = Fail(ex)
                    )
                }

            }

        }

    }

    fun searchByCountryName(key: CharSequence){
        val searchResult = rawData.filter { it.country.contains(key, true) }
        val searchViews = searchResult.map { CountryViewData(it) }

        setState { copy(views = searchViews) }
    }


}