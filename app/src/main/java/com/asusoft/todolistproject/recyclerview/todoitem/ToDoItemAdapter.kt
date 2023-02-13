package com.asusoft.todolistproject.recyclerview.todoitem

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asusoft.todolistproject.R
import com.asusoft.todolistproject.realm.ToDoItem
import com.asusoft.todolistproject.realm.dto.ToDoItemDto
import com.asusoft.todolistproject.recyclerview.EmptyViewHolder
import com.asusoft.todolistproject.recyclerview.ViewHolderInterface
import io.realm.Realm
import java.util.HashMap

class ToDoItemAdapter(
    var list: ArrayList<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        // TODO: - realm 적용하여 order 기준으로 정렬 함수 만들 필요 있음
        val itemIndex = event["index"] as Int
        val dto = event["dto"] as ToDoItemDto
        val completeIndex = list.indexOf(context.getString(R.string.add_item))

        val removeAt = list.removeAt(itemIndex) as ToDoItemDto
        notifyItemRemoved(itemIndex)

        if (itemIndex < completeIndex) {
            list.add(removeAt)
        } else {
            list.add(completeIndex, removeAt)
        }

        val index = list.indexOf(removeAt)
        notifyItemInserted(index)
//        notifyDataSetChanged()

        dto.updateIsComplete(realm)
    }

    fun updateTitle(realm: Realm, event: HashMap<String, Any>) {
        val dto = event["dto"] as ToDoItemDto
        dto.updateTitle(realm)
    }
}