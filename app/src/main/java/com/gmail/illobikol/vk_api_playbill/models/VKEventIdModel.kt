package com.gmail.illobikol.vk_api_playbill.models

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class VKEventIdModel(
    val id: Int = 0) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VKEventIdModel> {
        override fun createFromParcel(parcel: Parcel): VKEventIdModel {
            return VKEventIdModel(parcel)
        }

        override fun newArray(size: Int): Array<VKEventIdModel?> {
            return arrayOfNulls(size)
        }

        fun parse(json: JSONObject)
                = VKEventIdModel(id = json.optInt("id", 0))
    }

}
