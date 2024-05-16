import com.example.procatfirst.data.CartPayload
import com.example.procatfirst.data.Tool
import com.example.procatfirst.data.ToolWithCnt
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.cache.UserCartCache
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_storage.DataStorage

    suspend fun DataCoordinator.getUserCart(): Map<Int, ToolWithCnt> {
        if (UserCartCache.shared.getUserCartStuff().isEmpty()) {
            ApiCalls.shared.getCartApi { payload : CartPayload -> UserCartCache.shared.setUserCartStuff(payload) }
            //UserCartCache.shared.setUserCartStuff(DataStorage.shared.getUserCartFromMemory())
        }
        return UserCartCache.shared.getUserCartStuff()
    }
    suspend fun DataCoordinator.getUserCartPayload(): CartPayload {
//        if (UserCartCache.shared.getUserCartStuff().isEmpty()) {
//            UserCartCache.shared.setUserCartStuff(DataStorage.shared.getUserCartFromMemory())
//        }

        return UserCartCache.shared.getUserCartPayload()
    }

    suspend fun DataCoordinator.addToolToUserCart(tool : Tool){
        UserCartCache.shared.addUserCartStuff(tool)
        ApiCalls.shared.postCart(tool.id, 1)
        NotificationCoordinator.shared.sendIntent(SystemNotifications.cartLoaded)
        //DataStorage.shared.setUserCartToMemory(UserCartCache.shared.getUserCartStuff())
    }

    suspend fun DataCoordinator.removeToolFromUserCart(id : Int) {
        UserCartCache.shared.getUserCartStuff()[id]?.let { ApiCalls.shared.deleteInCart(id,  it.cnt) }
        UserCartCache.shared.removeUserCartStuff(id)
        //DataStorage.shared.setUserCartToMemory(UserCartCache.shared.getUserCartStuff())
    }

    suspend fun DataCoordinator.increaseToolCount(id : Int) {
        UserCartCache.shared.increaseAmount(id)
        //DataStorage.shared.setUserCartToMemory(UserCartCache.shared.getUserCartStuff())
        ApiCalls.shared.postCart(id, 1)
    }

    suspend fun DataCoordinator.decreaseToolCount(id : Int) {
        UserCartCache.shared.decreaseAmount(id)
        ApiCalls.shared.deleteInCart(id, 1)
        //DataStorage.shared.setUserCartToMemory(UserCartCache.shared.getUserCartStuff())
    }