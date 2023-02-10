package com.asusoft.todolistproject.eventbus

import org.greenrobot.eventbus.EventBus
import java.util.HashMap

object GlobalBus {
    var sBus: EventBus? = null

    private fun getBus(): EventBus {
        if (sBus == null) sBus = EventBus.getDefault()
        return sBus!!
    }

    fun register(any: Any) {
        if (!getBus().isRegistered(any)) {
            getBus().register(any)
        }
    }

    fun unregister(any: Any) {
        if (getBus().isRegistered(any)) {
            getBus().unregister(any)
        }
    }

    fun post(event: HashMap<String, Any>) {
        getBus().post(event)
    }
}