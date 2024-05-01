package com.example.procatfirst.repository.cache

import android.util.Log
import com.example.procatfirst.data.CartItem
import com.example.procatfirst.data.CartPayload
import com.example.procatfirst.data.Tool

class UserCartCache {

    private var toolsStorage: MutableMap<Tool, Int> = HashMap()

    companion object {
        val shared = UserCartCache()
        const val identifier = "[UserCartCache]"
    }

    fun addUserCartStuff(tool : Tool) {
        Log.v("AddTool", tool.toString())
        if (toolsStorage.containsKey(tool)) {
            toolsStorage[tool] = toolsStorage[tool]!! + 1
        }
        else {
            toolsStorage[tool] = 1
        }
        Log.v("TOOLCache", toolsStorage.toString())
    }

    fun setUserCartStuff(cart: MutableMap<Tool, Int>) {
        if (cart.isNotEmpty()) {
            toolsStorage = cart
        }
    }

    fun getUserCartStuff(): MutableMap<Tool, Int> {
        return toolsStorage
    }

    fun getUserCartPayload(): CartPayload {
        Log.d("Cache", toolsStorage.toString())
        val set : MutableSet<CartItem> = mutableSetOf()
        for (tool in toolsStorage) {
            val key = tool.key
            set.add(CartItem(key.id, key.name, key.price, tool.value, key.imageResId.toString())) //??
        }
        return CartPayload(set)
    }

    fun removeUserCartStuff(id : Int) {
        for (tool in toolsStorage.keys) {
            if (tool.id == id) {
                toolsStorage.remove(tool)
                break
            }
        }
    }

}