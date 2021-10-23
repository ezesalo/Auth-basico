package com.ejercicios.authbasico.fragments.direccion

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ejercicios.authbasico.R
import com.ejercicios.authbasico.viewModels.direccion.DireccionViewModel

class DireccionFragment : Fragment() {

    companion object {
        fun newInstance() = DireccionFragment()
    }

    private lateinit var viewModel: DireccionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.direccion_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DireccionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}