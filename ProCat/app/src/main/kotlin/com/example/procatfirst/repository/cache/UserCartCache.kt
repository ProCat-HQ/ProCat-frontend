package com.example.procatfirst.repository.cache

import android.util.Log
import com.example.procatfirst.data.CartItem
import com.example.procatfirst.data.CartPayload
import com.example.procatfirst.data.Tool
import com.example.procatfirst.data.ToolWithCnt

class UserCartCache {

    private var toolsStorage: MutableMap<Int, ToolWithCnt> = HashMap()

    companion object {
        val shared = UserCartCache()
        const val identifier = "[UserCartCache]"
    }

    fun addUserCartStuff(tool : Tool) {
        Log.v("AddTool", tool.toString())
        if (toolsStorage.containsKey(tool.id)) {
            toolsStorage[tool.id] = toolsStorage[tool.id]!!.copy(cnt = toolsStorage[tool.id]!!.cnt + 1)
        }
        else {
            toolsStorage[tool.id] = ToolWithCnt(tool.id, tool.name, tool.description, tool.price, tool.isInStock, tool.categoryId, tool.imageResId, 1)
        }
        Log.v("TOOLCache", toolsStorage.toString())
    }

    fun setUserCartStuff(cart: MutableMap<Int, ToolWithCnt>) {
        if (cart.isNotEmpty()) {
            toolsStorage = cart
        }
    }

    fun getUserCartStuff(): MutableMap<Int, ToolWithCnt> {
        return toolsStorage
    }

    fun getUserCartPayload(): CartPayload {
        Log.d("Cache", toolsStorage.toString())
        val set : MutableSet<CartItem> = mutableSetOf()
        for (tool in toolsStorage) {
            val key = tool.value
            set.add(CartItem(key.id, key.name, key.price, key.cnt, key.imageResId.toString())) //??
        }
        return CartPayload(set)
    }

    fun removeUserCartStuff(id : Int) {
        toolsStorage.remove(id)
    }

}