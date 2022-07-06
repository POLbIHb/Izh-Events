package com.gmail.illobikol.vk_api_playbill.models

import android.util.Log
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKEventsIdRequest () : VKRequest<List<VKEventIdModel>>("groups.search") {
    init {
        addParam("access_token", "227519E88F40D7221DC405A40BC82F755FA925B5")
        addParam("q", " ")
        addParam("city_id", "56")
        addParam("type", "event")
        addParam("future", "1")
        addParam("sort", "6")
        addParam("count", "200")
    }

    override fun parse(r: JSONObject): List<VKEventIdModel> {
        Log.e("TAG", "ss")
        val users = r.getJSONObject("response").getJSONArray("items")
        val result = ArrayList<VKEventIdModel>()
        for (i in 0 until users.length()) {
            result.add(VKEventIdModel.parse(users.getJSONObject(i)))
        }
        Log.e("TAG", result.toString())
        return result
    }
}