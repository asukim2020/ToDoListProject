package com.asusoft.todolistproject.recyclerview.todoitem

import android.content.res.ColorStateList
import android.graphics.Paint
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.asusoft.todolistproject.R
import com.asusoft.todolistproject.application.ItemApplication
import com.asusoft.todolistproject.customview.RecyclerEditText
import com.asusoft.todolistproject.eventbus.GlobalBus
import com.asusoft.todolistproject.extension.onClick
import com.asusoft.todolistproject.realm.dto.ToDoItemDto
import com.asusoft.todolistproject.recyclerview.ViewHolderInterface

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
        val editText = view.findViewById<RecyclerEditText>(R.id.title)

        checkBox.isChecked = dto.isComplete
        checkBox.onClick {
            dto.isComplete = !dto.isComplete
            postIsComplete(dto)
        }

        val states = arrayOf(intArrayOf(android.R.attr.state_enabled))
        val colors: IntArray = if (checkBox.isChecked) {
            editText.setTextColor(ItemApplication.getColor(R.color.lightFont))
            editText.paintFlags = editText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            intArrayOf(ItemApplication.getColor(R.color.lightFont))
        } else {
            editText.setTextColor(ItemApplication.getColor(R.color.font))
            editText.paintFlags = 0
            intArrayOf(ItemApplication.getColor(R.color.lightGray))
        }
        checkBox.buttonTintList = ColorStateList(states, colors)

        editText.clearTextChangedListeners()
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