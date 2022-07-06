package com.gmail.illobikol.vk_api_playbill.models

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKJoinRequest (id: String) : VKRequest<List<VKJoinModel>>("groups.join") {
    init {
        addParam("access_token", "227519E88F40D7221DC405A40BC82F755FA925B5")
        addParam("group_id", id)
    }

    override fun parse(r: JSONObject): List<VKJoinModel> {
        val result = ArrayList<VKJoinModel>()
        result.add(VKJoinModel.parse(r.getJSONObject("response")))
        return result
    }
}