package com.asusoft.todolistproject.recyclerview.todoitem

import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.asusoft.todolistproject.R
import com.asusoft.todolistproject.eventbus.GlobalBus
import com.asusoft.todolistproject.extension.onClick
import com.asusoft.todolistproject.realm.dto.ToDoItemDto
import com.asusoft.todolistproject.recyclerview.ViewHolderInterface
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ToDoItemHolder(
    private val view: View
): RecyclerView.ViewHolder(view), ViewHolderInterface {
    companion object {
        var TAG = ToDoItemHolder::class.java.simpleName ?: "ToDoItemHolder"
    }

    override fun bind(item: Any) {
        val data = item as? ToDoItemDto ?: return

        // TODO: - 체크박스 컬러 조정하기
        val checkBox = view.findViewById<CheckBox>(R.id.checkbox)
        checkBox.isChecked = data.isComplete
        checkBox.onClick {
            data.isComplete = !data.isComplete
            isCompleteToDoItem()
        }

        // TODO: - 완료시 취소선 추가하기
        val editText = view.findViewById<EditText>(R.id.title)
        editText.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .subscribe { charSequence ->
                data.title = charSequence.toString()
            }
    }

    private fun isCompleteToDoItem() {
        Log.d(TAG, "isCompleteToDoItem()")
        val map = HashMap<String, Any>()
        map[TAG] = TAG
        map["index"] = adapterPosition
        GlobalBus.post(map)
    }

}