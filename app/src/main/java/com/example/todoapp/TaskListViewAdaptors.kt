package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListViewAdaptors(private val tasks: MutableList<String>): RecyclerView.Adapter<TaskListViewAdaptors.DataViewer>() {
    class DataViewer(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.Text_TaskTitle)
        val taskContent: TextView = itemView.findViewById(R.id.Text_TaskContent)
        val taskStatus: TextView = itemView.findViewById(R.id.Field_Status)
        //val editButton: Button = itemView.findViewById(R.id.Btn_Edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewer {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.task_list_config,parent,false)
        return DataViewer(view)
    }

    override fun getItemCount(): Int {
        return tasks!!.size
    }

    override fun onBindViewHolder(holder: DataViewer, position: Int) {
        val task = tasks[position]
        println(tasks)
        val taskParts = task.split("_")
        if (taskParts.size == 3) {
            val status = taskParts[0].toBoolean()
            val title = taskParts[1]
            val content = taskParts[2]

            // Assign values to views
            holder.taskTitle.text = title
            holder.taskContent.text = content
            if (status.equals("true")) {
                holder.taskStatus.text = "Completed"
            }
            else{
                holder.taskStatus.text = "Yet To Do"
            }
        }

    }

}