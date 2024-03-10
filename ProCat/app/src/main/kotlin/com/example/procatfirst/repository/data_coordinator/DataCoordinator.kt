package com.example.procatfirst.repository.data_coordinator

import android.content.Context
import android.util.Log
import com.example.procatfirst.data.Tool
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.cache.UserCartCache
import com.example.procatfirst.repository.data_storage.DataStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep

/**
 * К этому (и только к этому) статическому классу обращаемся
 * для получения данных из кэша/памяти/сервера (всего)
 *
 * И для изменения данных тоже всё тут!
 */
class DataCoordinator {

    // TODO !!!! Пройти курс по корутинам !!!! (в процессе)

    companion object {
        val shared = DataCoordinator()
        const val identifier = "[DataCoordinator]"
    }

    /**
     *
     */
    suspend fun getUserCart(): List<Tool> {
        if (UserCartCache.shared.getUserCartStuff().isEmpty()) {
            UserCartCache.shared.setUserCartStuff(DataStorage.shared.getUserCartFromMemory())
        }
        return UserCartCache.shared.getUserCartStuff()
    }

    fun addToolToUserCart(tool : Tool){
        UserCartCache.shared.addUserCartStuff(tool)
    }

    suspend fun removeToolFromUserCart(tool : Tool) {
        UserCartCache.shared.removeUserCartStuff(tool)
    }

    suspend fun initialize(con: Context) {
        DataStorage.shared.initialize(con)
        //Чтобы корзина работала до её открытия (когда добавляем инструмент)
        withContext(Dispatchers.IO) {
            UserCartCache.shared.setUserCartStuff(DataStorage.shared.getUserCartFromMemory())
        }
    }

    // TODO разбить на отдельные файлы класс координатора как в старой версии
    fun loadCatalog() {
        ApiCalls.shared.getItems()
    }

}