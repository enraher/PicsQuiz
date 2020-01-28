package com.pics.quiz.repositories

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.pics.quiz.model.User
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * Created by enraher on 23/01/20.
 */

class PreferencesManager(context: Context) {
    private val preferences =
        PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
    var pushToken by PreferenceFieldDelegate.String(KEY_PUSH_TOKEN)
    var authToken by PreferenceFieldDelegate.String(KEY_AUTH_TOKEN)
    var musicOn by PreferenceFieldDelegate.Boolean(KEY_SOUND_ON)

    private var userJson by PreferenceFieldDelegate.String(KEY_USER)

    var user: User?
        get() {
            return if (userJson.isEmpty()) null else Gson().fromJson(userJson, User::class.java)
        }
        set(value) {
            userJson = Gson().toJson(value, User::class.java)
        }


    private sealed class PreferenceFieldDelegate<T>(protected val key: kotlin.String) :
        ReadWriteProperty<PreferencesManager, T> {

        class Boolean(key: kotlin.String, val def: kotlin.Boolean = false) :
            PreferenceFieldDelegate<kotlin.Boolean>(key) {

            override fun getValue(thisRef: PreferencesManager, property: KProperty<*>) =
                thisRef.preferences.getBoolean(key, def)

            override fun setValue(
                thisRef: PreferencesManager,
                property: KProperty<*>,
                value: kotlin.Boolean
            ) =
                thisRef.preferences.edit().putBoolean(key, value).apply()
        }

        class Int(key: kotlin.String, val def: kotlin.Int = 0) :
            PreferenceFieldDelegate<kotlin.Int>(key) {
            override fun getValue(thisRef: PreferencesManager, property: KProperty<*>) =
                thisRef.preferences.getInt(key, def)

            override fun setValue(
                thisRef: PreferencesManager,
                property: KProperty<*>,
                value: kotlin.Int
            ) =
                thisRef.preferences.edit().putInt(key, value).apply()
        }

        class String(key: kotlin.String, val def: kotlin.String = "") :
            PreferenceFieldDelegate<kotlin.String>(key) {
            override fun getValue(
                thisRef: PreferencesManager,
                property: KProperty<*>
            ): kotlin.String =
                thisRef.preferences.getString(key, def) ?: def

            override fun setValue(
                thisRef: PreferencesManager,
                property: KProperty<*>,
                value: kotlin.String
            ) =
                thisRef.preferences.edit().putString(key, value).apply()
        }
    }


    fun clearSessionData() {
        authToken = ""
        user = null
    }

    companion object {
        private const val KEY_PUSH_TOKEN = "push_token"
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_SOUND_ON = "sound_on"
        private const val KEY_USER = "user"
    }
}


