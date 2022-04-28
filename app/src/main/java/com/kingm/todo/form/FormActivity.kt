package com.kingm.todo.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.kingm.todo.R
import com.kingm.todo.tasklist.Task
import java.util.*


class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val taskToEdit = intent.getSerializableExtra("modifTask") as Task?
        Log.e("obj modif", taskToEdit.toString() )

        val titleEntry = findViewById<EditText>(R.id.titleE)

        titleEntry.setText(taskToEdit?.title ?: "")
        val descEntry = findViewById<EditText>(R.id.descE)

        descEntry.setText(taskToEdit?.description ?: "")
        val validate = findViewById<Button>(R.id.validate)

        validate.setOnClickListener{
            Log.e("lala","validate")
            val id = taskToEdit?.id ?: UUID.randomUUID().toString()

            if(titleEntry.text.isNotBlank()){
                val newTask = Task(id, title = titleEntry.text.toString())

                if(descEntry.text.isNotBlank())
                    newTask.description = descEntry.text.toString()
                if(taskToEdit == null){
                    intent.putExtra("task", newTask)
                }
                else{
                    intent.putExtra("modTask", newTask)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
            else {
                Toast.makeText(this,"title not specified",Toast.LENGTH_LONG).show()
            }

        }
    }
}