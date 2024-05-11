package com.example.procatfirst.repository.data_coordinator

import android.content.Context
import com.example.procatfirst.repository.cache.UserCartCache
import com.example.procatfirst.repository.data_storage.DataStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * К этому (и только к этому) статическому классу обращаемся
 * для получения или изменения данных из кэша/памяти/сервера (всего)
 *
 */
class DataCoordinator {

    companion object {
        val shared = DataCoordinator()
        const val identifier = "[DataCoordinator]"
    }

    suspend fun initialize(con: Context) {
        DataStorage.shared.initialize(con)
        //Чтобы корзина работала до её открытия (когда добавляем инструмент)
        withContext(Dispatchers.IO) {
            UserCartCache.shared.setUserCartStuff(DataStorage.shared.getUserCartFromMemory())
        }
    }

}