package com.bldj.project

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import data.User

/**
 * Класс констант.
 */
class ConstantValues {
    companion object {
        //Объект FirebaseAuth.
        var auth: FirebaseAuth? = null

        //Объект FirebaseDatabase.
        var database: FirebaseDatabase? = null

        //Объект текущего User.
        lateinit var user: User
    }
}