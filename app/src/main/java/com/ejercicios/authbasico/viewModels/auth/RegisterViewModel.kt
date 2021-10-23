package com.ejercicios.authbasico.viewModels.auth

import android.util.Log
import androidx.core.util.PatternsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ejercicios.authbasico.entities.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class RegisterViewModel : ViewModel() {
    val db = Firebase.firestore
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val registroExitoso = MutableLiveData<Boolean>()

     fun registrar (nombre: String, apellido: String, telefono: String, email: String, password: String){

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(){
                if (it.isSuccessful){
                    val user = auth.currentUser
                    val dbUser: Usuario = Usuario(user?.uid, nombre, apellido, email, telefono, true, null, null)
                    db.collection("usuarios").document(dbUser.id!!).set(dbUser)
                        .addOnCompleteListener(){
                            if(it.isSuccessful){
                                registroExitoso.value = true
                                Log.d("Auth", "Se pudo guardar en la BD")
                            }else{
                                registroExitoso.value = false
                                Log.d("Auth", "No se ha podido guardar en la BD")
                            }
                        }
                    Log.d("Auth", "Se pudo registrar en AUTH")
                }else{
                    registroExitoso.value = false
                    Log.d("Auth", "No se ha podido registrar en AUTH")
                }
            }
    }


    //ver de borrar
    fun camposCompletos (nombre: String, apellido: String, telefono: String,email: String, password: String ): Boolean {
        var registroCompleto = false

        if(nombre.isNotEmpty() && apellido.isNotEmpty() && telefono.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
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
        val passwordRegex = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +        //at least 1 lower case letter
                    "(?=.*[A-Z])" +        //at least 1 upper case letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$"
        )


        if (pass.isEmpty()){
            passwordValida = "Debe completar su contraseña"
        }else if (!passwordRegex.matcher(pass).matches()){
            passwordValida = "Contraseña demasiado debil, elija otra por favor."
        }
        return passwordValida
    }

    fun validateGenerales(texto: String): String? {

        var textValido: String? = null

        if (texto.isEmpty()){
            textValido = "Debe completar este campo"
        }

        return textValido
    }
}