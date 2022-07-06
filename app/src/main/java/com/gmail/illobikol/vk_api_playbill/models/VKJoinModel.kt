package com.gmail.illobikol.vk_api_playbill.models

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class VKJoinModel(
    val response: String = "0") : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().toString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(response)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VKJoinModel> {
        override fun createFromParcel(parcel: Parcel): VKJoinModel {
            return VKJoinModel(parcel)
        }

        override fun newArray(size: Int): Array<VKJoinModel?> {
            return arrayOfNulls(size)
        }

        fun parse(json: JSONObject)
                = VKJoinModel(response = json.optString("response", ""))
    }

    fun stringId(idEvents: List<VKJoinModel>){

    }
}