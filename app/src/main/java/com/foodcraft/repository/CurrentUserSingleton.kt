package com.foodcraft.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.foodcraft.model.User

object CurrentUserSingleton {
    private val userLiveData = MutableLiveData<User>()

    fun setUser(user: User) {
        userLiveData.value = user
    }

    fun getUser(): LiveData<User> = userLiveData
}
