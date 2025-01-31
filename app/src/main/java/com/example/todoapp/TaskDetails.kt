package com.example.todoapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TaskDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //to retrieve the stored data
        val sharedPreferences: SharedPreferences =getSharedPreferences("SavedTaskList", Context.MODE_PRIVATE)
        val savedHistory = sharedPreferences.getStringSet("task_list", HashSet<String>())
        val taskset = savedHistory?.toMutableSet()

        //Button to save
        val savebutton=findViewById<Button>(R.id.Btn_Save)
        savebutton.setOnClickListener {
            val title_tx=findViewById<EditText>(R.id.ET_Title)
            val content_tx=findViewById<EditText>(R.id.ET_Content)
            val status_cb=findViewById<CheckBox>(R.id.CB_Status)
            val editor = sharedPreferences.edit()

            //to store the data
            val text=status_cb.isChecked.toString()+"_"+title_tx.text.toString()+"_"+content_tx.text.toString()
            taskset?.add(text)
            editor.putStringSet("task_list", taskset).apply()
            Toast.makeText(this, "Task Saved!", Toast.LENGTH_SHORT).show()

            //To exit after saving the task
            finish()
        }

    }
}