package com.example.procatfirst.repository.data_coordinator

import com.example.procatfirst.data.Tool
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.cache.CatalogCache

fun DataCoordinator.loadCatalog() {
    ApiCalls.shared.getItems()
}

fun DataCoordinator.getCatalog() : List<Tool> {
    return CatalogCache.shared.getCatalogStuff()
}