package com.example.parsefile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.EntriesListAdapter
import com.example.parsefile.R
import com.example.parsefile.activities.MainActivity
import com.example.parsefile.databinding.FragmentFirstBinding
import com.example.parsefile.interfaces.OnItemClickListener
import com.example.parsefile.model.KeyStoreObj
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(),OnItemClickListener{

    var list: HashMap<String, KeyStoreObj> = HashMap()
    lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.button.setOnClickListener {

            (activity as MainActivity).chooseFile()
        }
        if((activity as MainActivity).viewModel.treeUri != null){
            setAdapter((activity as MainActivity).viewModel.list.keys.toList())
        }
        return binding.root
    }


    override fun onPause() {
        super.onPause()
        binding.progressBar.visibility = View.VISIBLE
    }


    override fun onResume() {
        super.onResume()

        if((activity as MainActivity).viewModel.treeUri == null || (activity as MainActivity).isCancelled
            || binding.recyclerView.isVisible || binding.emptyList.isVisible){
            binding.progressBar.visibility = View.GONE
        }
    }
    override fun onItemClicked(pos: Int) {
        val extras = Bundle()
        extras.putSerializable("HashMap", list)
        extras.putString("Alias", list.keys.toList()[pos])
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, extras)
    }


    public fun setAdapter(entries : List<String>){
        list = (activity as MainActivity).viewModel.list

        if(list.size ==0){
            binding.progressBar.visibility = View.GONE
            binding.emptyList.visibility = View.VISIBLE
            binding.recyclerView.visibility=View.INVISIBLE
        }else{
            val listener  = this
            binding.progressBar.visibility = View.GONE
            binding.emptyList.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            binding.recyclerView.apply { // set a LinearLayoutManager to handle Android
                // RecyclerView behavior
                layoutManager = LinearLayoutManager(activity as MainActivity)
                // set the custom adapter to the RecyclerView
                adapter = EntriesListAdapter(
                   entries,
                    listener
                ) }
        }
    }
}