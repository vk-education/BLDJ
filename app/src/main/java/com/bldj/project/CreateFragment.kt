package com.bldj.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import data.Advert

/**
 * Фрагмент окна создания объявления.
 */
class CreateFragment : Fragment() {
    private lateinit var databaseReference: DatabaseReference
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

        databaseReference = FirebaseDatabase.getInstance().getReference("USER")

        val publishBtn = view.findViewById<Button>(R.id.create_ad)
        fromET = view.findViewById(R.id.A_point)
        toET = view.findViewById(R.id.B_point)
        priceET = view.findViewById(R.id.edit_price)
        placesET = view.findViewById(R.id.edit_places)
        notesET = view.findViewById(R.id.create_comments)

        publishBtn?.setOnClickListener {
            val bottomSheet = BottomSheetCreateFragment()
            bottomSheet.show(requireFragmentManager(), "TAG")
            val adv = Advert(
                fromET.text.toString(),
                toET.text.toString(),
                priceET.text.toString().toInt(),
                placesET.text.toString().toInt(),
                notesET.text.toString()
            )

            databaseReference.push().setValue(adv)
        }

        // Inflate the layout for this fragment
        return view
    }
}