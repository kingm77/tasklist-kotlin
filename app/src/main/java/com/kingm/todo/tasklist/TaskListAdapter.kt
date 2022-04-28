package com.kingm.todo.tasklist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kingm.todo.R

class TaskListAdapter: RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    var currentList: List<Task> = emptyList()
    var onClickDelete: (Task) -> Unit = {}
    var onModify: (Task) -> Unit = {}

    inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val del = itemView.findViewById<ImageButton>(R.id.imageButton)
        val modify = itemView.findViewById<Button>(R.id.modif_btn)


        fun bind(task: Task){
            val titleText = itemView.findViewById<TextView>(R.id.task_title)
            val descText = itemView.findViewById<TextView>(R.id.task_desc)
            titleText.text = task.title
            descText.text = task.description
            del.setOnClickListener { onClickDelete(task) }
            modify.setOnClickListener{onModify(task)}
            Log.e("ldo", "nrm bind")
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)

        Log.e("ldo", "nrm create")

        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
       return currentList.size
    }

    fun refreshAdapter(nList: List<Task>){
        currentList = nList
        notifyDataSetChanged()
    }
}