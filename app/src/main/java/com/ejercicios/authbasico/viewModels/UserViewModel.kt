package com.ejercicios.authbasico.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ejercicios.authbasico.entities.Direccion
import com.ejercicios.authbasico.entities.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects

class UserViewModel : ViewModel() {
    val db = FirebaseFirestore.getInstance()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var direccionesUser = MutableLiveData<MutableList<Direccion>??>()
    var idDireccionesUser = MutableLiveData<MutableList<String>>()
    var userDb = MutableLiveData<Usuario?>()


    fun getDirecciones(){

//        db.collection("direcciones")
//            .whereEqualTo("userId", auth.uid!!)
//            .get().addOnSuccessListener{ snapshot ->
//                if(snapshot != null){
//                    direccionesUser.value =  snapshot.toObjects()
//                }
//            }
//            .addOnFailureListener(){
//                direccionesUser.value = null
//            }


        db.collection("direcciones").get().addOnCompleteListener(){
            if (it.isSuccessful){
                var auxCollection = mutableListOf<Direccion>()
                var auxIds = mutableListOf<String>()
                for (document in it.result!!.documents){
                    if (document["userId"] == auth.uid){
                        var dirAux: Direccion
                        dirAux = document.toObject()!!
                        auxCollection.add(dirAux)
                        auxIds.add(document.id)
                    }
                }
                direccionesUser.value = auxCollection.toMutableList()
                idDireccionesUser.value = auxIds
            }
        }

    }

    fun getUser(){

        db.collection("usuarios").document(auth.currentUser!!.uid).get().addOnSuccessListener {
            if(it != null){
                userDb.value = it.toObject<Usuario>()
            }else{
                userDb.value = null
            }
        }
    }

    fun updateUser(field: String, value: String){

        db.collection("usuarios").document(auth.currentUser!!.uid).update(field, value).addOnSuccessListener {
        }
    }
}