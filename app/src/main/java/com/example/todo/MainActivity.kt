package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.adapters.TodoAdapter
import com.example.todo.models.ToDo
import com.example.todo.viewModels.TodoViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val toDoViewModel by lazy { ViewModelProvider(this).get(TodoViewModel::class.java) }

    private lateinit var toDoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toDoAdapter = TodoAdapter(mutableListOf())

        rvTodos.adapter = toDoAdapter
        rvTodos.layoutManager = LinearLayoutManager(this)

        toDoViewModel.getTodoList().observe(this, Observer {
            it?.let {
                toDoAdapter.refreshTodoList(it)
            }
        })

        btnAddTodo.setOnClickListener {
            val toDoTitle = etToDo.text.toString()

            if (toDoTitle.isNotEmpty()) {
                val toDo = ToDo(toDoTitle)
                toDoAdapter.addTodo(toDo)
                etToDo.text.clear()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btnDeleteDoneItems -> {
                toDoAdapter.deleteDoneTodos()
                Toast.makeText(this, "Выполненные задачи удалены", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}