package com.example.todoapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private val tasklist = mutableListOf<String>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:TaskListViewAdaptors

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //to retrieve the stored data
        sharedPreferences=getSharedPreferences("SavedTaskList", Context.MODE_PRIVATE)

        recyclerView = findViewById(R.id.List_TodoList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        loadTasks()

        findViewById<Button>(R.id.btn_NewToDoCreation).setOnClickListener{
            val nextscreen = Intent(this,TaskDetails::class.java)
            startActivity(nextscreen)
        }

        findViewById<Button>(R.id.btn_Back).setOnClickListener{
            //retrieve()
            finish()
        }

    }

    //To load the list of tasks
    private fun loadTasks() {
        val savedTasks = sharedPreferences.getStringSet("task_list", emptySet()) ?: emptySet()
        tasklist.clear()
        tasklist.addAll(savedTasks.toList())

        adapter = TaskListViewAdaptors(tasklist, this) { index ->
            onTaskClicked(index)
        }

        recyclerView.adapter = adapter

    }

    private fun onTaskClicked(index:Int) {
        if (index in tasklist.indices) {
            val task = tasklist[index]
            val intent = Intent(this, EditTask::class.java)
            intent.putExtra("Edit_Text", task)
            startActivity(intent)
        } else {
            println("Error: Invalid index $index for tasklist size ${tasklist.size}")
        }
    }

    override fun onStart() {
        super.onStart()
        loadTasks()
    }
    override fun onRestart() {
        super.onRestart()
        loadTasks()
    }

}