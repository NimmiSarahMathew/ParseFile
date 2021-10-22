package com.example.parsefile.model

import java.security.KeyPair
import java.security.cert.X509Certificate

data class KeyStoreObj(var certificate: X509Certificate, var keyPair:KeyPair?)