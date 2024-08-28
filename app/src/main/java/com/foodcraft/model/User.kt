package com.foodcraft.model

import java.io.Serializable
import java.util.UUID
data class User(
    var id: String = "",
    val email: String = "",
    val name: String = "",
    var password: String = "",
    val image: String = ""
) : Serializable {

    constructor() : this(
        id = "",
        email = "",
        name = "",
        password = "",
        image = ""
    )
}
