package com.example.procatfirst.repository.cache

import com.example.procatfirst.R
import com.example.procatfirst.repository.api.Item
import com.example.procatfirst.data.Tool
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent

class CatalogCache {

    private var toolsStorage: MutableList<Tool> = mutableListOf()

    companion object {
        val shared = CatalogCache()
        const val identifier = "[LocalStorage]"
    }

    fun addCatalogStuff(list: List<Item>) {
        if (toolsStorage.isEmpty()) {
            val resultList = mutableListOf<Tool>()
            for (i: Item in list) {
                val img: Int = if(i.name == "Молоток") {
                    R.drawable.hammer
                } else {
                    R.drawable.set
                }
                resultList.add(Tool(i.id, i.name, img, i.description, "", i.price))
            }
            toolsStorage.addAll(resultList)
            //intent
            NotificationCoordinator.shared.sendIntent(SystemNotifications.stuffAddedIntent)
        }
    }

    fun setCatalogStuff(cart: MutableList<Tool>) {
        toolsStorage = cart
    }

    fun getCatalogStuff(): MutableList<Tool> {
        return toolsStorage
    }



}