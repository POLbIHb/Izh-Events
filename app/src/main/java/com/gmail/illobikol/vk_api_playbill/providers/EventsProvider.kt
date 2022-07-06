package com.gmail.illobikol.vk_api_playbill.providers

import com.gmail.illobikol.vk_api_playbill.presenters.EventsPresenter
import android.util.Log
import com.gmail.illobikol.vk_api_playbill.models.*
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback


class EventsProvider(var presenter: EventsPresenter) {
    fun loadEvents(){
        VK.execute(VKEventsIdRequest(),object: VKApiCallback<List<VKEventIdModel>>{

            override fun success(resultId: List<VKEventIdModel>) {
                Log.e("TAG", "нормально - 1")
                Log.e("TAG", resultId.toString())
                VK.execute(VKEventsRequest(resultId),object: VKApiCallback<List<VKEventModel>>{

                    override fun success(result: List<VKEventModel>) {
                        Log.e("TAG", "нормально - 2")
                        presenter.eventsLoaded(result)
                    }

                    override fun fail(error: Exception) {
                        Log.e("TAG", "ошибочка - 2")
                        presenter.showError(error)
                    }
                })
            }

            override fun fail(error: Exception) {
                Log.e("TAG", "ошибочка - 1")
                presenter.showError(error)
            }
        })
    }
}