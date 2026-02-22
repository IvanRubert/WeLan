package com.example.welan.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.welan.model.BusinessUserWeLan
import com.example.welan.model.CommonUserWeLan
import com.example.welan.network.WeLanGetApiService
import com.example.welan.ui.componentes.Encriptador
import com.example.welan.ui.componentes.reemplazararroba
import retrofit2.Response


interface WeLanRepository {
    suspend fun getCommonUserValidate(email: String, password: String): Result<CommonUserWeLan>
    suspend fun getCommonUser(email: String): Result<CommonUserWeLan>
    suspend fun getBusinessUserValidate(email: String, password: String): Result<BusinessUserWeLan>
    suspend fun getBusinessUser(email: String): Result<BusinessUserWeLan>
}

class NetworkWeLanRepository(
    private val weLanGetApiService: WeLanGetApiService
): WeLanRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCommonUserValidate(email: String, password: String): Result<CommonUserWeLan> {
        return try {
            var correo = reemplazararroba(email)
            var passwordEnc = Encriptador.cifrar(password)
            val respuesta = weLanGetApiService.getCommonUserValidate(correo, passwordEnc)
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
            val respuesta = weLanGetApiService.getCommonUser(correo)
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

    override suspend fun getBusinessUserValidate(email: String, organizacion: String): Result<BusinessUserWeLan> {
        return try {
            var correo = reemplazararroba(email)
            val respuesta = weLanGetApiService.getBusinessUserValidate(correo, organizacion)
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
            val respuesta = weLanGetApiService.getBusinessUser(correo)
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
}