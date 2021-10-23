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

        nombreRegistro = v.findViewById(R.id.nombreRegistroTxt)
        apellidoRegistro = v.findViewById(R.id.apellidoRegistroTxt)
        telefonoRegistro = v.findViewById(R.id.telefonoRegistroTxt)
        emailRegistro = v.findViewById(R.id.emailRegistroTxt)
        passwordRegistro = v.findViewById(R.id.passwordRegistroTxt)
        registroButton = v.findViewById(R.id.RegistroButton)
        rootLayout = v.findViewById(R.id.frameLayout2)

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
                val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                v.findNavController().navigate(action)
            }else{
                Snackbar.make(rootLayout, "Error en el registro. Verifique sus datos e intentelo nuevamente", Snackbar.LENGTH_LONG)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE).setBackgroundTint(
                        Color.parseColor("#E91E3C")).show()
            }
        })

        registroButton.setOnClickListener() {
            val nombre: String = nombreRegistro.text.toString()
            val apellido: String = apellidoRegistro.text.toString()
            val telefono: String = telefonoRegistro.text.toString()
            val email: String = emailRegistro.text.toString()
            val password: String = passwordRegistro.text.toString()

            val msgErrorNombre = viewModelRegistro.validateGenerales(nombre)
            val msgErrorApellido = viewModelRegistro.validateGenerales(apellido)
            val msgErrorTelefono = viewModelRegistro.validateGenerales(telefono)
            val msgErrorEmail = viewModelRegistro.validateEmail(email)
            val msgErrorPassword = viewModelRegistro.validatePassword(password)

          if (!msgErrorNombre.isNullOrEmpty()){
              nombreRegistro.error = msgErrorNombre
          }else if (!msgErrorApellido.isNullOrEmpty()){
              apellidoRegistro.error = msgErrorApellido
          }else if (!msgErrorTelefono.isNullOrEmpty()){
              telefonoRegistro.error = msgErrorTelefono
          }else if (!msgErrorEmail.isNullOrEmpty()){
              emailRegistro.error = msgErrorEmail
          }else if (!msgErrorPassword.isNullOrEmpty()){
              passwordRegistro.error = msgErrorPassword
          }else viewModelRegistro.registrar(nombre, apellido, telefono, email, password)

        }

            }

        }