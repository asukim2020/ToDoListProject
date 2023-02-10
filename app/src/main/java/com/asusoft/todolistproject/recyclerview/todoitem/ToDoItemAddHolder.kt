package com.asusoft.todolistproject.recyclerview.todoitem

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.asusoft.todolistproject.eventbus.GlobalBus
import com.asusoft.todolistproject.extension.onClick
import com.asusoft.todolistproject.recyclerview.ViewHolderInterface
import org.greenrobot.eventbus.EventBus

class ToDoItemAddHolder(
    val view: View
): RecyclerView.ViewHolder(view), ViewHolderInterface {
    var TAG = ToDoItemAddHolder::class.java.simpleName

    override fun bind(item: Any) {
        view.onClick {
            addToDoItem()
        }
    }

    private fun addToDoItem() {
        val map = HashMap<String, Any>()
        map[TAG] = TAG
        GlobalBus.post(map)
    }
}