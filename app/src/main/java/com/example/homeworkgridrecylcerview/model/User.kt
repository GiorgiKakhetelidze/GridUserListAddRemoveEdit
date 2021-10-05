package com.example.homeworkgridrecylcerview.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val image: Int,
    val name: String,
    val lastName: String,
    val vipStatus: Boolean = false
) :
    Parcelable
