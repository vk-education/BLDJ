package data

data class Advert(
    val from: String = "",
    val to: String = "",
    val price: Int = 0,
    val places: Int = 0,
    val notes: String = "",
    val users: MutableList<User> = arrayListOf()
)