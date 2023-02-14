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
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ToDoItemHolder(
    private val view: View,
    private val adapter: ToDoItemAdapter
): RecyclerView.ViewHolder(view), ViewHolderInterface {
    companion object {
        val TAG = ToDoItemHolder::class.java.simpleName ?: "ToDoItemHolder"
    }

    override fun bind(item: Any) {
        val dto = item as? ToDoItemDto ?: return

        // TODO: - 체크박스 컬러 조정하기
        val checkBox = view.findViewById<CheckBox>(R.id.checkbox)
        checkBox.isChecked = dto.isComplete
        checkBox.onClick {
            dto.isComplete = !dto.isComplete
            postIsComplete(dto)
        }

        // TODO: - 완료시 취소선 추가하기
        val editText = view.findViewById<EditText>(R.id.title)
        for (toDoItem in adapter.list) {
            if (toDoItem is ToDoItemDto) {
                editText.removeTextChangedListener(toDoItem.textWatcher)
            }
        }
        editText.setText(dto.title)
        editText.addTextChangedListener(dto.textWatcher)

        // TODO: - 포커스 기능 추가하기
        if (dto.addFlag) {
            editText.isFocusable = true
            editText.requestFocus()
        }
    }

    private fun postIsComplete(dto: ToDoItemDto) {
        Log.d(TAG, "postIsComplete()")
        val map = HashMap<String, Any>()
        map[TAG] = TAG
        map[ToDoItemDto.IS_COMPLETE] = dto.isComplete
        map["index"] = adapterPosition
        map["dto"] = dto
        GlobalBus.post(map)
    }

}