package com.asusoft.todolistproject.recyclerview.todoitem

enum class ToDoItemType(val value: Int) {
    ITEM(0),
    ADD(1);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }

}