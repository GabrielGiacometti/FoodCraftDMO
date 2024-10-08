package com.foodcraft.viewmodel

import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.foodcraft.model.User
import com.foodcraft.repository.UserRepository

class UserViewModel (application: Application) : AndroidViewModel(application) {

    private val usersRepository = UserRepository(getApplication())

    fun createUser(user: User) = usersRepository.createUser(user)

    fun updateUser(user: User) = usersRepository.update(user)

    fun login(email: String, password: String) : LiveData<User> = usersRepository.login(email, password)

    fun logout() = PreferenceManager.getDefaultSharedPreferences(getApplication()).let {
        it.edit().remove(USER_ID).apply()
    }

    fun isLogged(): LiveData<User> = PreferenceManager.getDefaultSharedPreferences(getApplication()).let {
        val id = it.getString(USER_ID, null)

        if(id.isNullOrEmpty())
            return MutableLiveData(null)

        return usersRepository.load(id)
    }

    companion object {
        val USER_ID = "USER_ID"
    }
}