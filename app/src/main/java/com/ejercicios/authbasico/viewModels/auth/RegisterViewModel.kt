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
    var registroExitoso = MutableLiveData<Boolean>()
    lateinit var msgErrorEmail: String
    lateinit var msgErrorPassword: String
    lateinit var msgErrorGeneral: String
    lateinit var msgErrorPhone: String

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


    fun validateEmail(email: String): Boolean {
        var emailValido: Boolean = false

        if (email.isEmpty()){
            msgErrorEmail = "Debe completar su email"
        }else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
            msgErrorEmail = "Debe completar un email valido"
        }else{
            emailValido = true
        }
        return emailValido
    }

    fun validatePhone (phone: String): Boolean{
        var phoneValido: Boolean = false

        if (phone.isEmpty()){
            msgErrorPhone = "Debe completar su celular"
        }else if (phone.length < 10 || phone.length > 10){
            msgErrorPhone = "Debe tener 10 digitos incluyendo el 11"
        }else if ((!phone[0].equals("1".toCharArray()[0], false)) || !phone[1].equals("1".toCharArray()[0], false)){
            msgErrorPhone = "Debe empezar con 11"
        }else{
            phoneValido = true
        }

        return phoneValido
    }

    fun validatePassword(pass: String): Boolean {
        var passwordValida: Boolean = false
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
            msgErrorPassword = "Debe completar su contraseña"
        }else if (!passwordRegex.matcher(pass).matches()){
            msgErrorPassword = "Contraseña demasiado debil, elija otra por favor."
        }else{
            passwordValida = true
        }
        return passwordValida
    }

    fun validateGenerales(texto: String): Boolean {

        var textValido: Boolean = false
        if (texto.isEmpty()){
            msgErrorGeneral = "Debe completar este campo"
        }else{
            textValido = true
        }
        return textValido
    }

    fun validateForm(nombre: Boolean, apellido: Boolean, telefono: Boolean,email: Boolean, password: Boolean ): Boolean{
        val result = arrayOf(nombre, apellido, telefono, email, password)

        if (false in result){
            return false
        }
        return true
    }
}