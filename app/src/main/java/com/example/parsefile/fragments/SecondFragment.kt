package com.example.parsefile.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.parsefile.model.KeyStoreObj
import com.example.parsefile.databinding.FragmentSecondBinding
import kotlin.collections.HashMap

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

 lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val alias:String = arguments?.getString("Alias", "abc").toString()
            binding.alias.setText(alias)

            val hashMap : HashMap<String, KeyStoreObj> = arguments?.getSerializable("HashMap") as HashMap<String, KeyStoreObj>
            val certificateDetails : KeyStoreObj? = hashMap.get(alias)
            val certificate = certificateDetails?.component1()

            binding.commonName.setText(certificate?.subjectDN?.name?.replace("CN=",""))
            binding.expiryDate.setText(certificate?.notBefore.toString() + " to \n" + certificate?.notAfter.toString())
            binding.subjectDN.setText(certificate?.issuerDN?.toString()?.replace("CN=",""))
            binding.algo.setText(certificate?.sigAlgName)


        } catch (e: Exception) {
            Log.d("P12", e.toString())
        }
    }
}