package com.gultendogan.gulten_dogan_odev7.utils

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.gultendogan.gulten_dogan_odev7.R
import com.gultendogan.gulten_dogan_odev7.database.DB
import com.gultendogan.gulten_dogan_odev7.view.DetailsActivity
import kotlinx.android.synthetic.main.delete_dialog.view.*

class DeleteButtonClickListener(private val activity: AppCompatActivity, private val noteId: Int) : View.OnClickListener {

    override fun onClick(v: View?) {
        val view = View.inflate(activity, R.layout.delete_dialog, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        view.delete_dialog_ok_btn.setOnClickListener {
            // Yapılacak işlemler
            val db = DB(activity)
            db.deleteNoteById(noteId)
            dialog.dismiss()
            (activity as DetailsActivity).goToMain()
        }
        view.delete_dialog_cancel_btn.setOnClickListener {
            dialog.cancel()
        }
    }
}