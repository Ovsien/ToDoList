package com.example.todo.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.models.ToDo

class TodoViewModel : ViewModel() {

    private val toDoList: MutableLiveData<MutableList<ToDo>> = MutableLiveData()

    init {
        toDoList.value = mutableListOf()
    }

    fun getTodoList() = toDoList
}