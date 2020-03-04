package com.exercie.leboncoin.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ParcelableAlbum(val id: Long,
                      val title: String,
                      val coverUrl: String) : Parcelable
