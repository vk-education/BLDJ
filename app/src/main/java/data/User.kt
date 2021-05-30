package data

data class User(
    val email: String = "",
    var name: String = "Даниил Можайский",
    var group: String = "Бакалавриат группа 205-2019"
) {
    var isTraveller: Boolean = false

    override fun equals(other: Any?): Boolean {
        return email == (other as User).email
    }
}