package com.ejercicios.authbasico.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Direccion (var userId: String, var departamento: String, var piso: String, var numero: String, var alias: String, var localidad: String, var codigoPostal: String,
                 var provincia: String, var nombreCompleto: String, var calle: String): Parcelable
        {
                constructor():this("","","","","","","","","","")
                constructor( userId: String,  departamento: String,  piso: String,  numero: String,  alias: String,  localidad: String,  codigoPostal: String,
                             provincia: String,  nombreCompleto: String,  calle: String, id: String) : this(){

                        var userId = userId
                        var departamento: String = departamento
                        var piso: String = piso
                        var numero: String = numero
                        var alias: String = alias
                        var localidad: String = localidad
                        var codigoPostal: String = codigoPostal
                        var provincia: String = provincia
                        var nombreCompleto: String = nombreCompleto
                        var calle: String = calle
                        var id: String = id
                             }

}
