package com.example.procatfirst.repository.data_coordinator

import com.example.procatfirst.data.Tool
import com.example.procatfirst.repository.cache.CatalogCache
import com.example.procatfirst.repository.cache.UserCartCache
import com.example.procatfirst.repository.data_storage.DataStorage

/**
 * К этому (и только к этому) статическому классу обращаемся
 * для получения данных из кэша/памяти/сервера (всего)
 *
 * И для изменения данных тоже всё тут!
 */
class DataCoordinator {

    // TODO !!!! Пройти курс по корутинам !!!!

    companion object {
        val shared = DataCoordinator()
        const val identifier = "[DataCoordinatorNew]"
    }

    fun getUserCart(): MutableList<Tool> {
        if (UserCartCache.shared.getUserCartStuff().isEmpty()) {
            //get from data storage
            UserCartCache.shared.setUserCartStuff(DataStorage.shared.getUserCartFromMemory())
        }
        return UserCartCache.shared.getUserCartStuff()
    }

    fun addToolToUserCart(tool : Tool){
        UserCartCache.shared.addUserCartStuff(tool)
    }

    fun removeToolFromUserCart(tool : Tool) {
        UserCartCache.shared.removeUserCartStuff(tool)
    }

}