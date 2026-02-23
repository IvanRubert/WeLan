package com.example.welan.ui.componentes

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

fun base64ToImageBitmap(base64String: String): ImageBitmap? {
    return try {
        // Si viene con "data:image/png;base64,..." lo limpiamos
        val cleanBase64 = base64String.substringAfter("base64,", base64String)

        val decodedBytes = Base64.decode(cleanBase64, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

        bitmap?.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


private fun imageUriToBase64(context: Context, uri: android.net.Uri): String {
    val inputStream = context.contentResolver.openInputStream(uri)
    val byteArrayOutputStream = ByteArrayOutputStream()

    inputStream?.let {
        val buffer = ByteArray(1024)
        var length: Int
        while (it.read(buffer).also { len -> length = len } != -1) {
            byteArrayOutputStream.write(buffer, 0, length)
        }
        it.close()
    }

    // Convertir a Base64
    return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
}
