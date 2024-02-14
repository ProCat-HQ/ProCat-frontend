package com.example.procatfirst.repository.cache

import com.example.procatfirst.data.Tool
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent
import com.example.procatfirst.repository.data_storage.DataStorage
import com.example.procatfirst.ui.personal.notifications.NotificationsScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserCartCache {

    private var toolsStorage: MutableList<Tool> = mutableListOf()

    companion object {
        val shared = UserCartCache()
        const val identifier = "[UserCartCache]"
    }

    fun addUserCartStuff(tool : Tool) {
        toolsStorage.add(tool)
        runBlocking {
            launch(Dispatchers.Default) {
                DataStorage.shared.setUserCartToMemory(toolsStorage)
            }
        }
    }

    fun setUserCartStuff(cart: MutableList<Tool>) {
        if (cart.isNotEmpty()) {
            toolsStorage = cart
            NotificationCoordinator.shared.sendIntent(SystemNotifications.cartLoaded)
        }
    }

    fun getUserCartStuff(): MutableList<Tool> {
        return toolsStorage
    }

    fun removeUserCartStuff(tool : Tool) {
        toolsStorage.remove(tool)
        runBlocking {
            launch(Dispatchers.Default) {
                DataStorage.shared.setUserCartToMemory(toolsStorage)
            }
        }
        NotificationCoordinator.shared.sendIntent(SystemNotifications.delInCartIntent)
    }

}