package com.gultendogan.gulten_dogan_odev7.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gultendogan.gulten_dogan_odev7.adapter.NoteAdapter
import com.gultendogan.gulten_dogan_odev7.databinding.ActivityMainBinding
import com.gultendogan.gulten_dogan_odev7.model.Note
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteList : ArrayList<Note>
    private lateinit var noteAdapter : NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteList = ArrayList<Note>()

        noteAdapter = NoteAdapter(noteList)
        binding.recyclerview.layoutManager = GridLayoutManager(this,2)
        binding.recyclerview.adapter = noteAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("info","new")
            startActivity(intent)
        }

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().toLowerCase(Locale.getDefault())
                val filteredList = ArrayList<Note>()

                for (note in noteList) {
                    if (note.title.toLowerCase(Locale.getDefault()).contains(searchText)) {
                        filteredList.add(note)
                    }
                }

                noteAdapter.filterList(filteredList)
            }
        })

        try {
            val database = this.openOrCreateDatabase("Notes", MODE_PRIVATE,null)
            val cursor = database.rawQuery("SELECT * FROM notes",null)
            val noteTitleIndex = cursor.getColumnIndex("notetitle")
            val idIndex = cursor.getColumnIndex("id")
            val description = cursor.getColumnIndex("notedescription")

            while (cursor.moveToNext()){
                val title = cursor.getString(noteTitleIndex)
                val id = cursor.getInt(idIndex)
                val description = cursor.getString(description)
                val note = Note(title,id,description)
                noteList.add(note)
            }

            noteAdapter.notifyDataSetChanged()

            cursor.close()

        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}