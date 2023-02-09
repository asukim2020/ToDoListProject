package com.asusoft.todolistproject.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EmptyViewHolder (
    val view: View
): RecyclerView.ViewHolder(view), ViewHolderInterface {

    override fun bind(item: Any) {

    }

}