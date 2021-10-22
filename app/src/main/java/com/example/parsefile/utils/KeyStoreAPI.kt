package com.example.parsefile.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.parsefile.model.KeyStoreObj
import java.io.*
import java.math.BigInteger
import java.security.*
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap


class KeyStoreAPI @Inject constructor(){


  fun parseFile(file: InputStream?) : HashMap<String, KeyStoreObj>{

      val certList: HashMap<String, KeyStoreObj> = HashMap()
      try {
          val p12: KeyStore = KeyStore.getInstance("pkcs12")
          //p12.load(FileInputStream(file), "android".toCharArray())
          p12.load(file,"android".toCharArray())
          for (alias in Collections.list(p12.aliases())) {
              val keyPair = getKeyPair(p12, alias);
              val certificate: Certificate = p12.getCertificate(alias)
              if (certificate !is X509Certificate) {
                  continue
              }

              certList.put(alias, KeyStoreObj(certificate, keyPair))
          }
      } catch (e: Exception) {
          //Log.d("P12", "Exception !! --- " + e.toString())
      }
      return certList
  }



    fun getKeyPair(keystore: KeyStore, alias: String): KeyPair? {
        try {
            val key = keystore.getKey(alias, "android".toCharArray())
            if (key is PrivateKey) {
                // Get certificate of public key
                val cert = keystore.getCertificate(alias)

                // Get public key
                val publicKey = cert.publicKey

                // Return a key pair
                return KeyPair(publicKey, key)
            }
        } catch (e: KeyStoreException) {
            Log.d("P12", "Exception !! --- " + e.toString())
        } catch (e: NoSuchAlgorithmException) {
            Log.d("P12", "Exception !! --- " + e.toString())
        } catch (e: UnrecoverableKeyException) {
            Log.d("P12", "Exception !! --- " + e.toString())
        }
        return null;
    }
}