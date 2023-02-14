package com.asusoft.todolistproject.realm.dto

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.asusoft.todolistproject.eventbus.GlobalBus
import com.asusoft.todolistproject.realm.ToDoItem
import com.asusoft.todolistproject.recyclerview.todoitem.ToDoItemHolder
import io.realm.Realm

class ToDoItemDto(
   var key: Long,
   var title: String,
   var isComplete: Boolean,
   var order: Long,
   var addFlag: Boolean = false
) {

   var textWatcher: TextWatcher = object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
         title = s.toString()
//         Log.d(ToDoItemHolder.TAG, "postTitle()")
         val map = HashMap<String, Any>()
         map[ToDoItemHolder.TAG] = ToDoItemHolder.TAG
         map[TITLE] = title
         map["dto"] = this@ToDoItemDto
         GlobalBus.post(map)
      }

      override fun afterTextChanged(s: Editable) {}
   }

   fun updateTitle(realm: Realm) {
      ToDoItem.updateTitle(realm, this)
   }

   fun updateIsComplete(realm: Realm) {
      ToDoItem.updateIsComplete(realm, this)
   }

   fun updateOrder(realm: Realm) {
      ToDoItem.updateOrder(realm, this)
   }

   companion object {
      const val TITLE = "title"
      const val IS_COMPLETE = "isComplete"
      const val ORDER = "order"
   }
}