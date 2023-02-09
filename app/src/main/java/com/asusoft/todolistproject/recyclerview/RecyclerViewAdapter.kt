package com.asusoft.todolistproject.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asusoft.todolistproject.R
import com.asusoft.todolistproject.recyclerview.RecyclerViewType.*
import com.asusoft.todolistproject.recyclerview.todoitem.ToDoItemHolder

class RecyclerViewAdapter(
    typeObject: Any,
    var list: ArrayList<Any>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        var TAG = RecyclerViewAdapter::class.java.simpleName
    }

    private val type = RecyclerViewType.getType(typeObject)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return when(type) {
            TO_DO_ITEM -> {
                Log.d(TAG, "onCreateViewHolder(): $TO_DO_ITEM")
                val view = inflater.inflate(R.layout.item_to_do_default, parent, false)
                ToDoItemHolder(view)
            }
        }

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
        val item = list[position]

        return when(type) {
            TO_DO_ITEM -> {
                0
            }
        }

        return 0
    }
}