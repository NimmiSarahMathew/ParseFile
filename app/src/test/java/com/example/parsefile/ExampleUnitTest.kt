package com.example.parsefile

import androidx.core.net.toUri
import com.example.parsefile.utils.KeyStoreAPI
import org.hamcrest.core.StringContains
import org.junit.Test

import org.junit.Assert.*
import java.io.File
import java.io.FileInputStream
import java.security.KeyStore

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testValidFile(){
        //val result = KeyStoreAPI().parseFile(("/Users/nimmi/Downloads/nimmimathew.pfx"))
        val result = KeyStoreAPI().parseFile(FileInputStream("/Users/nimmi/Downloads/nimmimathew.pfx"))
        assertNotNull(result.get("bcf45262-8b81-4656-abdc-c62d3073f101"))
    }

    @Test
    fun testInvalidPath(){
        try{
            val result = KeyStoreAPI().parseFile(FileInputStream("/Users/nimmi/Downloads/nimmimathews.pfx"))
        }catch(e: Exception){
            assertThat(e.message,StringContains("No such file or directory"))
        }


    }

    @Test
    fun testEmptyCertificate(){
        val result = KeyStoreAPI().parseFile(FileInputStream("/Users/nimmi/Downloads/trial.p12"))
        assertTrue(result.isEmpty())
    }

    @Test
    fun testValidKeyPair(){
        val p12: KeyStore = KeyStore.getInstance("pkcs12")
        p12.load(FileInputStream(File("/Users/nimmi/Downloads/nimmimathew.pfx")), "android".toCharArray())
        val result = KeyStoreAPI().getKeyPair(p12,"bcf45262-8b81-4656-abdc-c62d3073f101")
        assertNotNull(result)
    }

    @Test
    fun testEmptyKeyPair(){
        val p12: KeyStore = KeyStore.getInstance("pkcs12")
        p12.load(FileInputStream(File("/Users/nimmi/Downloads/nimmimathew.pfx")), "android".toCharArray())
        val result = KeyStoreAPI().getKeyPair(p12,"Nimmi Mathew")
        assertNull(result)
    }
}