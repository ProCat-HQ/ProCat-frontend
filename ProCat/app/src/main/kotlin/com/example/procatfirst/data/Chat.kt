package com.example.procatfirst.data

data class Chat(
    val chatId: Int,
    val name: String,
    val isSolved: Boolean,
    val firstUserId: Int,
    val secondUserId: Int,
    val orderId: Int,
    val message: String,
    val time: String,
    val direction: Boolean
)

data class Message(
    val messageId: Int,
    val text: String,
    val userId: Int,
    val chatId: Int,
    val date: String //not in DB model
)

val messageList = listOf(
    Message(
        1,
        "Hey! How have you been?",
        1,
        1,
        "23:15"
    ),
    Message(
        2,
        "Fine, thanks",
        2,
        1,
        "23:15"
    ),
    Message(
        3,
        "Tell me more",
        1,
        1,
        "23:15"
    ),
    Message(
        4,
        "I want to know more",
        1,
        1,
        "23:15"
    ),
    Message(
        5,
        "I don't want to",
        2,
        1,
        "23:15"
    ),
    Message(
        6,
        "Leave me alone",
        2,
        1,
        "23:15"
    ),
    Message(
        7,
        "Okay",
        1,
        1,
        "23:15"
    ),
    Message(
        8,
        "It was a joke. Come back",
        2,
        1,
        "23:15"
    ),
)