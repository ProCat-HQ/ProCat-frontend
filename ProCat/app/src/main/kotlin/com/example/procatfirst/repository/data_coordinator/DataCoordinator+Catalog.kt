package com.example.procatfirst.repository.data_coordinator

import android.graphics.Bitmap
import com.example.procatfirst.data.Item2
import com.example.procatfirst.data.ItemFullPayload
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.cache.CatalogCache

suspend fun DataCoordinator.loadCatalog(callback : () -> Unit) {
    if (CatalogCache.shared.getCatalogStuff().isEmpty()) {
        ApiCalls.shared.getItemsApi(callback)
    }
    else {
        callback()
    }
}

fun DataCoordinator.getCatalog() : List<Item2> {
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

suspend fun DataCoordinator.getCurrentTool(id: Int, callback: (ItemFullPayload?) -> Unit) {
    if (CatalogCache.shared.getCurrent() == null || CatalogCache.shared.getCurrent()!!.id != id) {
        ApiCalls.shared.getItemApi(id) {
            if (it == null) {
                callback(null)
            }
            else {
                CatalogCache.shared.setCurrent(it)
                callback(it)
            }
        }
    }
    else {
        callback(CatalogCache.shared.getCurrent())
    }
}