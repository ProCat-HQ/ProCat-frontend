package com.example.procatfirst.repository.data_coordinator

import android.content.Context
import com.example.procatfirst.data.Tool
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.cache.CatalogCache
import com.example.procatfirst.repository.cache.UserCartCache
import com.example.procatfirst.repository.data_storage.DataStorage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
        const val identifier = "[DataCoordinatorNew]"
    }

    /**
     * Я этим не пользуюсь, но смотрю сюда иногда и думаю...
     */
    @OptIn(DelicateCoroutinesApi::class) //useless
    fun getUserCart1(): List<Tool> {
        if (UserCartCache.shared.getUserCartStuff().isEmpty()) {
            //get from data storage
            GlobalScope.launch(Dispatchers.Default) {
                sleep(100) //Пока без этого не работает((. Буду думать
                UserCartCache.shared.setUserCartStuff(DataStorage.shared.getUserCartFromMemory())
            }
        }
        return UserCartCache.shared.getUserCartStuff()
    }

    /**
     * Вот эта версия блочит поток корутины пока не завершится.
     * Версия выше плохо себя ведёт
     * "runBlocking не предполагается к использованию в основном коде Android приложения."
     * - цитата фейк какой то, мне понравилось как оно работает (оно просто работает и всё)
     */
    fun getUserCart(): List<Tool> {
        if (UserCartCache.shared.getUserCartStuff().isEmpty()) {
            runBlocking {
                launch(Dispatchers.Default) {
                    UserCartCache.shared.setUserCartStuff(DataStorage.shared.getUserCartFromMemory())
                }
            }
        }
        return UserCartCache.shared.getUserCartStuff()
    }

    fun addToolToUserCart(tool : Tool){
        UserCartCache.shared.addUserCartStuff(tool)
    }

    fun removeToolFromUserCart(tool : Tool) {
        UserCartCache.shared.removeUserCartStuff(tool)
    }

    fun initialize(con: Context) {
        DataStorage.shared.initialize(con)
        //Чтобы корзина работала до её открытия (когда добавляем инструмент)
        runBlocking {
            launch(Dispatchers.Default) {
                UserCartCache.shared.setUserCartStuff(DataStorage.shared.getUserCartFromMemory())
            }
        }
    }

    // TODO разбить на отдельные файлы класс координатора как в старой версии
    fun loadCatalog() {
        ApiCalls.shared.getItems()
    }

}