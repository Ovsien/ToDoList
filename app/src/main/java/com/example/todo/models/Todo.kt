package com.example.todo.models

data class Todo(
    val task: String,
    var isChecked: Boolean = false
)