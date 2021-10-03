package com.example.todo.adapters

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.models.ToDo
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter(
    var toDoList: MutableList<ToDo>
) : RecyclerView.Adapter<TodoAdapter.ToDoViewHolder>() {

    inner class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return ToDoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val currentTask = toDoList[position]

        holder.itemView.apply {
            tvTask.text = toDoList[position].task
            cbDone.isChecked = toDoList[position].isChecked
            crossOutCompletedTask(tvTask, currentTask.isChecked)
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                crossOutCompletedTask(tvTask, isChecked)
                currentTask.isChecked = !currentTask.isChecked
            }
        }
    }

    override fun getItemCount() = toDoList.size

    @SuppressLint("NotifyDataSetChanged")
    fun refreshTodoList(toDos: MutableList<ToDo>) {
        this.toDoList = toDos
        notifyDataSetChanged()
    }

    fun addTodo(toDo: ToDo) {
        toDoList.add(toDo)
        notifyItemInserted(toDoList.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteDoneTodos() {
        toDoList.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun crossOutCompletedTask(tvTask: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvTask.paintFlags = tvTask.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTask.paintFlags = tvTask.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}