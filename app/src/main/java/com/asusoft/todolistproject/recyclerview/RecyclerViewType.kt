package com.asusoft.todolistproject.recyclerview

import com.asusoft.todolistproject.activity.ToDoItemActivity

enum class RecyclerViewType(val value: Int) {
    TO_DO_ITEM(0);

    companion object {
        fun getType(typeObject: Any): RecyclerViewType {
            return when(typeObject) {
                is ToDoItemActivity -> TO_DO_ITEM
                else -> TO_DO_ITEM
            }
        }
    }
}