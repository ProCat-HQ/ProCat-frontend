package com.example.procatfirst.repository.data_coordinator

import com.example.procatfirst.data.Tool
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.cache.CatalogCache

suspend fun DataCoordinator.loadCatalog(callback : () -> Unit) {
    if (CatalogCache.shared.getCatalogStuff().isEmpty()) {
        ApiCalls.shared.getItemsApi(callback)
    }
    else {
        callback
    }
}

fun DataCoordinator.getCatalog() : List<Tool> {
    return CatalogCache.shared.getCatalogStuff()
}