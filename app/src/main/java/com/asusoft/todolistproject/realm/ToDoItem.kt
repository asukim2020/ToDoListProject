package com.asusoft.todolistproject.realm

import android.util.Log
import com.asusoft.todolistproject.realm.dto.ToDoItemDto
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.kotlin.deleteFromRealm

@RealmClass
open class ToDoItem: RealmModel {

    @PrimaryKey
    private var key: Long = System.currentTimeMillis()

    private var title: String = ""
    private var isComplete: Boolean = false
    private var order: Long = System.currentTimeMillis()

    constructor() : super()
    private constructor(title: String, isComplete: Boolean) : super() {
        this.title = title
        this.isComplete = isComplete
    }

    fun insert(realm: Realm) {
        realm.beginTransaction()
        realm.insertOrUpdate(this)
        realm.commitTransaction()
    }

    fun getDto(): ToDoItemDto {
        return ToDoItemDto(key, title, isComplete, order)
    }

    companion object {
        val TAG = ToDoItemDto::class.java.simpleName ?: "ToDoItem"

        fun create(
            realm: Realm,
            title: String,
            isComplete: Boolean = false
        ): ToDoItemDto {
            val item = ToDoItem(title, isComplete)
            item.insert(realm)
            return item.getDto()
        }

        fun selectAll(realm: Realm): List<ToDoItemDto> {
            realm.beginTransaction()
            val itemList = realm.where(ToDoItem::class.java).findAll()
            Log.d(TAG, "selectAll count: " + itemList.count())
            realm.commitTransaction()
            return itemList.map { it.getDto() }
        }

        fun updateTitle(realm: Realm, dto: ToDoItemDto) {
//            Log.d(TAG, "updateTitle title: " + dto.title)
            realm.beginTransaction()
            val item = realm.where(ToDoItem::class.java).equalTo("key", dto.key).findFirst()
            item?.title = dto.title
            realm.commitTransaction()
        }

        fun updateIsComplete(realm: Realm, dto: ToDoItemDto) {
            realm.beginTransaction()
            val item = realm.where(ToDoItem::class.java).equalTo("key", dto.key).findFirst()
            item?.isComplete = dto.isComplete
            realm.commitTransaction()
        }

        fun updateOrder(realm: Realm, dto: ToDoItemDto) {
            realm.beginTransaction()
            val item = realm.where(ToDoItem::class.java).equalTo("key", dto.key).findFirst()
            item?.order = dto.order
            realm.commitTransaction()
        }

        fun delete(realm: Realm, dto: ToDoItemDto) {
            realm.beginTransaction()
            val item = realm.where(ToDoItem::class.java).equalTo("key", dto.key).findFirst()
            item?.deleteFromRealm()
            realm.commitTransaction()
        }
    }


}