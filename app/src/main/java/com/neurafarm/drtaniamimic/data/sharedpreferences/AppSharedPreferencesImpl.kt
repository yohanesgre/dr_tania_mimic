package com.neurafarm.drtaniamimic.data.sharedpreferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.neurafarm.drtaniamimic.common.Constants
import com.google.gson.Gson
import javax.inject.Inject

@SuppressLint("CommitPrefEdits")
class AppSharedPreferencesImpl @Inject constructor(var appContext: Context) :
    AppSharedPreferences {

    private val prefs: Lazy<SharedPreferences> = lazy {
        appContext.getSharedPreferences(
            Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE
        )
    }

    override fun putString(key: String, value: String) {
       prefs.value.edit().apply{
           putString(key, value)
           apply()
       }
    }
    override fun getString(key: String) = prefs.value.getString(key, "")!!

    override fun putBoolean(key: String, value: Boolean) {
        prefs.value.edit().apply{
            putBoolean(key, value)
            apply()
        }
    }
    override fun getBoolean(key: String): Boolean = prefs.value.getBoolean(key, false)

    override fun putInt(key: String, value: Int) {
        prefs.value.edit().apply{
            putInt(key, value)
            apply()
        }
    }

    override fun getInt(key: String): Int = prefs.value.getInt(key, 0)

    override fun <T> putList(key: String, list: List<T>) {
        val gson = Gson()
        val json = gson.toJson(list)
        putString(key, json)
    }

    override fun <T> putObj(key: String, obj: T) {
        val gson = Gson()
        val json = gson.toJson(obj)
        putString(key, json)
    }

    override fun <T> getList(key: String, cls: Class<Array<T>>): List<T> {
        val gson = Gson()
        val json = getString(key)
        val list = gson.fromJson(json, cls)
        return if (list != null) listOf(*list) else emptyList()
    }

    override fun <T> getObj(key: String, cls: Class<T>): T {
        val gson = Gson()
        val json = getString(key)
        return gson.fromJson(json, cls)
    }

    override fun clear(key: String) {
        prefs.value.edit().remove(key).apply()
    }

    override fun clear() {
        prefs.value.edit().clear().apply()
    }

    override fun resetSharedPreferences() {
        prefs.value.edit {
            clear()
            commit()
        }
    }
}
