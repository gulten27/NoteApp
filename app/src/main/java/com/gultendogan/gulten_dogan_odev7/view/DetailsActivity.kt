package com.gultendogan.gulten_dogan_odev7.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gultendogan.gulten_dogan_odev7.database.DB
import com.gultendogan.gulten_dogan_odev7.databinding.ActivityDetailsBinding
import com.gultendogan.gulten_dogan_odev7.utils.Constants.Prefs.INFO
import com.gultendogan.gulten_dogan_odev7.utils.Constants.Prefs.NEW
import com.gultendogan.gulten_dogan_odev7.utils.DateButtonClickListener
import com.gultendogan.gulten_dogan_odev7.utils.DeleteButtonClickListener

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var db: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DB(this)

        val info = intent.getStringExtra(INFO)

        if (info.equals(NEW)){
            binding.saveButton.visibility = View.VISIBLE
            binding.deleteButton.visibility = View.GONE
        }else{
            binding.saveButton.visibility = View.INVISIBLE
            binding.deleteButton.visibility = View.VISIBLE

            val selectedId = intent.getIntExtra("id",1)

            val note = db.getNoteById(selectedId)
            note?.let {
                binding.noteTitle.setText(it.title)
                binding.noteDescription.setText(it.description)
                binding.noteDate.setText(it.date)
            }

            binding.deleteButton.setOnClickListener(DeleteButtonClickListener(this, selectedId))
        }

        binding.dateButton.setOnClickListener(DateButtonClickListener(this, binding))

        binding.saveButton.setOnClickListener {
            val noteTitle = binding.noteTitle.text.toString()
            val noteDescription = binding.noteDescription.text.toString()
            val noteDate = binding.noteDate.text.toString()

            db.insertNote(noteTitle, noteDescription, noteDate)

            goToMain()
        }

    }

    fun goToMain(){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}