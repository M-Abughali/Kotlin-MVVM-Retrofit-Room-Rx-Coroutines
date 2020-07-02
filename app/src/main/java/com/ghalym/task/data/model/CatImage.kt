package com.ghalym.task.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "CatImage")
data class CatImage(
    @SerializedName("height")
    val height: Int,

    @ColumnInfo(name = "id")
    @PrimaryKey
    @SerializedName("id")
    val id: String,

    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String,

    @ColumnInfo(name = "width")
    @SerializedName("width")
    val width: Int
) : Serializable