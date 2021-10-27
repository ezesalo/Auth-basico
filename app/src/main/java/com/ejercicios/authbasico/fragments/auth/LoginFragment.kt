package com.ejercicios.authbasico.fragments.auth

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.ejercicios.authbasico.R
import com.ejercicios.authbasico.viewModels.auth.LoginViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {

    lateinit var v: View
    lateinit var email2TxtLayout: TextInputLayout
    lateinit var email2TextInputEdit: TextInputEditText
    lateinit var password2TxtLayout: TextInputLayout
    lateinit var password2TextInputEdit: TextInputEditText
    lateinit var loginButton: Button
    lateinit var irARegistro: Button
    lateinit var rootLayout: ConstraintLayout
    lateinit var progressBar: ProgressBar
    private val viewModelLogin: LoginViewModel by viewModels()

   // lateinit var emailV2: EditText

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.login_fragment, container, false)
        email2TxtLayout = v.findViewById(R.id.emailInputLayOutTxt2)
        email2TextInputEdit = v.findViewById(R.id.emailRegistroTxt2)

        password2TxtLayout = v.findViewById(R.id.passInputLayOutTxt2)
        password2TextInputEdit = v.findViewById(R.id.passRegistroTxt2)
        loginButton = v.findViewById(R.id.loginButton)
        irARegistro = v.findViewById(R.id.segundo_text_registro)
        progressBar = v.findViewById(R.id.progressBar)
        rootLayout = v.findViewById(R.id.frameLayout)

     //   emailV2 = v.findViewById(R.id.emailLoginTxt2)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        progressBar.setVisibility(View.GONE)

        viewModelLogin.loginExitoso.observe(viewLifecycleOwner, Observer { result ->
            if (result){
                Snackbar.make(rootLayout, "Ingreso Exitoso", Snackbar.LENGTH_LONG)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE).setBackgroundTint(
                        Color.parseColor("#4CAF50")).show()
                progressBar.setVisibility(View.GONE)
                val action = LoginFragmentDirections.actionLoginFragmentToDireccionFragment()
                v.findNavController().navigate(action)
            }else{
                progressBar.setVisibility(View.GONE)
                Snackbar.make(rootLayout, "El ingreso no fue exitoso. Verifique sus datos", Snackbar.LENGTH_LONG).setAnimationMode(
                    BaseTransientBottomBar.ANIMATION_MODE_FADE).setBackgroundTint(
                        Color.parseColor("#E91E3C")).show()
            }
        })

        loginButton.setOnClickListener(){

            var email: String = email2TextInputEdit.text.toString()
            var password: String = password2TextInputEdit.text.toString()

            var emailValido = viewModelLogin.validateEmail(email)
            var passwordValida = viewModelLogin.validatePassword(password)

            sacarErrores(emailValido, passwordValida)

            if (viewModelLogin.validateLogin(emailValido, passwordValida)){
                progressBar.setVisibility(View.VISIBLE)
                viewModelLogin.ingresar(email, password)
            }else{
                asignarErrores(emailValido, passwordValida)
                Snackbar.make(rootLayout, "Campos invalidos. Verifique sus datos", Snackbar.LENGTH_LONG).setAnimationMode(
                    BaseTransientBottomBar.ANIMATION_MODE_FADE).setBackgroundTint(
                    Color.parseColor("#E91E3C")).show()
            }


        }

        irARegistro.setOnClickListener(){
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            v.findNavController().navigate(action)
        }
    }

    fun asignarErrores (email: Boolean, password: Boolean){
        if (!email) email2TxtLayout.error = viewModelLogin.msgErrorEmail
        if (!password) password2TxtLayout.error = viewModelLogin.msgErrorPassword
    }

    fun sacarErrores (email: Boolean, password: Boolean){
        if (email) email2TxtLayout.error = null
        if (password) password2TxtLayout.error = null
    }
}