package com.example.procatfirst.repository.cache

import com.example.procatfirst.R
import com.example.procatfirst.data.Item
import com.example.procatfirst.data.Tool
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent

class CatalogCache {

    private var toolsStorage: MutableList<Tool> = mutableListOf()
    private var currentTool: Tool = Tool(1, "?", "1", 1, false, 1, R.drawable.logo)

    companion object {
        val shared = CatalogCache()
        const val identifier = "[CatalogCache]"
    }

    fun addCatalogStuff(list: List<Item>) {
        if (toolsStorage.isEmpty()) {
            val resultList = mutableListOf<Tool>()
            for (i: Item in list) {
                resultList.add(Tool(i.id, i.name, i.description, i.price, false, 0, R.drawable.hammer))
            }
            toolsStorage.addAll(resultList)
            //intent
            NotificationCoordinator.shared.sendIntent(SystemNotifications.stuffAddedIntent)
        }
    }

    fun setCatalogStuff(cart: MutableList<Tool>) { //useless?
        toolsStorage = cart
    }

    fun getCatalogStuff(): List<Tool> {
        return toolsStorage
    }

    fun getCurrent(): Tool {
        return currentTool
    }

    fun setCurrent(tool : Tool){
        currentTool = tool
    }



}