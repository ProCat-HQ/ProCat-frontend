package com.example.procatfirst.repository.cache

import android.graphics.Bitmap
import com.example.procatfirst.data.Item
import com.example.procatfirst.data.Item2
import com.example.procatfirst.data.ItemFullPayload
import com.example.procatfirst.data.Tool

class CatalogCache {

    private var toolsStorage: MutableList<Item2> = mutableListOf()
    private var currentId: Int = -1
    private var images: MutableMap<String, Bitmap?> = HashMap()
    private var currentTool: ItemFullPayload? = null

    companion object {
        val shared = CatalogCache()
        const val identifier = "[CatalogCache]"
    }

    fun addCatalogStuff(list: List<Item2>, callback : () -> Unit) {
        if (toolsStorage.isEmpty()) {
            val resultList = mutableListOf<Item2>()
            for (i: Item2 in list) {
                val img = if (i.image == "") { "hammer.jpg" }
                else { i.image }
                val category = i.categoryId ?: 0
                resultList.add(Item2(i.id, i.name, i.description, i.price, i.isInStock, category, i.categoryName, img))
            }
            toolsStorage.addAll(resultList)
            //intent
            callback()
        }
    }

    fun setCatalogStuff(cart: MutableList<Item2>) { //useless?
        toolsStorage = cart
    }

    fun getCatalogStuff(): List<Item2> {
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