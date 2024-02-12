package com.example.procatfirst.repository.cache

import com.example.procatfirst.data.Tool
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent
import com.example.procatfirst.repository.data_storage.DataStorage

class UserCartCache {

    private var toolsStorage: MutableList<Tool> = mutableListOf()

    companion object {
        val shared = UserCartCache()
        const val identifier = "[UserCartCache]"
    }

    fun addUserCartStuff(tool : Tool) {
        toolsStorage.add(tool)
        DataStorage.shared.setUserCartToMemory(toolsStorage)
        //intent
        //NotificationCoordinator.shared.sendIntent(SystemNotifications.stuffAddedIntent)

    }

    fun setUserCartStuff(cart: MutableList<Tool>) {
        toolsStorage = cart
    }

    fun getUserCartStuff(): MutableList<Tool> {
        return toolsStorage
    }

    fun removeUserCartStuff(tool : Tool) {
        toolsStorage.remove(tool)
        NotificationCoordinator.shared.sendIntent(SystemNotifications.delInCartIntent)
        DataStorage.shared.setUserCartToMemory(toolsStorage)
    }

}