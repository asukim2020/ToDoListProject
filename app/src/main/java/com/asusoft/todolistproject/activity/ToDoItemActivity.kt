package com.asusoft.todolistproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.asusoft.todolistproject.R
import com.asusoft.todolistproject.application.ItemApplication
import com.asusoft.todolistproject.databinding.ActivityToDoItemBinding
import com.asusoft.todolistproject.eventbus.GlobalBus
import com.asusoft.todolistproject.realm.ToDoItem
import com.asusoft.todolistproject.realm.dto.ToDoItemDto
import com.asusoft.todolistproject.recyclerview.todoitem.ToDoItemAdapter
import com.asusoft.todolistproject.recyclerview.todoitem.ToDoItemAddHolder
import com.asusoft.todolistproject.recyclerview.todoitem.ToDoItemHolder
import io.realm.Realm
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.HashMap

class ToDoItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityToDoItemBinding
    private lateinit var realm: Realm

    private lateinit var adapter: ToDoItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToDoItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        realm = Realm.getInstance(ItemApplication.getRealmConfig())

        adapter = ToDoItemAdapter()
        adapter.initItem(realm, baseContext)
        binding.recyclerView.layoutManager = LinearLayoutManager(baseContext)
        binding.recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        GlobalBus.register(this)
    }

    override fun onStop() {
        super.onStop()
        GlobalBus.unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onEvent(event: HashMap<String, Any>) {

        when {
            event[ToDoItemAddHolder.TAG] != null -> adapter.addItem(realm, baseContext)

            event[ToDoItemHolder.TAG] != null -> {
                when {
                    event["isComplete"] != null -> adapter.updateIsComplete(realm, baseContext, event)
                    event["title"] != null -> adapter.updateTitle(realm, event)
                }
            }

            else -> {}
        }

    }
}