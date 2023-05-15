package com.gultendogan.gulten_dogan_odev7.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gultendogan.gulten_dogan_odev7.databinding.RecyclerRowBinding
import com.gultendogan.gulten_dogan_odev7.model.Note
import com.gultendogan.gulten_dogan_odev7.view.DetailsActivity

class NoteAdapter(var noteList: ArrayList<Note>) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    class NoteHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.binding.rvTextView.text = noteList.get(position).title
        holder.binding.rvDescriptionTextView.text = noteList.get(position).description
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra("info","old")
            intent.putExtra("id",noteList.get(position).id)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun filterList(filteredList: ArrayList<Note>) {
        noteList = filteredList
        notifyDataSetChanged()
    }

}