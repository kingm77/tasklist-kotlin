package com.kingm.todo.tasklist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kingm.todo.R
import com.kingm.todo.form.FormActivity
import java.util.*
import kotlin.math.log

class TaskListFragment: Fragment() {

    private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )
    private val adapter = TaskListAdapter()

    val createTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        var task = result.data?.getSerializableExtra("task") as Task?
        Log.e("mod", "hom!!!")
        if (task != null) {
            addTask(task)
        }
        else
        {
            task = result.data?.getSerializableExtra("modTask") as Task?
            if (task != null) {
                taskList = taskList.map { (if (it.id == task.id) task else it) }
            }
            adapter.refreshAdapter(taskList)
        }
    }

    /*val modifTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = result.data?.getSerializableExtra("modTask") as Task?
        Log.e("mod", "hum!!!")

    }*/


    fun addTask(newTask: Task){
        taskList = taskList + newTask
        adapter.refreshAdapter(taskList)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
        adapter.currentList = taskList

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutView = view.findViewById<ConstraintLayout>(R.id.constraint_view)
        val recyclerView = view.findViewById<RecyclerView>(R.id.task_recycler_view)

        recyclerView.adapter = adapter
        val floatingButton = layoutView.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        floatingButton.setOnClickListener {
            val intent = Intent(context, FormActivity::class.java)
            createTask.launch(intent)
        }

        adapter.onClickDelete = { task ->
            Log.e("ldo", "nrm")
            Log.e("ldo", taskList.indexOf(task).toString())
            taskList = taskList.filter { taske: Task ->  taske != task}
            adapter.refreshAdapter(taskList)
        }

        adapter.onModify = {task ->
            Log.e("ldo", "modify")
            val fragmentIntent = Intent(context, FormActivity::class.java)
            fragmentIntent.putExtra("modifTask", task)
            createTask.launch(fragmentIntent)
        }
    }
}