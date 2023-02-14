package com.asusoft.todolistproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.asusoft.todolistproject.R
import com.asusoft.todolistproject.application.ItemApplication
import com.asusoft.todolistproject.databinding.ActivityToDoItemBinding
import com.asusoft.todolistproject.eventbus.GlobalBus
import com.asusoft.todolistproject.extension.settingActionBar
import com.asusoft.todolistproject.realm.ToDoItem
import com.asusoft.todolistproject.realm.dto.ToDoItemDto
import com.asusoft.todolistproject.recyclerview.helper.ItemTouchHelperCallback
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

        settingActionBar()

        realm = Realm.getInstance(ItemApplication.getRealmConfig())

        adapter = ToDoItemAdapter()
        adapter.initItem(realm, baseContext)
        binding.recyclerView.layoutManager = LinearLayoutManager(baseContext)
        binding.recyclerView.adapter = adapter

        val itemTouchHelperCallback = ItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(itemTouchHelperCallback)
        touchHelper.attachToRecyclerView(binding.recyclerView)
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

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(menuItem)
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

            event[ToDoItemAdapter.TAG] != null -> {
                when {
                    event[ItemTouchHelperCallback.ON_ITEM_DISMISS] != null -> {
                        (event["dto"] as ToDoItemDto).delete(realm)
                    }
                }
            }

            else -> {}
        }

    }
}