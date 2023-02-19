package com.example.composenoteapp.model

import android.graphics.Color
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    var title:String,
    var content:String,
    var color:Long
) : Parcelable
