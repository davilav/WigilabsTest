package com.davilav.wigilabstest.data.local

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferenceHelper(
    application: Application
) {

    private val sharedPreferences: SharedPreferences = application.getSharedPreferences(PREFS_NAME,
        Context.MODE_PRIVATE
    )


    /**
     * Metodo que se encarga de guardar un cadena de caracteres en persistencia de datos
     * @param KEY_NAME
     * @param value
     */
    fun save(KEY_NAME: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_NAME, value)
        editor.apply()
    }

    /**
     * Metodo que se encarga de guardar un valor entero en persistencia de datos
     * @param KEY_NAME
     * @param value
     */
    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(KEY_NAME, value)
        editor.apply()
    }

    /**
     * Metodo que se encarga de guardar un valor booleano en persistencia de datos
     * @param KEY_NAME
     * @param value
     */
    fun save(KEY_NAME: String, value: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(KEY_NAME, value)
        editor.apply()
    }

    /**
     * Metodo que se encarga de guardar una lista en persistencia de datos
     * @param KEY_NAME
     * @param value
     */
    fun saveList(KEY_NAME: String, value: List<*>) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_NAME, Gson().toJson(value))
        editor.apply()
    }

    /**
     * Metodo que se encarga de guardar un objeto de usuario( mapeado de JSON) en persistencia de datos
     * @param value
     */
    fun saveUser(value: LoginResponse) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(Constants.KEY_USER, Gson().toJson(value))
        editor.apply()
    }
    /**
     * Metodo que se encarga de guardar objeto de usuario( mapeado de JSON) en persistencia de datos
     * @param value
     */
    fun saveInstitution(value: Data) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(Constants.KEY_INSTITUTION, Gson().toJson(value))
        editor.apply()
    }
    /**
     * Metodo que se encarga de obtener una cadena de caracteres mediante su llave asociada
     * @param KEY_NAME
     */

    fun getValueString(KEY_NAME: String): String? = sharedPreferences.getString(KEY_NAME, null)

    /**
     * Metodo que se encarga de obtener un valor entero mediante su llave asociada
     * @param KEY_NAME
     */
    fun getValueInt(KEY_NAME: String): Int = sharedPreferences.getInt(KEY_NAME, 0)

    /**
     * Metodo que se encarga de obtener un valor booleano mediante su llave asociada
     * @param KEY_NAME
     */

    fun getValueBoolean(KEY_NAME: String, defaultValue: Boolean): Boolean = sharedPreferences.getBoolean(KEY_NAME, defaultValue)

    /**
     * Metodo que se encarga de obtener un objeto de usuario( mapeado de JSON) mediante su llave asociada
     *
     */
    fun getUser(): LoginResponse {
        val data = sharedPreferences.getString(Constants.KEY_USER, null)
        return Gson().fromJson(data, object : TypeToken<LoginResponse>() {}.type)
    }
    /**
     * Metodo que se encarga de obtener un objeto de institucion( mapeado de JSON) mediante su llave asociada
     * @param value
     */
    fun getInstitution(): Data {
        val data = sharedPreferences.getString(Constants.KEY_INSTITUTION, null)
        return Gson().fromJson(data, object : TypeToken<Data>() {}.type)
    }
    /**
     * Metodo que se encarga de limpiar la persistencia de datos
     */

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val allPreferences = sharedPreferences.all
        for ((key) in allPreferences) {
            if (key != Constants.KEY_PARTNER && key != Constants.KEY_ROLE) {
                editor.remove(key).apply()
            }
        }
    }

    /**
     * Metodo que se encarga de remover un valor mediante su llave asociada
     * @param KEY_NAME
     */
    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(KEY_NAME)
        editor.apply()
    }

    companion object {
        const val PREFS_NAME = "PREFS_NAME"
    }
}