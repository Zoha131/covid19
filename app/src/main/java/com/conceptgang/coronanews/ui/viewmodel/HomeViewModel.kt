package com.conceptgang.coronanews.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.*
import com.conceptgang.coronanews.CoronaApp
import com.conceptgang.coronanews.base.BaseViewModel
import com.conceptgang.coronanews.component.view.WorldShimmerData
import com.conceptgang.coronanews.component.view.WorldViewData
import com.conceptgang.coronanews.component.view.WorldViewType
import com.conceptgang.coronanews.component.view.ZHViewData
import com.conceptgang.coronanews.model.CountryData
import com.conceptgang.coronanews.ui.repository.CoronaRepository
import kotlinx.coroutines.launch

data class HomeState(
    val views: List<ZHViewData> = emptyList(),
    val isLoading: Async<Boolean> = Uninitialized
) : MvRxState


class HomeViewModel(state: HomeState, val repository: CoronaRepository) :
    BaseViewModel<HomeState>(state) {


    companion object : MvRxViewModelFactory<HomeViewModel, HomeState> {
        override fun create(viewModelContext: ViewModelContext, state: HomeState): HomeViewModel {

            val app = viewModelContext.app<CoronaApp>()

            return HomeViewModel(state, app.repository)

        }
    }


    fun update() {

        setState {
            copy(
                isLoading = Loading()
            )
        }

        viewModelScope.launch {

            try {

                val world = repository.getWorldData()

                setState {
                    copy(
                        views = listOf(
                            WorldViewData(world, WorldViewType.TodayTotal),
                            WorldViewData(world, WorldViewType.ActiveClosed)
                        ),
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

    fun getViews() {
        setState {
            copy(
                views = listOf(
                    WorldShimmerData,
                    WorldShimmerData
                ),
                isLoading = Loading()
            )
        }
    }


}