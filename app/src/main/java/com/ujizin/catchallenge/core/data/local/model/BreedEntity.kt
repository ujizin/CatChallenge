package com.ujizin.catchallenge.core.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breeds")
data class BreedEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String,
    @ColumnInfo("image_url")
    val imageUrl: String,
)
