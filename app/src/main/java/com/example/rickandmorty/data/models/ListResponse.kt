package com.example.rickandmorty.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "rickmorty_property")
data class ListResponse(
    @SerializedName("results")
    @Embedded
    val details: List<Details>,
    @PrimaryKey(autoGenerate = true)
    val id: Int=1
)