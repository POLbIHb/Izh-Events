package com.gmail.illobikol.vk_api_playbill.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gmail.illobikol.vk_api_playbill.R
import com.gmail.illobikol.vk_api_playbill.models.VKEventModel
import com.gmail.illobikol.vk_api_playbill.providers.EventsProvider
import com.gmail.illobikol.vk_api_playbill.views.EventsView


@InjectViewState
class EventsPresenter : MvpPresenter<EventsView>(){
    fun loadEvents() {
        viewState.startLoading(true)
        Log.e("TAG", "srart loading")
        EventsProvider(presenter = this).loadEvents()
    }

    fun eventsLoaded(eventsList: List<VKEventModel>){
        viewState.startLoading(false)
        if(eventsList.isEmpty()){
            viewState.setupEmptyList()
            viewState.showError(textResource = R.string.events_no_items)
        } else{
            viewState.setupEventsList(eventsList = eventsList)
        }
    }

    fun showError(error: Exception){
        viewState.startLoading(false)
        viewState.setupEmptyList()
        viewState.showError(R.string.list_error_notification)
    }
}