package com.bldj.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bldj.project.databinding.LoginLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import data.ConstantValues
import data.User

class LoginFragment : Fragment() {

    private var usersDbRef: DatabaseReference? = null
    private lateinit var loginLayoutBinding: LoginLayoutBinding

    lateinit var inflaterThis: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ConstantValues.auth = FirebaseAuth.getInstance()
        ConstantValues.database = FirebaseDatabase.getInstance()
        usersDbRef = ConstantValues.database?.reference?.child(ConstantValues.USER_DB_REFERENCE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginLayoutBinding = LoginLayoutBinding.inflate(inflater, container, false)
//        inflaterThis =
//            LayoutInflater.from(this.context).inflate(R.layout.login_layout, container, false)
//
//        val loginET: EditText = inflaterThis.findViewById(R.id.login_edit)
//        inflaterThis.findViewById<Button>(R.id.go_bttn).setOnClickListener { onLogin(loginET) }
        // Inflate the layout for this fragment
        //return inflaterThis
        loginLayoutBinding.goBttn.setOnClickListener { onLogin() }
        return loginLayoutBinding.root
    }

    private fun onLogin(/*loginET: EditText*/) {

        val password = loginLayoutBinding.passwordEdit.text.toString()
        val login = loginLayoutBinding.loginEdit.text.toString()
        // Log.i("LOGINPAROL", login)
//        val actionCodeSettings = actionCodeSettings {
//            // URL you want to redirect back to. The domain (www.example.com) for this
//            // URL must be whitelisted in the Firebase Console.
//            url = "https://www.example.com/finishSignUp?cartId=1234"
//            // This must be true
//            handleCodeInApp = true
//            iosBundleId = "com.example.ios"
//            setAndroidPackageName(
//                "com.bldj.project",
//                true, /* installIfNotAvailable */
//                "21" /* minimumVersion */)
//        }
//        ConstantValues.auth?.sendSignInLinkToEmail(login, actionCodeSettings)
//            ?.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("send_Email", "Email sent.")
//                }
//            }
        //ConstantValues.auth?.sendSignInLinkToEmail(login)
        ConstantValues.auth?.createUserWithEmailAndPassword(login, password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("auth", "createUserWithEmail:success")
                    val user = User(login, password)
                    user.id = ConstantValues.auth?.currentUser!!.uid
                    ConstantValues.user = user
                    usersDbRef!!.child(login.replace(".", "")).setValue(user)
                    parentFragmentManager.beginTransaction()
                        .replace((view?.parent as View).id, AccessCodeFragment(), "LoginSuccess")
                        .commit()

                } else {
                    Log.i("authq", "createUserWithEmail:failure", task.exception)
                    ConstantValues.auth?.signInWithEmailAndPassword(login, password)
                        ?.addOnCompleteListener { taskAnother ->
                            if (taskAnother.isSuccessful) {
                                ConstantValues.alreadyCreated = true
                                parentFragmentManager.beginTransaction()
                                    .replace(
                                        (view?.parent as View).id,
                                        AccessCodeFragment(),
                                        "LoginSuccess"
                                    )
                                    .commit()
                            } else {
                                Toast.makeText(
                                    this.context, "Неверный пароль",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
    }
}