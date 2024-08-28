package com.foodcraft.repository

import android.app.Application
import android.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.foodcraft.model.User
import com.foodcraft.viewmodel.UserViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONObject

class UserRepository (application: Application) {

    private val firestore = FirebaseFirestore.getInstance()
    private val userLiveData = MutableLiveData<User>()

    private val preference = PreferenceManager.getDefaultSharedPreferences(application)

    fun login(email: String, password: String): LiveData<User> {
        val liveData = MutableLiveData<User>(null)


        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val user = FirebaseAuth.getInstance().currentUser
                    val localId = user?.uid
                    val idToken = task.result?.user?.getIdToken(false)?.result?.token

                    if (localId != null && idToken != null) {

                        FirebaseFirestore.getInstance().collection("user")
                            .document(localId).get()
                            .addOnSuccessListener { documentSnapshot ->
                                val userData = documentSnapshot.toObject(User::class.java)
                                userData?.id = localId
                                userData?.password = idToken //

                                liveData.value = userData


                                preference.edit().putString(UserViewModel.USER_ID, localId).apply()


                                FirebaseFirestore.getInstance().collection("user")
                                    .document(localId).set(userData!!)
                            }
                            .addOnFailureListener { e ->
                                Log.e(this.toString(), "Erro ao recuperar os dados do Firestore", e)
                            }
                    }
                } else {
                    Log.e(this.toString(), "Erro ao fazer login: ${task.exception?.message}")
                }
            }

        return liveData
    }


    fun createUser(user: User) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val firebaseUser = FirebaseAuth.getInstance().currentUser
                    val localId = firebaseUser?.uid
                    val idToken = task.result?.user?.getIdToken(false)?.result?.token

                    if (localId != null && idToken != null) {
                        user.id = localId
                        user.password = idToken

                        FirebaseFirestore.getInstance().collection("user")
                            .document(localId).set(user)
                            .addOnSuccessListener {
                                Log.d(this.toString(), "Usuário ${user.email} cadastrado com sucesso.")
                            }
                            .addOnFailureListener { e ->
                                Log.e(this.toString(), "Erro ao salvar usuário no Firestore", e)
                            }
                    }
                } else {
                    Log.e(this.toString(), "Erro ao criar usuário: ${task.exception?.message}")
                }
            }
    }


    fun load(userId: String) : LiveData<User> {
        val liveData = MutableLiveData<User>()

        val userRef = firestore.collection("user").document(userId)

        userRef.get().addOnSuccessListener {
            val user = it.toObject(User::class.java)
            user?.id = it.id

            liveData.value = user
        }

        return liveData
    }
    fun setUser(user: User) {
        userLiveData.value = user
    }

    fun getUser(): LiveData<User> = userLiveData


    fun update(user: User) : Boolean {

        var updated = false

        val userRef = firestore.collection("user").document(user.id)

        userRef.set(user).addOnSuccessListener { updated = true }

        return updated
    }

    companion object {

        const val BASE_URL = "https://identitytoolkit.googleapis.com/v1/"

        const val SIGNUP = "accounts:signUp"

        const val SIGNIN = "accounts:signInWithPassword"

        const val PASSWORD_RESET = "accounts:sendOobCode"

        const val KEY = "?key=AIzaSyBlefjmuBIvtl9AuI2HkgFYqAd5M1llll4" // pegar nas Configurações do Projeto no Firebase - valor parecido com AIzaSyBxFjit4FD8NN5Mx8hTFQQxeVA1Pv2OUag

    }

}