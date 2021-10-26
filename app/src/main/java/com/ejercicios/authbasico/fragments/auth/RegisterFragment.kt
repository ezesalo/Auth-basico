package com.ejercicios.authbasico.fragments.auth

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.ejercicios.authbasico.R
import com.ejercicios.authbasico.viewModels.auth.RegisterViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    lateinit var v: View
    lateinit var nombreRegistro: EditText
    lateinit var apellidoRegistro: EditText
    lateinit var telefonoRegistro: EditText
    lateinit var emailRegistro: EditText
    lateinit var passwordRegistro: EditText
    lateinit var registroButton: Button
    lateinit var rootLayout: ConstraintLayout
    lateinit var nombre2TxtLayout: TextInputLayout
    lateinit var nombre2TextInputEdit: TextInputEditText

    lateinit var apellido2TxtLayout: TextInputLayout
    lateinit var apellido2TextInputEdit: TextInputEditText

    lateinit var telefono2TxtLayout: TextInputLayout
    lateinit var telefono2TextInputEdit: TextInputEditText

    lateinit var email2TxtLayout: TextInputLayout
    lateinit var email2TextInputEdit: TextInputEditText

    lateinit var password2TxtLayout: TextInputLayout
    lateinit var password2TextInputEdit: TextInputEditText


    private val viewModelRegistro: RegisterViewModel by viewModels()

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.register_fragment, container, false)

//        nombreRegistro = v.findViewById(R.id.nombreRegistroTxt)
//        apellidoRegistro = v.findViewById(R.id.apellidoRegistroTxt)
//        telefonoRegistro = v.findViewById(R.id.telefonoRegistroTxt)
//        emailRegistro = v.findViewById(R.id.emailRegistroTxt)
//        passwordRegistro = v.findViewById(R.id.passwordRegistroTxt)
        registroButton = v.findViewById(R.id.RegistroButton)
        rootLayout = v.findViewById(R.id.frameLayout2)
        nombre2TxtLayout = v.findViewById(R.id.nombreInputLayOutTxt2)
        nombre2TextInputEdit = v.findViewById(R.id.nombreRegistroTxt2)

        apellido2TxtLayout = v.findViewById(R.id.apellidoInputLayOutTxt2)
        apellido2TextInputEdit = v.findViewById(R.id.apellidoRegistroTxt2)

        telefono2TxtLayout = v.findViewById(R.id.telefonoInputLayOutTxt2)
        telefono2TextInputEdit = v.findViewById(R.id.telefonoRegistroTxt2)

        email2TxtLayout = v.findViewById(R.id.emailInputLayOutTxt2)
        email2TextInputEdit = v.findViewById(R.id.emailRegistroTxt2)

        password2TxtLayout = v.findViewById(R.id.passInputLayOutTxt2)
        password2TextInputEdit = v.findViewById(R.id.passRegistroTxt2)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        viewModelRegistro.registroExitoso.observe(viewLifecycleOwner, Observer { result ->
            if (result){
                Snackbar.make(rootLayout, "Registro Exitoso", Snackbar.LENGTH_LONG)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE).setBackgroundTint(
                        Color.parseColor("#4CAF50")).show()
                //val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                //v.findNavController().navigate(action)

                v.findNavController().backQueue
            }else{
                Snackbar.make(rootLayout, "Error en el registro. Verifique sus datos e inténtelo nuevamente", Snackbar.LENGTH_LONG)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE).setBackgroundTint(
                        Color.parseColor("#E91E3C")).show()
            }
        })

        registroButton.setOnClickListener() {


           // var nombre: String = nombreRegistro.text.toString()
            var nombre: String = nombre2TextInputEdit.text.toString()
            var apellido: String = apellido2TextInputEdit.text.toString()
            var telefono: String = telefono2TextInputEdit.text.toString()
            var email: String = email2TextInputEdit.text.toString()
            var password: String = password2TextInputEdit.text.toString()

            var nombreValido = viewModelRegistro.validateGenerales(nombre)
            var apellidoValido = viewModelRegistro.validateGenerales(apellido)
            var telefonoValido = viewModelRegistro.validatePhone(telefono)
            var emailValido = viewModelRegistro.validateEmail(email)
            var passwordValida = viewModelRegistro.validatePassword(password)

            sacarErrores(nombreValido, apellidoValido, telefonoValido, emailValido, passwordValida)

            if (viewModelRegistro.validateForm(nombreValido, apellidoValido, telefonoValido, emailValido, passwordValida)){
                viewModelRegistro.registrar(nombre, apellido, telefono, email, password)
            }else{
                asignarErrores(nombreValido, apellidoValido, telefonoValido, emailValido, passwordValida)
                Snackbar.make(rootLayout, "Campos invalidos. Verifique sus datos", Snackbar.LENGTH_LONG).setAnimationMode(
                    BaseTransientBottomBar.ANIMATION_MODE_FADE).setBackgroundTint(
                    Color.parseColor("#E91E3C")).show()
            }
        }

            }

    fun asignarErrores (nombre: Boolean, apellido: Boolean, telefono: Boolean,email: Boolean, password: Boolean){
        if (!nombre) nombre2TxtLayout.error = viewModelRegistro.msgErrorGeneral
        if (!apellido) apellido2TxtLayout.error = viewModelRegistro.msgErrorGeneral
        if (!telefono) telefono2TxtLayout.error = viewModelRegistro.msgErrorPhone
        if (!email) email2TxtLayout.error = viewModelRegistro.msgErrorEmail
        if (!password) password2TxtLayout.error = viewModelRegistro.msgErrorPassword
    }

    fun sacarErrores (nombre: Boolean, apellido: Boolean, telefono: Boolean,email: Boolean, password: Boolean){
        if (nombre) nombre2TxtLayout.error = null
        if (apellido) apellido2TxtLayout.error = null
        if (telefono) telefono2TxtLayout.error = null
        if (email) email2TxtLayout.error = null
        if (password) password2TxtLayout.error = null
    }

        }