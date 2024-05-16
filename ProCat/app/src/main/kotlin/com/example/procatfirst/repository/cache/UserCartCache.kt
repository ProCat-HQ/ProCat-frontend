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

    fun setUserCartStuff(cart: CartPayload) {
        if (cart.items != null) {
            if (cart.items.isNotEmpty()) {
                for (item in cart.items) {
                    toolsStorage[item.id] = ToolWithCnt(item.id, item.name, "", item.price, true, 0, 0, item.count)
                }
            }
        }
    }

    fun getUserCartStuff(): MutableMap<Int, ToolWithCnt> {
        return toolsStorage
    }

    fun getUserCartPayload(): CartPayload {
        Log.d("Cache", toolsStorage.toString())
        val set : MutableSet<CartItem> = mutableSetOf()
        for (tool in toolsStorage) {
            val item = tool.value
            set.add(CartItem(item.id, item.name, item.price, item.cnt, item.imageResId.toString())) //??
        }
        return CartPayload(set)
    }

    fun removeUserCartStuff(id : Int) {
        toolsStorage.remove(id)
    }

    fun increaseAmount(id : Int) {
        val tool = toolsStorage[id]
        if (tool != null) {
            toolsStorage[id] = tool.copy(cnt = tool.cnt + 1)
        }
    }

    fun decreaseAmount(id : Int) {
        val tool = toolsStorage[id]
        if (tool != null) {
            toolsStorage[id] = tool.copy(cnt = tool.cnt - 1)
        }
    }

}