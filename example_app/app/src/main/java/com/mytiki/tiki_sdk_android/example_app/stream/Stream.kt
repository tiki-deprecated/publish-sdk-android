package com.mytiki.tiki_sdk_android.example_app.stream

import android.os.Parcel
import android.os.Parcelable

data class Stream (
    val source: String,
    val data: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(source)
        parcel.writeString(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Stream> {
        override fun createFromParcel(parcel: Parcel): Stream {
            return Stream(parcel)
        }

        override fun newArray(size: Int): Array<Stream?> {
            return arrayOfNulls(size)
        }
    }
}