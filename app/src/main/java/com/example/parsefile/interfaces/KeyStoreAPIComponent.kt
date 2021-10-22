package com.example.parsefile.interfaces

import com.example.parsefile.utils.KeyStoreAPI
import dagger.Component

@Component
interface KeyStoreAPIComponent {

    fun getKeyStore() : KeyStoreAPI
}