package com.gmail.illobikol.vk_api_playbill.models
import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class VKEventModel(
    val id: Int = 0,
    val name: String = "",
    val screen_name: String = "",
    val is_closed: Int = 0,
    val type: String = "",
    val photo_50: String = "",
    val photo_100: String = "",
    val photo_200: String = "",
    val start_date: String = "",
    var finish_date: String = "",
    val description: String = "") : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(screen_name)
        parcel.writeInt(is_closed)
        parcel.writeString(type)
        parcel.writeString(photo_50)
        parcel.writeString(photo_100)
        parcel.writeString(photo_200)
        parcel.writeString(start_date)
        parcel.writeString(finish_date)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VKEventModel> {
        override fun createFromParcel(parcel: Parcel): VKEventModel {
            return VKEventModel(parcel)
        }

        override fun newArray(size: Int): Array<VKEventModel?> {
            return arrayOfNulls(size)
        }

        fun parse(json: JSONObject)
                = VKEventModel(id = json.optInt("id", 0),
            name = json.optString("name", ""),
            screen_name = json.optString("screen_name", ""),
            is_closed = json.optInt("is_closed", 0),
            type = json.optString("type", ""),
            photo_50 = json.optString("photo_50", ""),
            photo_100 = json.optString("photo_100", ""),
            photo_200 = json.optString("photo_200", ""),
            start_date = json.optString("start_date", ""),
            finish_date = json.optString("finish_date", ""),
            description = json.optString("description", ""))
    }
}
