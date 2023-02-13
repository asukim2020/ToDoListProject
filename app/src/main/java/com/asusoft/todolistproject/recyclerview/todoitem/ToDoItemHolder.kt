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
    private val view: View
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
        // TODO: - recyclerview edit text 이슈 해결하기 change event 삭제 등록 필요 - datalogger 앱 참고
        val editText = view.findViewById<EditText>(R.id.title)
        editText.setText(dto.title)
        editText.textChanges()
            .debounce(0, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .subscribe { charSequence ->
                dto.title = charSequence.toString()
                postTitle(dto)
            }

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

    private fun postTitle(dto: ToDoItemDto) {
        Log.d(TAG, "postTitle()")
        val map = HashMap<String, Any>()
        map[TAG] = TAG
        map[ToDoItemDto.TITLE] = dto.title
        map["dto"] = dto
        GlobalBus.post(map)
    }

}