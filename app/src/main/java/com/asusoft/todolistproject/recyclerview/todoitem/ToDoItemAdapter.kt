package com.asusoft.todolistproject.recyclerview.todoitem

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asusoft.todolistproject.R
import com.asusoft.todolistproject.application.ItemApplication
import com.asusoft.todolistproject.eventbus.GlobalBus
import com.asusoft.todolistproject.realm.ToDoItem
import com.asusoft.todolistproject.realm.dto.ToDoItemDto
import com.asusoft.todolistproject.recyclerview.EmptyViewHolder
import com.asusoft.todolistproject.recyclerview.ViewHolderInterface
import com.asusoft.todolistproject.recyclerview.helper.ItemTouchHelperCallback
import com.asusoft.todolistproject.recyclerview.helper.ItemTouchHelperCallback.Companion.ON_ITEM_DISMISS
import io.realm.Realm
import java.util.HashMap

class ToDoItemAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperCallback.ItemTouchHelperAdapter {

    var list: ArrayList<Any> = ArrayList<Any>()

    companion object {
        val TAG = ToDoItemAdapter::class.java.simpleName ?: "ToDoItemAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        when (viewType) {
            ToDoItemType.ITEM.value -> {
                val view = inflater.inflate(R.layout.item_to_do_default, parent, false)
                return ToDoItemHolder(view)
            }
            ToDoItemType.ADD.value -> {
                val view = inflater.inflate(R.layout.item_to_do_add, parent, false)
                return ToDoItemAddHolder(view)
            }
        }

        return EmptyViewHolder(View(context))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderInterface) {
            val item = list[position]
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return getType(position)
    }

    private fun getType(position: Int): Int {
        return when (list[position]) {
            is ToDoItemDto -> ToDoItemType.ITEM.value
            is String -> ToDoItemType.ADD.value
            else -> 999
        }
    }

    fun initItem(realm: Realm, context: Context) {
        val toDoItemList = ToDoItem.selectAll(realm)
        val notCompleteList = toDoItemList.filter { !it.isComplete }
        notCompleteList.sortedBy { it.order }
        list.addAll(notCompleteList)

        list.add(context.getString(R.string.add_item))

        val completeList = toDoItemList.filter { it.isComplete }
        completeList.sortedBy { it.order }
        list.addAll(completeList)
    }

    fun addItem(realm: Realm, context: Context) {
        val index = list.indexOf(context.getString(R.string.add_item))

        for (item in list) {
            if (item is ToDoItemDto) {
                item.addFlag = false
            }
        }

        val dto = ToDoItem.create(realm, "")
        dto.addFlag = true
        list.add(index, ToDoItem.create(realm, ""))
        notifyItemInserted(index)
    }

    fun updateIsComplete(realm: Realm, context: Context, event: HashMap<String, Any>) {
        val itemIndex = event["index"] as Int
        val dto = event["dto"] as ToDoItemDto

        val removeAt = list.removeAt(itemIndex) as ToDoItemDto
        notifyItemRemoved(itemIndex)

        val insertIndex = getCompleteChangeIndex(context, dto, dto.isComplete)
        list.add(insertIndex, removeAt)
        notifyItemInserted(insertIndex)
        dto.updateIsComplete(realm)
    }

    private fun getCompleteChangeIndex(context: Context, dto: ToDoItemDto, isComplete: Boolean): Int {
        val itemList = ArrayList<ToDoItemDto>()

        for (item in list) {
            if (item is ToDoItemDto) {
                if (item.isComplete == isComplete)
                    itemList.add(item)
            }
        }

        var index = 0
        for (item in itemList) {
            if (item.order < dto.order)
                index++
        }

        if (isComplete)
            index += list.indexOf(context.getString(R.string.add_item)) + 1

        return index
    }

    fun updateTitle(realm: Realm, event: HashMap<String, Any>) {
        val dto = event["dto"] as ToDoItemDto
        dto.updateTitle(realm)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        // nothing
    }

    override fun onItemDismiss(position: Int) {
        if (list.indexOf(ItemApplication.getString(R.string.add_item)) == position) return

        val removeAt = list.removeAt(position)
        notifyItemRemoved(position)

        if (removeAt is ToDoItemDto) {
            val map = HashMap<String, Any>()
            map[TAG] = TAG
            map[ON_ITEM_DISMISS] = ON_ITEM_DISMISS
            map["dto"] = removeAt
            GlobalBus.post(map)
        }
    }
}