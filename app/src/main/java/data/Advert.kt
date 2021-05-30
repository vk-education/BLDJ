package data

import java.util.*

data class Advert(
    val date: Date = Date(),
    val from: String = "",
    val to: String = "",
    val price: Int = 0,
    val places: Int = 0,
    val notes: String = "",
    val users: MutableList<User> = arrayListOf()
)