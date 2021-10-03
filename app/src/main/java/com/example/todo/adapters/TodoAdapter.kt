package com.example.todo.adapters

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.models.Todo
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter(
    var todoList: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentTask = todoList[position]

        holder.itemView.apply {
            tvTask.text = todoList[position].task
            cbDone.isChecked = todoList[position].isChecked
            crossOut(tvTask, currentTask.isChecked)
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                crossOut(tvTask, isChecked)
                currentTask.isChecked = !currentTask.isChecked
            }
        }
    }

    override fun getItemCount() = todoList.size

    fun addTodo(todo: Todo) {
        todoList.add(todo)
        notifyItemInserted(todoList.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteDoneTodos() {
        todoList.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun crossOut(tvTask: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvTask.paintFlags = tvTask.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTask.paintFlags = tvTask.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}