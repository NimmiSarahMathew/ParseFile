package com.example.parsefile.activities

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.parsefile.R
import com.example.parsefile.fragments.FirstFragment
import com.example.parsefile.model.EntriesViewModel


class MainActivity : AppCompatActivity(){

    var isCancelled: Boolean = false
    val viewModel: EntriesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.content_main)
        supportActionBar?.setTitle("ParseFile")

    }

    override fun onCreateOptionsMenu(menu : Menu): Boolean{
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    public fun chooseFile() {
        val intent = Intent(Intent.ACTION_PICK)
        resultLauncher.launch(intent)
    }


    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data != null) {
                isCancelled = false
                viewModel.setPath(result.data?.data)
                Log.d("P12", "info : " + result.data?.data?.path)
                viewModel.setList(contentResolver)
                refreshForegroundFragment()
            }
        }else if(result.resultCode == RESULT_CANCELED){
            isCancelled = true
        }
    }
    fun refreshForegroundFragment() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val frag: FirstFragment = navHostFragment?.childFragmentManager?.fragments?.get(0) as FirstFragment
        frag.setAdapter(viewModel.list.keys.toList())
    }


}