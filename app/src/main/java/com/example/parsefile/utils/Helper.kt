package com.example.parsefile.utils

import android.util.Base64
import java.io.ByteArrayOutputStream
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher

object Helper {

    fun encryptByPublicKey(str: String, publicKey: PublicKey?): String {
        val transformation = "RSA"
        val ENCRYPT_MAX_SIZE = 117

        val byteArray = str.toByteArray()
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)

        // Define the buffer
        var temp: ByteArray? = null
        //current offset
        var offset = 0

        val outputStream = ByteArrayOutputStream()

        while (byteArray.size - offset > 0) {
            //The remaining part is larger than the maximum encrypted field, and the maximum length of 117 bytes is encrypted.
            if (byteArray.size - offset >= ENCRYPT_MAX_SIZE) {
                temp = cipher.doFinal(byteArray, offset, ENCRYPT_MAX_SIZE)
                // Offset increased by 117
                offset += ENCRYPT_MAX_SIZE
            } else {
                // If the remaining number of bytes is less than 117, then encrypt all remaining
                temp = cipher.doFinal(byteArray, offset, (byteArray.size - offset))
                offset = byteArray.size
            }
            outputStream.write(temp)
        }
        outputStream.close()
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }

    fun decryptByPrivateKey(str: String, privateKey: PrivateKey?): String {
        val transformation = "RSA"
        val DECRYPT_MAX_SIZE = 256
        val byteArray = Base64.decode(str, Base64.DEFAULT)
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)

        var temp: ByteArray? = null
        var offset = 0

        val outputStream = ByteArrayOutputStream()

        while (byteArray.size - offset > 0) {
            if (byteArray.size - offset >= DECRYPT_MAX_SIZE) {
                temp = cipher.doFinal(byteArray, offset, DECRYPT_MAX_SIZE)
                offset += DECRYPT_MAX_SIZE
            } else {
                temp = cipher.doFinal(byteArray, offset, (byteArray.size - offset))
                offset = byteArray.size
            }
            outputStream.write(temp)
        }
        outputStream.close()
        return String(outputStream.toByteArray())
    }


}