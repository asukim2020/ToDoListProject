package com.asusoft.todolistproject.realm.dto

import com.asusoft.todolistproject.realm.ToDoItem
import io.realm.Realm

class ToDoItemDto(
   var key: Long,
   var title: String,
   var isComplete: Boolean,
   var order: Long,
   var addFlag: Boolean = false
) {

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