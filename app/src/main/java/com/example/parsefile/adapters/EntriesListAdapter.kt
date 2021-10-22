package com.example.myapplication.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parsefile.R
import com.example.parsefile.interfaces.OnItemClickListener
import com.example.parsefile.model.KeyStoreObj

class EntriesListAdapter(var list: List<String>, listener: OnItemClickListener) :
    RecyclerView.Adapter<EntriesListAdapter.ViewHolder>() {
    var listener: OnItemClickListener

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View, clickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        val textView: TextView
        private val clickListener: OnItemClickListener
        override fun onClick(v: View) {
            clickListener.onItemClicked(bindingAdapterPosition)
        }

        init {
            this.clickListener = clickListener
            textView = view.findViewById<View>(R.id.entry_name) as TextView
            textView.setOnClickListener(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(listItem, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    init {
        this.listener = listener
    }

}