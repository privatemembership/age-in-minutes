package com.example.ageinminutes

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnDatePicker.setOnClickListener{view ->
            clickDatePicker(view)
        }
    }


    fun clickDatePicker(view: View){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{
                view, selectedYear, selectedMonth, selectedDayOfMonth ->
            // Confirmation text
            (Toast.makeText(this,"The chosen year is $selectedYear, the month is $selectedMonth and the day is $selectedDayOfMonth ", Toast.LENGTH_LONG).show())

            // Displaying the date, the month starts counting from 0, so we need to add 1
            val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
            tvSelectedDate.setText(selectedDate)

            // Pattern used for displaying the date
            val sdf = SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH)
            // Convert from string to date format with parse method
            val theDate = sdf.parse(selectedDate)

            // Dividing the time with 60000 to get milliseconds, otherwise we can divide it with 1000 to get seconds
            val selectedDateInMinutes = theDate!!.time / 1000

            // Converting into a date object
            val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

            val currentDateToMinutes = currentDate!!.time / 1000

            // Calculating the difference
            val differenceInMinutes = currentDateToMinutes - selectedDateInMinutes

            tvSelectedDateInMinutes.setText(differenceInMinutes.toString())
        }, year,
            month,
            day)
        // setMaxDate for not letting you choose a date from future other that the current day
        dpd.datePicker.setMaxDate(Date().time - 86400000)
        dpd.show()
    }

}