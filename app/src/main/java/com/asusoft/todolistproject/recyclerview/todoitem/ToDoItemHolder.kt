package com.asusoft.todolistproject.recyclerview.todoitem

import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.asusoft.todolistproject.R
import com.asusoft.todolistproject.realm.dto.ToDoItemDto
import com.asusoft.todolistproject.recyclerview.ViewHolderInterface
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ToDoItemHolder(
    val view: View
): RecyclerView.ViewHolder(view), ViewHolderInterface {

    override fun bind(item: Any) {
        val data = item as? ToDoItemDto ?: return

        val editText = view.findViewById<EditText>(R.id.title)
        editText.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .subscribe { charSequence ->
                data.title = charSequence.toString()
            }
    }

}