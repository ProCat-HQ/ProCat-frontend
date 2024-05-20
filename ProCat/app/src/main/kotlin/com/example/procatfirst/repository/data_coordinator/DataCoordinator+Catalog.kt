package com.example.procatfirst.repository.data_coordinator

import android.graphics.Bitmap
import com.example.procatfirst.data.Tool
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.cache.CatalogCache
import java.io.InputStream

suspend fun DataCoordinator.loadCatalog(callback : () -> Unit) {
    if (CatalogCache.shared.getCatalogStuff().isEmpty()) {
        ApiCalls.shared.getItemsApi(callback)
    }
    else {
        callback()
    }
}

fun DataCoordinator.getCatalog() : List<Tool> {
    return CatalogCache.shared.getCatalogStuff()
}

suspend fun DataCoordinator.getImage(img: String, callback: (Bitmap) -> Unit) {
    if (CatalogCache.shared.getImage(img) == null) {
        ApiCalls.shared.getImageApi(img) {
            CatalogCache.shared.setImage(img, it)
            callback(it)
        }
    }
    else {
        callback(CatalogCache.shared.getImage(img)!!)
    }
}