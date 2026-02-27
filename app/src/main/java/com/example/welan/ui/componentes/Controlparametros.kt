package com.example.welan.ui.componentes

import android.os.Build
import androidx.annotation.RequiresApi
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import java.util.Base64
import org.passay.PasswordValidator
import org.passay.LengthRule
import org.passay.CharacterRule
import org.passay.EnglishCharacterData
import org.passay.PasswordData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Encriptador {
    private val ALGORITMO = "AES"
    // La llave debe tener 16, 24 o 32 caracteres
    private val LLAVE_SECRETA = "MiLlaveSuperSec1".toByteArray()

    @RequiresApi(Build.VERSION_CODES.O)
    fun cifrar(texto: String): String {
        val cipher = Cipher.getInstance(ALGORITMO)
        val keySpec = SecretKeySpec(LLAVE_SECRETA, ALGORITMO)
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)

        val cifradoBytes = cipher.doFinal(texto.toByteArray())
        return Base64.getEncoder().encodeToString(cifradoBytes)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun descifrar(textoCifrado: String): String {
        val cipher = Cipher.getInstance(ALGORITMO)
        val keySpec = SecretKeySpec(LLAVE_SECRETA, ALGORITMO)
        cipher.init(Cipher.DECRYPT_MODE, keySpec)

        val descifradoBytes = cipher.doFinal(Base64.getDecoder().decode(textoCifrado))
        return String(descifradoBytes)
    }
}

fun reemplazararroba(email: String): String {
    return email.replace("@", "%40")
}

fun esCorreoValido(correo: String): Boolean {
    val regex = Regex("^[A-Za-z]+@[A-Za-z]+\\.[A-Za-z]{2,}$")
    return regex.matches(correo)
}

fun compPassword(password: String): Boolean{
    val validator = PasswordValidator(
        listOf(
            LengthRule(8, 30),
            CharacterRule(EnglishCharacterData.UpperCase, 1),
            CharacterRule(EnglishCharacterData.LowerCase, 1),
            CharacterRule(EnglishCharacterData.Digit, 1),
            CharacterRule(EnglishCharacterData.Special, 1)
        )
    )

    val result = validator.validate(PasswordData(password))
    return result.isValid
}

@RequiresApi(Build.VERSION_CODES.O)
fun parseFecha(fecha: LocalDateTime?): String {
    if (fecha == null) return ""

    val formatter = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm")
    return fecha.format(formatter)
}