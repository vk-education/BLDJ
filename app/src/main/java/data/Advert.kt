package data

import java.io.Serializable
import java.util.*

data class Advert(
    val date: Date = Date(),
    val departTime: String = "",
    val from: String = "",
    val to: String = "",
    val price: Int = 0,
    val places: Int = 0,
    val notes: String = "",
    val users: MutableList<User> = arrayListOf(),
    val owner: String = ""
) : Serializable {
    override fun equals(other: Any?): Boolean {
        var equal = false
        if (other is Advert) {
            equal =
                date == other.date && from == other.from && to == other.to && owner == other.owner
        }
        return equal
    }
}