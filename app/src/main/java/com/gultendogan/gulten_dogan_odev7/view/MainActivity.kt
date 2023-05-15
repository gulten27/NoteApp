package com.gultendogan.gulten_dogan_odev7.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gultendogan.gulten_dogan_odev7.adapter.NoteAdapter
import com.gultendogan.gulten_dogan_odev7.databinding.ActivityMainBinding
import com.gultendogan.gulten_dogan_odev7.model.Note
import com.gultendogan.gulten_dogan_odev7.utils.Constants.Prefs.INFO
import com.gultendogan.gulten_dogan_odev7.utils.Constants.Prefs.NEW
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
        binding.recyclerview.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerview.adapter = noteAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(INFO,NEW)
            startActivity(intent)
        }

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //
            }

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().toLowerCase(Locale.getDefault())
                noteAdapter.filterList(noteList.filter{it.title.toLowerCase().contains(searchText)} as ArrayList<Note>)
            }
        })

        try {
            val database = this.openOrCreateDatabase("Notes", MODE_PRIVATE,null)
            val cursor = database.rawQuery("SELECT * FROM notes",null)
            val noteTitleIndex = cursor.getColumnIndex("notetitle")
            val idIndex = cursor.getColumnIndex("id")
            val description = cursor.getColumnIndex("notedescription")
            val dateIndex = cursor.getColumnIndex("notedate")

            while (cursor.moveToNext()){
                val title = cursor.getString(noteTitleIndex)
                val id = cursor.getInt(idIndex)
                val description = cursor.getString(description)
                val date = cursor.getString(dateIndex)
                val note = Note(title,id,description,date)
                noteList.add(note)
            }

            noteAdapter.notifyDataSetChanged()

            cursor.close()

        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}