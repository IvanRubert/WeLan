package com.example.welan.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.welan.model.BusinessUserEdit
import com.example.welan.model.BusinessUserWeLan
import com.example.welan.model.CommonUserEdit
import com.example.welan.model.CommonUserWeLan
import com.example.welan.model.CreateBusinessUserDTO
import com.example.welan.model.CreateCommonUserDTO
import com.example.welan.model.Envio
import com.example.welan.model.Foro
import com.example.welan.model.MensajesData
import com.example.welan.model.NoticiDTO
import com.example.welan.model.Producto
import com.example.welan.model.ProductoOServicio
import com.example.welan.model.SerOProd
import com.example.welan.model.Servicio
import com.example.welan.model.Unidad
import com.example.welan.network.ForoWeLanGetApiService
import com.example.welan.network.MensajesWelanGetApiService
import com.example.welan.model.chatDTO
import com.example.welan.model.sendchatDTO
import com.example.welan.network.GetChatsWelanApiService
import com.example.welan.network.NoticiasWeLanGetApiService
import com.example.welan.network.PostChatApiService
import com.example.welan.network.SerOProdWeLanGetApiService
import com.example.welan.network.SerOProdWeLanPostApiService
import com.example.welan.network.UserWeLanGetApiService
import com.example.welan.network.UserWeLanPostApiService
import com.example.welan.network.UserWeLanPutApiService
import com.example.welan.ui.componentes.Encriptador
import com.example.welan.ui.componentes.reemplazararroba
import java.time.LocalDateTime


interface WeLanRepository {

    suspend fun getChat(email: String,email2 : String) : Result<List<chatDTO>>
    suspend fun getCommonUserValidate(email: String, password: String): Result<CommonUserWeLan>
    suspend fun getCommonUser(email: String): Result<CommonUserWeLan>
    suspend fun postCommonUser(user: CreateCommonUserDTO): Boolean

    suspend fun putCommonUserPassword(email: String, password: String): Result<CommonUserWeLan>
    suspend fun putCommonUser(user: CommonUserEdit): Result<CommonUserWeLan>
    suspend fun getBusinessUserValidate(email: String, certificate: String): Result<BusinessUserWeLan>
    suspend fun getBusinessUser(email: String): Result<BusinessUserWeLan>
    suspend fun postBusinessUser(user: CreateBusinessUserDTO): Boolean

    suspend fun putBusinessUser(user: BusinessUserEdit): Result<BusinessUserWeLan>

    suspend fun getSerOProd(): Result<List<SerOProd>>
    suspend fun getSerOProdFiltro(nombre: String): Result<List<SerOProd>>
    suspend fun postSerOProd(serOProd: ProductoOServicio): Boolean
    suspend fun getNoticias(): Result<List<NoticiDTO>>
    suspend fun getMensajes(email: String): Result<List<MensajesData>>
    suspend fun getForo(): Result<List<Foro>>
    suspend fun getForoFiltro(nombre: String): Result<List<Foro>>
    suspend fun postChat(chat : sendchatDTO) : chatDTO
}

class NetworkWeLanRepository(
    private val userWeLanGetApiService: UserWeLanGetApiService,
    private val userWeLanPostApiService: UserWeLanPostApiService,
    private val userWeLanPutApiService: UserWeLanPutApiService,
    private val serOProdWeLanGetApiService: SerOProdWeLanGetApiService,
    private val serOProdWeLanPostApiService: SerOProdWeLanPostApiService,
    private val noticiasWeLanGetApiService: NoticiasWeLanGetApiService,
    private val mensajesWeLanGetApiService: MensajesWelanGetApiService,
    private val foroWeLanGetApiService: ForoWeLanGetApiService
    private val chatsWelanApiService: GetChatsWelanApiService,
    private val chatsPostApiService : PostChatApiService
): WeLanRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCommonUserValidate(email: String, password: String): Result<CommonUserWeLan> {
        return try {
            val passwordEnc = Encriptador.cifrar(password)
            val respuesta = userWeLanGetApiService.getCommonUserValidate(email, passwordEnc)
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
    override suspend fun getCommonUser(email: String): Result<CommonUserWeLan> {
        return try {
            val respuesta = userWeLanGetApiService.getCommonUser(email)
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
    override suspend fun postCommonUser(user: CreateCommonUserDTO): Boolean {
        try {
            val passwordEnc = Encriptador.cifrar(user.passHash)
            user.passHash = passwordEnc
            val respuesta = userWeLanPostApiService.postCommonUser(user)
            if (respuesta.isSuccessful) {
               return true
            } else {
               return false
            }
        } catch (e: Exception) {
            return false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun putCommonUserPassword(email: String, password: String): Result<CommonUserWeLan> {
        return try {
            val passwordEnc = Encriptador.cifrar(password)
            val respuesta = userWeLanPutApiService.putCommonUserpassword(email, passwordEnc)
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
    override suspend fun putCommonUser(user: CommonUserEdit): Result<CommonUserWeLan> {
        return try {
            val respuesta = userWeLanPutApiService.putCommonUser(user)
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

    override suspend fun getBusinessUserValidate(email: String, certificate: String): Result<BusinessUserWeLan> {
        return try {
            val respuesta = userWeLanGetApiService.getBusinessUserValidate(email, certificate)
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
            val respuesta = userWeLanGetApiService.getBusinessUser(email)
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

    override suspend fun postBusinessUser(user: CreateBusinessUserDTO): Boolean {
        return try {
            val respuesta = userWeLanPostApiService.postBusinessUser(user)
            respuesta.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun putBusinessUser(user: BusinessUserEdit): Result<BusinessUserWeLan> {
        return try {
            val respuesta = userWeLanPutApiService.putBusinessUser(user)
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
                            pathImage = seroprod.img_path,
                            nombre = seroprod.nombre,
                            descripcion = seroprod.descripcion?:"",
                            precio = seroprod.precio,
                            tipo_precio = tipoPrecio,
                            envio = envio
                        )
                    )
                } else {
                    lista.add(
                        Servicio(
                            seroprod.img_path,
                            seroprod.nombre,
                            seroprod.correo,
                            seroprod.descripcion?:"",
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
                            pathImage = seroprod.img_path,
                            nombre = seroprod.nombre,
                            descripcion = seroprod.descripcion?:"",
                            precio = seroprod.precio,
                            tipo_precio = tipoPrecio,
                            envio = envio
                        )
                    )
                } else {
                    lista.add(
                        Servicio(
                            seroprod.img_path,
                            seroprod.nombre,
                            seroprod.correo,
                            seroprod.descripcion?:"",
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

    override suspend fun postSerOProd(serOProd: ProductoOServicio): Boolean {
        return try {
            val respuesta = serOProdWeLanPostApiService.postSerOProd(serOProd)
            respuesta.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getNoticias(): Result<List<NoticiDTO>> {
        return try {
            val respuesta = noticiasWeLanGetApiService.getNoticias()
            if (respuesta.isSuccessful) {
                respuesta.body()?.let { noticia ->
                    Result.success(noticia)
                } ?: Result.failure(Exception("Respuesta vacia"))
            } else {
                Result.failure(Exception("Error: ${respuesta.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMensajes(email: String): Result<List<MensajesData>> {
        return try {
            val respuesta = mensajesWeLanGetApiService.getMensajes(email)
            if (respuesta.isSuccessful) {
                respuesta.body()?.let { mensaje ->
                    Result.success(mensaje)
                } ?: Result.failure(Exception("Respuesta vacia"))
            } else {
                Result.failure(Exception("Error: ${respuesta.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getForo(): Result<List<Foro>> {
        return try {
            val respuesta = foroWeLanGetApiService.getForos()
            if (respuesta.isSuccessful) {
                respuesta.body()?.let { forolist ->
                    Result.success(forolist)
                } ?: Result.failure(Exception("Respuesta vacia"))
            } else {
                Result.failure(Exception("Error: ${respuesta.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getForoFiltro(nombre: String): Result<List<Foro>> {
        return try {
            val respuesta = foroWeLanGetApiService.getForosFiltro(nombre)
            if (respuesta.isSuccessful) {
                respuesta.body()?.let { forolist ->
                    Result.success(forolist)
                } ?: Result.failure(Exception("Respuesta vacia"))
            } else {
                Result.failure(Exception("Error: ${respuesta.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun getChat(email: String, email2: String): Result<List<chatDTO>> {
        return try {
            val respuesta = chatsWelanApiService.getConversation(email,email2)
            if (respuesta.isSuccessful) {
                respuesta.body()?.let { chat ->
                    Result.success(chat)
                } ?: Result.failure(Exception("Respuesta vacia"))
            } else {
                Result.failure(Exception("Error: ${respuesta.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun postChat(chat : sendchatDTO): chatDTO{
        return try {
            val respuesta = chatsPostApiService.postChat (chat)
            if (respuesta.isSuccessful) {
                chatDTO(
                    correo = chat.correoOrigen,
                    mensaje = chat.message,
                    dateLastMessage = LocalDateTime.now()
                )
            } else {
                throw Exception("No se ha podido enviar el mensaje")
            }
        } catch (e: Exception) {
            e.message
        } as chatDTO
    }
}