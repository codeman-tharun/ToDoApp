package com.example.todoapp

import android.annotation.SuppressLint
import android.content.Context
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
import java.time.LocalDateTime

class EditTask : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var timest: String
    private lateinit var taskList: MutableList<String>
    private var taskIndex: Int = -1
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        sharedPreferences = getSharedPreferences("SavedTaskList", Context.MODE_PRIVATE)

        val titleEditText = findViewById<EditText>(R.id.ET_Title)
        val contentEditText = findViewById<EditText>(R.id.ET_Content)
        val statusCheckbox = findViewById<CheckBox>(R.id.CB_Status)

        taskList = sharedPreferences.getStringSet("task_list", mutableSetOf())!!.toMutableList()

        val edit_text=intent.getStringExtra("Edit_Text") ?: ""

        val taskParts = edit_text.split("_")
        if (taskParts.size == 4) {
            val status = taskParts[0].toBoolean()
            val title = taskParts[1]
            val content = taskParts[2]
            timest=taskParts[3]

            titleEditText.setText(title)
            contentEditText.setText(content)
            statusCheckbox.isChecked=status
            taskIndex = taskList.indexOf(edit_text)
        }

        //Update Button
        findViewById<Button>(R.id.Btn_Update).setOnClickListener {
            val text=statusCheckbox.isChecked.toString()+"_"+titleEditText.text.toString()+"_"+contentEditText.text.toString()+"_"+timest
            if (taskIndex != -1) {
                val updatedTask = text
                taskList[taskIndex] = updatedTask
                saveTasks()
                Toast.makeText(this, "Task Updated!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        //Delete Button
        findViewById<Button>(R.id.Btn_Delete).setOnClickListener {
            if (taskIndex != -1) {
                taskList.removeAt(taskIndex)
                saveTasks()
                Toast.makeText(this, "Task Deleted!", Toast.LENGTH_SHORT).show()
                finish()
            }

        }

    }
    private fun saveTasks() {
        sharedPreferences.edit().putStringSet("task_list", taskList.toSet()).apply()
    }
}