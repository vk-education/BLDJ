package data

//class Advert {
//    val from: String? = null
//    val to: String? = null
//    val price: Int? = null
//    val places: Int? = null
//    val notes: String? = null
//}
data class Advert(
    val from: String = "",
    val to: String = "",
    val price: Int = 0,
    val places: Int = 0,
    val notes: String = ""
)