package com.gmail.illobikol.vk_api_playbill.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gmail.illobikol.vk_api_playbill.models.VKEventModel

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface EventsView: MvpView {
    fun showError(textResource: Int)
    fun setupEmptyList()
    fun setupEventsList(eventsList: List<VKEventModel>)
    fun startLoading(isLoading: Boolean)
}