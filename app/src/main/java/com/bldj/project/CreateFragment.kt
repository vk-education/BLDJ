package com.bldj.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import data.Advert

/**
 * Фрагмент окна создания объявления.
 */
class CreateFragment : Fragment() {

    private var advertsDbRef: DatabaseReference? = null
    private lateinit var fromET: EditText
    private lateinit var toET: EditText
    private lateinit var priceET: EditText
    private lateinit var placesET: EditText
    private lateinit var notesET: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)

        advertsDbRef = ConstantValues.database?.reference?.child("adverts")

        val publishBtn = view.findViewById<Button>(R.id.create_ad)
        fromET = view.findViewById(R.id.A_point)
        toET = view.findViewById(R.id.B_point)
        priceET = view.findViewById(R.id.edit_price)
        placesET = view.findViewById(R.id.edit_places)
        notesET = view.findViewById(R.id.create_comments)

        publishBtn?.setOnClickListener {
            val from = fromET.text.toString()
            val to = toET.text.toString()
            val price = priceET.text.toString().toInt()
            val places = placesET.text.toString().toInt()
            val notes = notesET.text.toString()
            if (from.isBlank() || from.isEmpty()) {
                Toast.makeText(
                    context,
                    "Адрес отправления не должен быть пустым",
                    Toast.LENGTH_LONG
                ).show()
            } else if (to.isEmpty() || to.isBlank()) {
                Toast.makeText(context, "Адрес прибытия не должен быть пустым", Toast.LENGTH_LONG)
                    .show()
            } else if (price <= 0) {
                Toast.makeText(context, "Неправильная цена", Toast.LENGTH_LONG).show()
            } else if (places < 2) {
                Toast.makeText(context, "Неправильное количество мест", Toast.LENGTH_LONG).show()
            } else {
                val adv = Advert(
                    from,
                    to,
                    price,
                    places,
                    notes,
                    arrayListOf(ConstantValues.user)
                )

                advertsDbRef!!.child("$from-$to").setValue(adv)
                val bottomSheet = BottomSheetCreateFragment()
                bottomSheet.show(parentFragmentManager, "TAG")
                parentFragmentManager.beginTransaction()
                    .replace((view?.parent as View).id, AdsFragment())
                    .addToBackStack(null).commit()
            }
        }
        // Inflate the layout for this fragment
        return view
    }
}