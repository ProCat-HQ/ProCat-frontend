import com.example.procatfirst.data.CartPayload
import com.example.procatfirst.data.Tool
import com.example.procatfirst.repository.cache.UserCartCache
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_storage.DataStorage

    suspend fun DataCoordinator.getUserCart(): Map<Tool, Int> {
        if (UserCartCache.shared.getUserCartStuff().isEmpty()) {
            UserCartCache.shared.setUserCartStuff(DataStorage.shared.getUserCartFromMemory())
        }
        return UserCartCache.shared.getUserCartStuff()
    }
    /*suspend*/ fun DataCoordinator.getUserCartPayload(): CartPayload {
//        if (UserCartCache.shared.getUserCartStuff().isEmpty()) {
//            UserCartCache.shared.setUserCartStuff(DataStorage.shared.getUserCartFromMemory())
//        }
        return UserCartCache.shared.getUserCartPayload()
    }

    suspend fun DataCoordinator.addToolToUserCart(tool : Tool){
        UserCartCache.shared.addUserCartStuff(tool)
        //DataStorage.shared.setUserCartToMemory(UserCartCache.shared.getUserCartStuff())
    }

    suspend fun DataCoordinator.removeToolFromUserCart(id : Int) {
        UserCartCache.shared.removeUserCartStuff(id)
        DataStorage.shared.setUserCartToMemory(UserCartCache.shared.getUserCartStuff())
    }