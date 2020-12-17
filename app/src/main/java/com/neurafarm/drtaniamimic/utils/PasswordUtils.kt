package com.neurafarm.drtaniamimic.utils

import com.neurafarm.drtaniamimic.common.Constants
import com.neurafarm.drtaniamimic.data.sharedpreferences.AppSharedPreferencesImpl
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec


object PasswordUtils {
    fun encryptAndSavePassword(strToEncrypt: String, appSharedPreferencesImpl: AppSharedPreferencesImpl): String {
        val plainText = strToEncrypt.toByteArray(Charsets.UTF_8)
        val keygen = KeyGenerator.getInstance("AES")
        keygen.init(256)
        val key = keygen.generateKey()
        saveSecretKey(key, appSharedPreferencesImpl)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val cipherText = cipher.doFinal(plainText)
        saveInitializationVector(cipher.iv, appSharedPreferencesImpl)

        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(cipherText)
        val strToSave = String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT))
        appSharedPreferencesImpl.putString(Constants.ENCRYPTED_PASSWORD, strToSave)

        return strToSave
    }

    fun getDecryptedPassword(appSharedPreferencesImpl: AppSharedPreferencesImpl, password:String): String {
        val bytes = android.util.Base64.decode(password, android.util.Base64.DEFAULT)
        val ois = ObjectInputStream(ByteArrayInputStream(bytes))
        val passwordToDecryptByteArray = ois.readObject() as ByteArray
        val decryptedPasswordByteArray = decrypt(passwordToDecryptByteArray, appSharedPreferencesImpl)

        val decryptedPassword = StringBuilder()
        for (b in decryptedPasswordByteArray) {
            decryptedPassword.append(b.toChar())
        }

        return decryptedPassword.toString()
    }

    private fun decrypt(dataToDecrypt: ByteArray, appSharedPreferencesImpl: AppSharedPreferencesImpl): ByteArray {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        val ivSpec = IvParameterSpec(getSavedInitializationVector(appSharedPreferencesImpl))
        cipher.init(Cipher.DECRYPT_MODE, getSavedSecretKey(appSharedPreferencesImpl), ivSpec)
        val cipherText = cipher.doFinal(dataToDecrypt)

        /*val sb = StringBuilder()
        for (b in cipherText) {
            sb.append(b.toChar())
        }
        Toast.makeText(context, "dbg decrypted = [" + sb.toString() + "]", Toast.LENGTH_LONG).show()*/

        return cipherText
    }

    private fun saveSecretKey(secretKey: SecretKey, appSharedPreferencesImpl: AppSharedPreferencesImpl) {
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(secretKey)
        val strToSave = String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT))
        appSharedPreferencesImpl.putString(Constants.SECRET_KEY, strToSave)
    }

    private fun getSavedSecretKey(appSharedPreferencesImpl: AppSharedPreferencesImpl): SecretKey {
        val strSecretKey = appSharedPreferencesImpl.getString(Constants.SECRET_KEY)
        val bytes = android.util.Base64.decode(strSecretKey, android.util.Base64.DEFAULT)
        val ois = ObjectInputStream(ByteArrayInputStream(bytes))
        val secretKey = ois.readObject() as SecretKey
        return secretKey
    }

    private fun saveInitializationVector(initializationVector: ByteArray, appSharedPreferencesImpl: AppSharedPreferencesImpl) {
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(initializationVector)
        val strToSave = String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT))
        appSharedPreferencesImpl.putString(Constants.INITIALIZATION_VECTOR, strToSave)
    }

    private fun getSavedInitializationVector(appSharedPreferencesImpl: AppSharedPreferencesImpl) : ByteArray {
        val strInitializationVector = appSharedPreferencesImpl.getString(Constants.INITIALIZATION_VECTOR)
        val bytes = android.util.Base64.decode(strInitializationVector, android.util.Base64.DEFAULT)
        val ois = ObjectInputStream(ByteArrayInputStream(bytes))
        val initializationVector = ois.readObject() as ByteArray
        return initializationVector
    }
}