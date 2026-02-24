package com.example.welan.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.welan.model.BusinessUserWeLan
import com.example.welan.model.CommonUserWeLan
import com.example.welan.model.Envio
import com.example.welan.model.Producto
import com.example.welan.model.SerOProd
import com.example.welan.model.Servicio
import com.example.welan.model.Unidad
import com.example.welan.network.SerOProdWeLanGetApiService
import com.example.welan.network.UserWeLanGetApiService
import com.example.welan.network.UserWeLanPutApiService
import com.example.welan.ui.componentes.Encriptador
import com.example.welan.ui.componentes.reemplazararroba
import org.w3c.dom.Text


interface WeLanRepository {
    suspend fun getCommonUserValidate(email: String, password: String): Result<CommonUserWeLan>
    suspend fun getCommonUser(email: String): Result<CommonUserWeLan>
    suspend fun putCommonUserPassword(email: String, password: String): Result<CommonUserWeLan>
    suspend fun putCommonUser(user: CommonUserWeLan): Result<CommonUserWeLan>
    suspend fun getBusinessUserValidate(email: String, password: String): Result<BusinessUserWeLan>
    suspend fun getBusinessUser(email: String): Result<BusinessUserWeLan>

    suspend fun putBusinessUser(user: BusinessUserWeLan): Result<BusinessUserWeLan>

    suspend fun getSerOProd(): Result<List<SerOProd>>
    suspend fun getSerOProdFiltro(nombre: String): Result<List<SerOProd>>
}

class NetworkWeLanRepository(
    private val userWeLanGetApiService: UserWeLanGetApiService,
    private val userWeLanPutApiService: UserWeLanPutApiService,
    private val serOProdWeLanGetApiService: SerOProdWeLanGetApiService
): WeLanRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCommonUserValidate(email: String, password: String): Result<CommonUserWeLan> {
        return try {
            var correo = reemplazararroba(email)
            var passwordEnc = Encriptador.cifrar(password)
            val respuesta = userWeLanGetApiService.getCommonUserValidate(correo, passwordEnc)
            if (respuesta.isSuccessful) {
                respuesta.body()?.let { user ->
                    Result.success(user.copy(passHash = Encriptador.descifrar(user.passHash)))
                } ?: Result.failure(Exception("Respuesta vacia"))
            } else {
                Result.failure(Exception("Error: ${respuesta.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCommonUser(email: String): Result<CommonUserWeLan> {
        return try {
            var correo = reemplazararroba(email)
            val respuesta = userWeLanGetApiService.getCommonUser(correo)
            if (respuesta.isSuccessful) {
                respuesta.body()?.let { user ->
                    Result.success(user)
                } ?: Result.failure(Exception("Respuesta vacia"))
            } else {
                Result.failure(Exception("Error: ${respuesta.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun putCommonUserPassword(email: String, password: String): Result<CommonUserWeLan> {
        return try {
            var correo = reemplazararroba(email)
            var passwordEnc = Encriptador.cifrar(password)
            val respuesta = userWeLanPutApiService.putCommonUserpassword(correo, passwordEnc)
            if (respuesta.isSuccessful) {
                respuesta.body()?.let { user ->
                    Result.success(user.copy(passHash = Encriptador.descifrar(user.passHash)))
                } ?: Result.failure(Exception("Respuesta vacia"))
            } else {
                Result.failure(Exception("Error: ${respuesta.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun putCommonUser(user: CommonUserWeLan): Result<CommonUserWeLan> {
        return try {
            var passwordEnc = Encriptador.cifrar(user.passHash)
            val commonUser = CommonUserWeLan(
                user.pathImage,
                user.nombre,
                user.correo,
                user.intereses,
                passwordEnc,
            )
            val respuesta = userWeLanPutApiService.putCommonUser(commonUser.correo, commonUser)
            if (respuesta.isSuccessful) {
                respuesta.body()?.let { user ->
                    Result.success(user.copy(passHash = Encriptador.descifrar(user.passHash)))
                } ?: Result.failure(Exception("Respuesta vacia"))
            } else {
                Result.failure(Exception("Error: ${respuesta.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBusinessUserValidate(email: String, organizacion: String): Result<BusinessUserWeLan> {
        return try {
            var correo = reemplazararroba(email)
            val respuesta = userWeLanGetApiService.getBusinessUserValidate(correo, organizacion)
            if (respuesta.isSuccessful) {
                respuesta.body()?.let { user ->
                    Result.success(user)
                } ?: Result.failure(Exception("Respuesta vacia"))
            } else {
                Result.failure(Exception("Error: ${respuesta.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBusinessUser(email: String): Result<BusinessUserWeLan> {
        return try {
            var correo = reemplazararroba(email)
            val respuesta = userWeLanGetApiService.getBusinessUser(correo)
            if (respuesta.isSuccessful) {
                respuesta.body()?.let { user ->
                    Result.success(user)
                } ?: Result.failure(Exception("Respuesta vacia"))
            } else {
                Result.failure(Exception("Error: ${respuesta.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun putBusinessUser(user: BusinessUserWeLan): Result<BusinessUserWeLan> {
        return try {
            val respuesta = userWeLanPutApiService.putBusinessUser(user.correo, user)
            if (respuesta.isSuccessful) {
                respuesta.body()?.let { user ->
                    Result.success(user)
                } ?: Result.failure(Exception("Respuesta vacia"))
            } else {
                Result.failure(Exception("Error: ${respuesta.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSerOProd(): Result<List<SerOProd>> {
        return try {
            val respuesta = serOProdWeLanGetApiService.getSerOProd()
            if (!respuesta.isSuccessful) {
                return Result.failure(Exception("Error: ${respuesta.code()}"))
            }
            val body = respuesta.body() ?: return Result.failure(Exception("Respuesta vacía"))
            val lista = mutableListOf<SerOProd>()
            body.forEach { seroprod ->
                val tipoPrecio = when (seroprod.tipo_precio) {
                    "UNIDAD" -> Unidad.UNIDADES
                    "HORAS" -> Unidad.HORAS
                    "KILOGRAMOS" -> Unidad.KILOGRAMOS
                    else -> Unidad.UNIDADES
                }
                val envio = when (seroprod.envio) {
                    "NOENVIO" -> Envio.NOENVIO
                    "POSSIBILIDADENVIO" -> Envio.POSSIBILIDADENVIO
                    "OBLIGACIONENVIO" -> Envio.OBLIGACIONENVIO
                    else -> Envio.NOENVIO
                }
                if (seroprod.tipo == "Producto") {
                    lista.add(
                        Producto(
                            correo = seroprod.correo,
                            pathImage = seroprod.pathImage,
                            nombre = seroprod.nombre,
                            descripcion = seroprod.descripcion,
                            precio = seroprod.precio,
                            tipo_precio = tipoPrecio,
                            envio = envio
                        )
                    )
                } else {
                    lista.add(
                        Servicio(
                            seroprod.pathImage,
                            seroprod.nombre,
                            seroprod.correo,
                            seroprod.descripcion,
                            seroprod.precio,
                            tipo_precio = tipoPrecio,
                            envio = envio
                        )
                    )
                }
            }
            Result.success(lista)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSerOProdFiltro(nombre: String): Result<List<SerOProd>> {
        return try {
            val respuesta = serOProdWeLanGetApiService.getSerOProdFiltro(nombre)
            if (!respuesta.isSuccessful) {
                return Result.failure(Exception("Error: ${respuesta.code()}"))
            }
            val body = respuesta.body() ?: return Result.failure(Exception("Respuesta vacía"))
            val lista = mutableListOf<SerOProd>()
            body.forEach { seroprod ->
                val tipoPrecio = when (seroprod.tipo_precio) {
                    "UNIDAD" -> Unidad.UNIDADES
                    "HORAS" -> Unidad.HORAS
                    "KILOGRAMOS" -> Unidad.KILOGRAMOS
                    else -> Unidad.UNIDADES
                }
                val envio = when (seroprod.envio) {
                    "NOENVIO" -> Envio.NOENVIO
                    "POSSIBILIDADENVIO" -> Envio.POSSIBILIDADENVIO
                    "OBLIGACIONENVIO" -> Envio.OBLIGACIONENVIO
                    else -> Envio.NOENVIO
                }
                if (seroprod.tipo == "Producto") {
                    lista.add(
                        Producto(
                            correo = seroprod.correo,
                            pathImage = seroprod.pathImage,
                            nombre = seroprod.nombre,
                            descripcion = seroprod.descripcion,
                            precio = seroprod.precio,
                            tipo_precio = tipoPrecio,
                            envio = envio
                        )
                    )
                } else {
                    lista.add(
                        Servicio(
                            seroprod.pathImage,
                            seroprod.nombre,
                            seroprod.correo,
                            seroprod.descripcion,
                            seroprod.precio,
                            tipo_precio = tipoPrecio,
                            envio = envio
                        )
                    )
                }
            }
            Result.success(lista)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}