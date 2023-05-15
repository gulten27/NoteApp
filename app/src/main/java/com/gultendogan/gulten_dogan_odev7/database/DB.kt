package com.gultendogan.gulten_dogan_odev7.database

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import com.gultendogan.gulten_dogan_odev7.model.Note

class DB(context: Context) {

    private val database: SQLiteDatabase = context.openOrCreateDatabase("Notes", MODE_PRIVATE, null)

    init {
        createTable()
    }

    private fun createTable() {
        try {
            database.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY, notetitle VARCHAR, notedescription VARCHAR, notedate VARCHAR)")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun insertNote(noteTitle: String, noteDescription: String, noteDate: String) {
        try {
            val sqlString = "INSERT INTO notes (notetitle,notedescription,notedate) VALUES (?,?,?)"
            val statement = database.compileStatement(sqlString)
            statement.bindString(1, noteTitle)
            statement.bindString(2, noteDescription)
            statement.bindString(3, noteDate)
            statement.execute()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getNoteById(id: Int): Note? {
        var note: Note? = null
        val cursor = database.rawQuery("SELECT * FROM notes WHERE id = ?", arrayOf(id.toString()))
        val noteTitleIx = cursor.getColumnIndex("notetitle")
        val noteDescriptionIx = cursor.getColumnIndex("notedescription")
        val noteDateIx = cursor.getColumnIndex("notedate")

        while (cursor.moveToNext()) {
            val title = cursor.getString(noteTitleIx)
            val description = cursor.getString(noteDescriptionIx)
            val date = cursor.getString(noteDateIx)
            note = Note(title, id, description, date)
        }

        cursor.close()
        return note
    }

    fun deleteNoteById(id: Int) {
        try {
            val deleteQuery = "DELETE FROM notes WHERE id = $id"
            database.execSQL(deleteQuery)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}