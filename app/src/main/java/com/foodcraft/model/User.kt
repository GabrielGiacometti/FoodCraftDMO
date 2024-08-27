package com.foodcraft.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.UUID

@Entity(tableName = "user")
data class User (
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var email: String,
    var name: String?= null,
    var password: String,
    val image: String?= null) : Serializable {


    @Ignore
    constructor() : this("", "", null, "", null)
}