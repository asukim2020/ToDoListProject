package com.asusoft.todolistproject.realm

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.kotlin.deleteFromRealm

@RealmClass
open class ToDoItem: RealmModel {

    @PrimaryKey
    var key: Long = System.currentTimeMillis()

    var title: String = ""
    var isComplete: Boolean = false
    var order: Long = System.currentTimeMillis()

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
    
    fun update(realm: Realm, title: String, isComplete: Boolean) {
        realm.beginTransaction()
        this.title = title
        this.isComplete = isComplete
        realm.commitTransaction()
    }

    fun updateOrder(realm: Realm, order: Long) {
        realm.beginTransaction()
        this.order = order
        realm.commitTransaction()
    }

    fun delete(realm: Realm) {
        realm.beginTransaction()
        this.deleteFromRealm()
        realm.commitTransaction()
    }

    companion object {
        fun create(
            title: String,
            isComplete: Boolean = false
        ): ToDoItem {
            return ToDoItem(title, isComplete)
        }
    }


}