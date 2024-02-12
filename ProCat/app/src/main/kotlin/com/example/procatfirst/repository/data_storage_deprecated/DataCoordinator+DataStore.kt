package com.example.procatfirst.repository.data_storage_deprecated

import android.util.Log
import androidx.datastore.preferences.core.edit
import com.example.procatfirst.data.Tool
import com.example.procatfirst.repository.data_storage_deprecated.DataCoordinatorOLD.Companion.identifier
import kotlinx.coroutines.flow.firstOrNull

// MARK: Sample String Functionality
// Please note that the DataStore functionality must be called within a coroutine.
suspend fun DataCoordinatorOLD.getUserEmailDataStore(): String {
    val context = this.context ?: return defaultUserEmailPreferenceValue
    return context.dataStore.data.firstOrNull()?.get(PreferencesKeys.userEmail)
        ?: defaultUserEmailPreferenceValue
}

suspend fun DataCoordinatorOLD.setUserEmailDataStore(value: String) {
    val context = this.context ?: return
    Log.i(
        identifier,
        "user email old: $userEmailPreferenceVariable"
    )
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.userEmail] = value
        Log.i(
            identifier,
            "user email new: $userEmailPreferenceVariable"
        )
    }
}

suspend fun DataCoordinatorOLD.getUserPhoneDataStore(): String {
    val context = this.context ?: return defaultUserPhonePreferenceValue
    return context.dataStore.data.firstOrNull()?.get(PreferencesKeys.userPhone)
        ?: defaultUserPhonePreferenceValue
}

suspend fun DataCoordinatorOLD.setUserPhoneDataStore(value: String) {
    val context = this.context ?: return
    Log.i(
        identifier,
        "ok"
    )
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.userPhone] = value
        Log.i(
            identifier,
            "ok"
        )
    }
}

// MARK: Sample Int Functionality
// Please note that the DataStore functionality must be called within a couroutine.
suspend fun DataCoordinatorOLD.getSampleIntDataStore(): Int {
    val context = this.context ?: return defaultSampleIntPreferenceVariable
    return context.dataStore.data.firstOrNull()?.get(PreferencesKeys.sampleInt)
        ?: defaultSampleIntPreferenceVariable
}

suspend fun DataCoordinatorOLD.setSampleIntDataStore(value: Int) {
    val context = this.context ?: return
    Log.i(
        identifier,
        "ok"
    )
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.sampleInt] = value
        Log.i(
            identifier,
            "ok"
        )
    }
}

// MARK: Sample Boolean Functionality
// Please note that the DataStore functionality must be called within a coroutine.
suspend fun DataCoordinatorOLD.getSampleBooleanDataStore(): Boolean {
    val context = this.context ?: return defaultSampleBooleanPreferenceVariable
    return context.dataStore.data.firstOrNull()?.get(PreferencesKeys.sampleBoolean)
        ?: defaultSampleBooleanPreferenceVariable
}

suspend fun DataCoordinatorOLD.setSampleBooleanDataStore(value: Boolean) {
    val context = this.context ?: return
    Log.i(
        identifier,
        "ok"
    )
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.sampleBoolean] = value
        Log.i(
            identifier,
            "ok"
        )
    }

suspend fun DataCoordinatorOLD.getToolsInCartDataStore(): List<Tool> {
    val context = this.context ?: return defaultToolsInCartPreferenceVariable
    val rowTools = context.dataStore.data.firstOrNull()?.get(PreferencesKeys.toolsInCart)
        ?: defaultToolsInCartPreferenceVariable
    val tools: MutableList<String> = emptyList<String>().toMutableList()
    for (i : Any in rowTools) {
        tools.add(i.toString())
    }
    val readyTools: MutableList<Tool> = emptyList<Tool>().toMutableList()
    for (i: Int in 0..<tools.size step 5) {
        val tool = Tool(tools[i].toInt(),
            tools[i+1], tools[i+2].toInt(), tools[i+3], tools[i+4], tools[i+5].toInt()
        )
        readyTools.add(tool)
    }
    return readyTools
}

suspend fun DataCoordinatorOLD.addToolInCartDataStore(value: Tool) {
    val context = this.context ?: return
    Log.i(
        identifier,
        "tools old: $toolsInCartPreferenceVariable"
    )
    val list = setOf<String>().toMutableSet()
    list.add(value.id.toString())
    list.add(value.name)
    list.add(value.imageResId.toString())
    list.add(value.description)
    list.add(value.specifications)
    list.add(value.price.toString())
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.toolsInCart] = list
        Log.i(
            identifier,
            "tools new: $toolsInCartPreferenceVariable"
        )
    }
}

}