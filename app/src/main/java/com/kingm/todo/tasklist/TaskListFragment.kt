package com.kingm.todo.tasklist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kingm.todo.R
import com.kingm.todo.form.FormActivity
import kotlinx.coroutines.launch


class TaskListFragment: Fragment() {

    private val viewModel: TasksListViewModel by viewModels()

    private val adapter = TaskListAdapter()

    private val createTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        var task = result.data?.getSerializableExtra("task") as Task?
        Log.e("mod", "hom!!!")
        if (task != null) {
            lifecycleScope.launch { // on lance une coroutine car `collect` est `suspend`
                viewModel.create(task!!)
            }
        }
        else
        {
            task = result.data?.getSerializableExtra("modTask") as Task?
            if (task != null) {
                lifecycleScope.launch { // on lance une coroutine car `collect` est `suspend`
                    viewModel.update(task)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutView = view.findViewById<ConstraintLayout>(R.id.constraint_view)
        val recyclerView = view.findViewById<RecyclerView>(R.id.task_recycler_view)

        lifecycleScope.launch { // on lance une coroutine car `collect` est `suspend`
            viewModel.tasksStateFlow.collect { newList ->
                adapter.refreshAdapter(newList)
            }
        }

        recyclerView.adapter = adapter
        val floatingButton = layoutView.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        floatingButton.setOnClickListener {
            val intent = Intent(context, FormActivity::class.java)
            createTask.launch(intent)
        }

        adapter.onClickDelete = { task ->
            Log.e("ldo", "remove")
            lifecycleScope.launch { // on lance une coroutine car `collect` est `suspend`
                viewModel.delete(task)
                viewModel.tasksStateFlow.collect { newList ->
                    adapter.refreshAdapter(newList)
                }
            }
        }

        adapter.onModify = {task ->
            Log.e("ldo", "modify")
            val fragmentIntent = Intent(context, FormActivity::class.java)
            fragmentIntent.putExtra("modifTask", task)
            createTask.launch(fragmentIntent)
        }
    }
}