package com.example.procatfirst.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.example.procatfirst.R
import kotlinx.serialization.Serializable

@Serializable
data class Person (
    val id: Int = 0,
    val name: String = "",
)

object PersonList {

    val personList = listOf(
        Person(
            1,
            "Pranav",
        ),
        Person(
            2,
            "Ayesha",
        ),
        Person(
            3,
            "Roshini",
        ),
        Person(
            4,
            "Kaushik",
        )
    )
}

data class Chat(
    val id:Int,
    val message:String,
    val time:String,
    val direction:Boolean
)

val chatList = listOf(
    Chat(
        1,
        "Hey! How have you been?",
        "12:15 PM",
        true
    ),
    Chat(
        2,
        "Wanna catch up for a beer?",
        "12:17 PM",
        true
    ),
    Chat(
        3,
        "Awesome! Let’s meet up",
        "12:19 PM",
        false
    ),
    Chat(
        4,
        "Can I also get my cousin along? Will that be okay?",
        "12:20 PM",
        false
    ),
    Chat(
        5,
        "Yeah sure! get him too.",
        "12:21 PM",
        true
    ),
    Chat(
        6,
        "Hey! How have you been?",
        "12:15 PM",
        false
    ),
    Chat(
        7,
        "Wanna catch up for a beer?",
        "12:17 PM",
        true
    ),
    Chat(
        8,
        "Awesome! Let’s meet up",
        "12:19 PM",
        false
    ),
    Chat(
        9,
        "Can I also get my cousin along? Will that be okay?",
        "12:20 PM",
        false
    ),
    Chat(
        10,
        "Yeah sure! get him too.",
        "12:21 PM",
        true
    ),
)