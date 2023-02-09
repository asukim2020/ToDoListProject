package com.asusoft.todolistproject.recyclerview.todoitem

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.asusoft.todolistproject.recyclerview.ViewHolderInterface

class ToDoItemHolder(
    val view: View
): RecyclerView.ViewHolder(view), ViewHolderInterface {

    override fun bind(item: Any) {

    }

}