import com.example.procatfirst.data.Tool
import com.example.procatfirst.repository.cache.UserCartCache
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_storage.DataStorage

suspend fun DataCoordinator.getUserCart(): List<Tool> {
        if (UserCartCache.shared.getUserCartStuff().isEmpty()) {
            UserCartCache.shared.setUserCartStuff(DataStorage.shared.getUserCartFromMemory())
        }
        return UserCartCache.shared.getUserCartStuff()
    }

    suspend fun DataCoordinator.addToolToUserCart(tool : Tool){
        UserCartCache.shared.addUserCartStuff(tool)
    }

    suspend fun DataCoordinator.removeToolFromUserCart(tool : Tool) {
        UserCartCache.shared.removeUserCartStuff(tool)
    }