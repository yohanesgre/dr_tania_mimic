package com.neurafarm.drtaniamimic.data.sharedpreferences

interface AppSharedPreferences {
    fun putString(key: String, value: String)
    fun getString(key: String): String
    fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean
    fun putInt(key: String, value: Int)
    fun getInt(key: String): Int
    fun <T> putList(key: String, list: List<T>)
    fun <T> getList(key: String, cls: Class<Array<T>>): List<T>
    fun <T> putObj(key: String, obj: T)
    fun <T> getObj(key: String, cls: Class<T>): T

    fun clear(key: String)
    fun clear()

    fun resetSharedPreferences()
}
