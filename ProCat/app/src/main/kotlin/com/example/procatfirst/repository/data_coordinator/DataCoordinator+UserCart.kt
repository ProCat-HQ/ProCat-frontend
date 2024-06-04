import com.example.procatfirst.data.CartItem
import com.example.procatfirst.data.CartPayload
import com.example.procatfirst.data.Item2
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.cache.UserCartCache
import com.example.procatfirst.repository.data_coordinator.DataCoordinator

suspend fun DataCoordinator.getUserCart(): Map<Int, CartItem> {
        ApiCalls.shared.getCartApi { payload : CartPayload -> UserCartCache.shared.setUserCartStuff(payload) }
        return UserCartCache.shared.getUserCartStuff()
    }
    suspend fun DataCoordinator.getUserCartPayload(): CartPayload {
        return UserCartCache.shared.getUserCartPayload()
    }

    suspend fun DataCoordinator.addToolToUserCart(tool : Item2, callback: (String) -> Unit){
        ApiCalls.shared.postCart(tool.id, 1) {
            if (it == "") {
                UserCartCache.shared.addUserCartStuff(tool)
            }
            callback(it)
        }
        NotificationCoordinator.shared.sendIntent(SystemNotifications.cartLoaded)
    }

    suspend fun DataCoordinator.removeToolFromUserCart(id : Int) {
        UserCartCache.shared.getUserCartStuff()[id]?.let { ApiCalls.shared.deleteInCart(id,  it.count) }
        UserCartCache.shared.removeUserCartStuff(id)
    }

    suspend fun DataCoordinator.increaseToolCount(id : Int, callback: (String) -> Unit) {
        ApiCalls.shared.postCart(id, 1) {
            if (it == "") {
                UserCartCache.shared.increaseAmount(id)
            }
            callback(it)
        }

    }

    suspend fun DataCoordinator.decreaseToolCount(id : Int) {
        UserCartCache.shared.decreaseAmount(id)
        ApiCalls.shared.deleteInCart(id, 1)
    }