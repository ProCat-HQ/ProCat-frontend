package com.example.procatfirst.repository.cache

import android.graphics.Bitmap
import com.example.procatfirst.data.Item
import com.example.procatfirst.data.ItemFullPayload
import com.example.procatfirst.data.Tool

class CatalogCache {

    private var toolsStorage: MutableList<Tool> = mutableListOf()
    private var currentId: Int = -1
    private var images: MutableMap<String, Bitmap?> = HashMap()
    private var currentTool: ItemFullPayload? = null

    companion object {
        val shared = CatalogCache()
        const val identifier = "[CatalogCache]"
    }

    fun addCatalogStuff(list: List<Item>, callback : () -> Unit) {
        if (toolsStorage.isEmpty()) {
            val resultList = mutableListOf<Tool>()
            for (i: Item in list) {
                val img = if (i.image == "") { "hammer.jpg" }
                else { i.image }
                val category = i.categoryId ?: 0
                resultList.add(Tool(i.id, i.name, i.description, i.price, i.isInStock, category, img))
            }
            toolsStorage.addAll(resultList)
            //intent
            callback()
        }
    }

    fun setCatalogStuff(cart: MutableList<Tool>) { //useless?
        toolsStorage = cart
    }

    fun getCatalogStuff(): List<Tool> {
        return toolsStorage
    }

    fun getCurrent(): ItemFullPayload? {
        return currentTool
    }

    fun setCurrent(tool : ItemFullPayload){
        currentTool = tool
    }

    fun getCurrentId(): Int {
        return currentId
    }

    fun setCurrentID(id: Int){
        currentId = id
    }

    fun getImage(name: String) : Bitmap? {
        return images[name]
    }

    fun setImage(name: String, img : Bitmap? ) {
        images[name] = img
    }

}