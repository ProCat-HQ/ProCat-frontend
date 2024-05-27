package com.example.procatfirst.ui.personal.chats

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.procatfirst.data.ChatDataProvider
import com.example.procatfirst.data.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class ChatViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    val messages: MutableList<Message> = mutableListOf()


    var userInputMessage by mutableStateOf("")
        private set

    init {
        messages.addAll(ChatDataProvider.messageList)
    }

    fun updateTextMessage(enteredMessage: String){
        userInputMessage = enteredMessage
    }

    fun sendMessage(text: String) {
        updateTextMessage("")
        val last = messages[messages.size - 1]
        messages.add(Message(last.messageId + 1, text, _uiState.value.currentUserId, last.chatId, "23:15"))
    }

}