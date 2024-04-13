package com.example.communitycircuit1.models

import android.os.Parcel
import android.os.Parcelable

data class PendingContact(
    val id: String? = null,
    val name: String? = null,
    val imguri: String? = null,
    val tagName: String? = null,
    val category: String? = null,
    val campaignDuration: String? = null,
    val campaignBeneficiary: String? = null,

    val campaignAbout: String? = null,
    val campaignNumber: String? =null,


    var approved: Boolean = false // Initially, contacts are not approved
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),


        parcel.readByte() != 0.toByte() // Read boolean value from parcel
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(imguri)
        parcel.writeString(tagName)
        parcel.writeString(category)
        parcel.writeString(campaignDuration)
        parcel.writeString(campaignAbout)
        parcel.writeString(campaignBeneficiary)
        parcel.writeString(campaignNumber)




        parcel.writeByte(if (approved) 1 else 0) // Write boolean value to parcel
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PendingContact> {
        override fun createFromParcel(parcel: Parcel): PendingContact {
            return PendingContact(parcel)
        }

        override fun newArray(size: Int): Array<PendingContact?> {
            return arrayOfNulls(size)
        }
    }
}
