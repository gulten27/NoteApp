package com.gultendogan.gulten_dogan_odev7.utils

import android.app.DatePickerDialog
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gultendogan.gulten_dogan_odev7.databinding.ActivityDetailsBinding
import java.util.*

class DateButtonClickListener(private val activity: AppCompatActivity, private val binding: ActivityDetailsBinding) : View.OnClickListener {

    override fun onClick(v: View?) {
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
            binding.noteDate.setText(selectedDate)
        }

        val datePickerDialog = DatePickerDialog(
            activity,
            dateSetListener,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}