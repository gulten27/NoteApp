package com.gultendogan.gulten_dogan_odev7.view

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.gultendogan.gulten_dogan_odev7.R
import com.gultendogan.gulten_dogan_odev7.databinding.ActivityDetailsBinding
import kotlinx.android.synthetic.main.delete_dialog.view.*
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = this.openOrCreateDatabase("Notes", MODE_PRIVATE, null)

        val intent = intent
        val info = intent.getStringExtra("info")
        if (info.equals("new")){
            binding.saveButton.visibility = View.VISIBLE
            binding.deleteButton.visibility = View.GONE
        }else{
            binding.saveButton.visibility = View.INVISIBLE
            binding.deleteButton.visibility = View.VISIBLE
            val selectedId = intent.getIntExtra("id",1)
            val cursor =database.rawQuery("SELECT * FROM notes WHERE id = ?", arrayOf(selectedId.toString()))
            val noteTitleIx = cursor.getColumnIndex("notetitle")
            val noteDescriptionIx = cursor.getColumnIndex("notedescription")
            val noteDateIx = cursor.getColumnIndex("notedate")

            while (cursor.moveToNext()){
                binding.noteTitle.setText(cursor.getString(noteTitleIx))
                binding.noteDescription.setText(cursor.getString(noteDescriptionIx))
                binding.noteDate.setText(cursor.getString(noteDateIx))
            }

            binding.deleteButton.setOnClickListener {

                val view = View.inflate(this, R.layout.delete_dialog,null)
                val builder = AlertDialog.Builder(this)
                builder.setView(view)
                val dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                view.delete_dialog_ok_btn.setOnClickListener{
                    val selectedId = intent.getIntExtra("id", 1)
                    val deleteQuery = "DELETE FROM notes WHERE id = $selectedId"
                    database.execSQL(deleteQuery)

                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
                view.delete_dialog_cancel_btn.setOnClickListener {
                    dialog.cancel()
                }

            }

            cursor.close()
        }

        binding.dateButton.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                binding.noteDate.setText(selectedDate)
            }

            val datePickerDialog = DatePickerDialog(
                this,
                dateSetListener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        binding.saveButton.setOnClickListener {

            val noteTitle = binding.noteTitle.text.toString()
            val noteDescription = binding.noteDescription.text.toString()
            val noteDate = binding.noteDate.text.toString()

            try {
                database.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY, notetitle VARCHAR, notedescription VARCHAR, notedate VARCHAR)")
                val sqlString = "INSERT INTO notes (notetitle,notedescription,notedate) VALUES (?,?,?)"
                val statement = database.compileStatement(sqlString)
                statement.bindString(1, noteTitle)
                statement.bindString(2, noteDescription)
                statement.bindString(3, noteDate)
                statement.execute()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

    }


}