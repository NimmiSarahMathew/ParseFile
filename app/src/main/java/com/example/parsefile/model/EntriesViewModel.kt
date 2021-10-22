package com.example.parsefile.model

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.parsefile.interfaces.DaggerKeyStoreAPIComponent
import com.example.parsefile.interfaces.KeyStoreAPIComponent
import com.example.parsefile.utils.KeyStoreAPI
import javax.inject.Inject

class EntriesViewModel: ViewModel(){
    @Inject
    lateinit var keystore: KeyStoreAPI

    init{
        val keyStoreAPIComponent : KeyStoreAPIComponent = DaggerKeyStoreAPIComponent.create()
        keystore = keyStoreAPIComponent.getKeyStore()
    }

    /**
     * list - list of Entries to be displayed on the 1st screen/fragment
     * treeUri - uri of the file selected
     */

    private var _list: HashMap<String, KeyStoreObj> = HashMap()
    private var _treeUri: Uri? = null

    val list: HashMap<String, KeyStoreObj>
    get() = _list

    val treeUri: Uri?
    get() = _treeUri


    fun setPath(uri: Uri?){
        _treeUri = uri
    }

    fun setList(contentResolver: ContentResolver) : HashMap<String, KeyStoreObj> {

        try {
            if(_treeUri!=null){
                _list = keystore.parseFile(contentResolver.openInputStream(_treeUri!!))
            }

        } catch (e: Exception) {
            Log.d("P12", "Exception " + e.toString())
        }

        return _list
    }

}