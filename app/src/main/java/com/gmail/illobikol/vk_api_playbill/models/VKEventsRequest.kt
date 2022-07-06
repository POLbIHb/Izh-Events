package com.gmail.illobikol.vk_api_playbill.models

import android.util.Log
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKEventsRequest (VKIdList: List<VKEventIdModel>) : VKRequest<List<VKEventModel>>("groups.getById") {
    init {
        addParam("access_token", "227519E88F40D7221DC405A40BC82F755FA925B5")
        addParam("group_ids", VKIdList.map { it.id })
        addParam("fields", "description,start_date,finish_date")
        Log.e("TAG", VKIdList.map { it.id }.toString())
    }

    override fun parse(r: JSONObject): List<VKEventModel> {
        val users = r.getJSONArray("response")
        val result = ArrayList<VKEventModel>()
        for (i in 0 until users.length()) {
            result.add(VKEventModel.parse(users.getJSONObject(i)))
            println(result)
        }
        return result
    }
}