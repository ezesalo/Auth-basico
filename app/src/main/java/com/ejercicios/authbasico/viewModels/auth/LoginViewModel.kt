package com.ejercicios.authbasico.viewModels.auth

import android.content.ContentValues
import android.util.Log
import android.util.Patterns
import androidx.core.util.PatternsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ejercicios.authbasico.entities.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    val loginExitoso = MutableLiveData<Boolean>()

    fun ingresar (email: String, password: String){

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(){
                if (it.isSuccessful){
                    loginExitoso.value = true
                    Log.d(ContentValues.TAG, "Login completado")
                }else{
                    loginExitoso.value = false
                    Log.d(ContentValues.TAG, "No se completo el Login")
                }
            }
    }

    fun camposCompletos (email: String, password: String ): Boolean {
        var registroCompleto = false

        if(email.isNotEmpty() && password.isNotEmpty()){
            registroCompleto = true
        }
        return registroCompleto
    }

    fun validateEmail(email: String): String? {
        var emailValido: String? = null

        if (email.isEmpty()){
            emailValido = "Debe completar su email"
        }else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
            emailValido = "Debe completar un email valido"
        }

        return emailValido
    }

    fun validatePassword(pass: String): String? {
        var passwordValida: String? = null

        if (pass.isEmpty()){
            passwordValida = "Debe completar su contraseña"
        }
        return passwordValida
    }

}