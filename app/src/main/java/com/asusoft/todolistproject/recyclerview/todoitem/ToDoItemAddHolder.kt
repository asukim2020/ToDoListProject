package com.asusoft.todolistproject.recyclerview.todoitem

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.asusoft.todolistproject.eventbus.GlobalBus
import com.asusoft.todolistproject.extension.onClick
import com.asusoft.todolistproject.recyclerview.ViewHolderInterface
import org.greenrobot.eventbus.EventBus

class ToDoItemAddHolder(
    private val view: View
): RecyclerView.ViewHolder(view), ViewHolderInterface {
    companion object {
        val TAG = ToDoItemAddHolder::class.java.simpleName ?: "ToDoItemAddHolder"
    }

    override fun bind(item: Any) {
        view.onClick {
            addToDoItem()
        }
    }

    private fun addToDoItem() {
        Log.d(TAG, "addToDoItem()")
        val map = HashMap<String, Any>()
        map[TAG] = TAG
        GlobalBus.post(map)
    }
}