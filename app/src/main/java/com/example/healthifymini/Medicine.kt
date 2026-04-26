package com.example.healthifymini

import android.os.Parcel
import android.os.Parcelable

data class Medicine(
    val name: String,
    val details: String,
    val imageResId: Int,
    val price: Double,
    var isSelected: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(details)
        parcel.writeInt(imageResId)
        parcel.writeDouble(price)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Medicine> {
        override fun createFromParcel(parcel: Parcel): Medicine = Medicine(parcel)
        override fun newArray(size: Int): Array<Medicine?> = arrayOfNulls(size)
    }
}