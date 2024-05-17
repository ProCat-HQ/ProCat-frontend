package com.example.procatfirst.repository.cache

import android.util.Log
import com.example.procatfirst.data.CartItem
import com.example.procatfirst.data.CartPayload
import com.example.procatfirst.data.Tool

class UserCartCache {

    private var toolsStorage: MutableMap<Int, CartItem> = HashMap()

    companion object {
        val shared = UserCartCache()
        const val identifier = "[UserCartCache]"
    }

    fun addUserCartStuff(tool : Tool) {
        Log.v("AddTool", tool.toString())
        if (toolsStorage.containsKey(tool.id)) {
            toolsStorage[tool.id] = toolsStorage[tool.id]!!.copy(count = toolsStorage[tool.id]!!.count + 1)
        }
        else {
            toolsStorage[tool.id] = CartItem(tool.id, tool.name, tool.price, 1, tool.imageResId.toString())
        }
        Log.v("TOOLCache", toolsStorage.toString())
    }

    fun setUserCartStuff(cart: CartPayload) {
        // Fake warning, it may be null, trust me
        if (cart.items != null) {
            if (cart.items.isNotEmpty()) {
                for (item in cart.items) {
                    toolsStorage[item.id] = item
                }
            }
        }
    }

    fun getUserCartStuff(): MutableMap<Int, CartItem> {
        return toolsStorage
    }

    fun getUserCartPayload(): CartPayload {
        Log.d("Cache", toolsStorage.toString())
        return CartPayload(toolsStorage.values.toSet())
    }

    fun removeUserCartStuff(id : Int) {
        toolsStorage.remove(id)
    }

    fun increaseAmount(id : Int) {
        val tool = toolsStorage[id]
        if (tool != null) {
            toolsStorage[id] = tool.copy(count = tool.count + 1)
        }
    }

    fun decreaseAmount(id : Int) {
        val tool = toolsStorage[id]
        if (tool != null) {
            toolsStorage[id] = tool.copy(count = tool.count - 1)
        }
    }

}