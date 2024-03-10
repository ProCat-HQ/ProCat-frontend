package com.example.procatfirst.repository.cache

import com.example.procatfirst.data.Tool
import com.example.procatfirst.repository.data_storage.DataStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserCartCache {

    private var toolsStorage: MutableList<Tool> = mutableListOf()

    companion object {
        val shared = UserCartCache()
        const val identifier = "[UserCartCache]"
    }

    //Снова нарушена архитектура: Этот класс ничего не должен знать про DataStorage
    // (ему вообщеничего знать не надо, это хранилище)
    suspend fun addUserCartStuff(tool : Tool) {
        toolsStorage.add(tool)
        DataStorage.shared.setUserCartToMemory(toolsStorage)
    }

    fun setUserCartStuff(cart: MutableList<Tool>) {
        if (cart.isNotEmpty()) {
            toolsStorage = cart
        }
    }

    fun getUserCartStuff(): MutableList<Tool> {
        return toolsStorage
    }

    suspend fun removeUserCartStuff(tool : Tool) {
        toolsStorage.remove(tool)
        DataStorage.shared.setUserCartToMemory(toolsStorage)
    }

}