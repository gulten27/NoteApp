package com.gultendogan.gulten_dogan_odev7.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gultendogan.gulten_dogan_odev7.databinding.RecyclerRowBinding
import com.gultendogan.gulten_dogan_odev7.model.Note
import com.gultendogan.gulten_dogan_odev7.utils.Constants.Prefs.INFO
import com.gultendogan.gulten_dogan_odev7.utils.Constants.Prefs.OLD
import com.gultendogan.gulten_dogan_odev7.view.DetailsActivity

class NoteAdapter(private val noteList: MutableList<Note>) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    inner class NoteHolder(private val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.rvTextView.text = note.title
            binding.rvDescriptionTextView.text = note.description
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailsActivity::class.java).apply {
                    putExtra(INFO, OLD)
                    putExtra("id", note.id)
                }
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(noteList[position])
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun filterList(filteredList: List<Note>) {
        noteList.clear()
        noteList.addAll(filteredList)
        notifyDataSetChanged()
    }
}