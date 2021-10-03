package com.example.todo.models

data class ToDo(
    val task: String,
    var isChecked: Boolean = false
)