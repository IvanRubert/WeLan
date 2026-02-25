package com.example.welan.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

object UserPreferencesKeys {
    val EMAIL = stringPreferencesKey("email")
    val TYPE = stringPreferencesKey("type")
}

suspend fun GuardarUsuario(context: Context, email: String, type: String) {
    context.dataStore.edit { prefs ->
        prefs[UserPreferencesKeys.EMAIL] = email
        prefs[UserPreferencesKeys.TYPE] = type
    }
}

suspend fun UsuarioJson(context: Context): String {
    val prefs = context.dataStore.data.first()
    return prefs[UserPreferencesKeys.EMAIL]!!
}

suspend fun UserType(context: Context): String {
    val prefs = context.dataStore.data.first()
    return prefs[UserPreferencesKeys.TYPE]!!
}

suspend fun UserExist(context: Context): Boolean {
    val prefs = context.dataStore.data.first()
    val email = prefs[UserPreferencesKeys.EMAIL]
    return !email.isNullOrEmpty()
}