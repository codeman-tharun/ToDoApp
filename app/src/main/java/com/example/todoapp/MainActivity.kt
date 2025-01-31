package com.example.todoapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
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
            //loadTasks()
            //retrieve()
            finish()
        }

    }

    private fun retrieve() {
        val savedTasks = sharedPreferences.getStringSet("task_list", emptySet()) ?: emptySet()
        for(task in savedTasks){
            println(task)
        }
    }

    private fun loadTasks() {
        val savedTasks = sharedPreferences.getStringSet("task_list", emptySet()) ?: emptySet()
        tasklist.clear()
        tasklist.addAll(savedTasks.toList())

        adapter = TaskListViewAdaptors(tasklist)
        recyclerView.adapter = adapter

        if (tasklist.isEmpty()) {
            Toast.makeText(applicationContext, "No tasks found", Toast.LENGTH_LONG).show()
        }

    }
    override fun onStart() {
        super.onStart()
        loadTasks()
        //Toast.makeText(applicationContext, "onStart Called", Toast.LENGTH_LONG).show()
    }
    override fun onRestart() {
        super.onRestart()
        loadTasks()
        //Toast.makeText(applicationContext, "onRestart Called", Toast.LENGTH_LONG).show()
    }

}