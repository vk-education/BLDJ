package com.bldj.project.views

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.bldj.project.R
import com.bldj.project.adapters.UsersAdapter
import com.bldj.project.databinding.FragmentTravellersInfoDialogBinding
import data.User


class TravellersInfoDialog(private var users: MutableList<User>) : DialogFragment() {

    private lateinit var dialogBinding: FragmentTravellersInfoDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //return super.onCreateDialog(savedInstanceState)
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding: FragmentTravellersInfoDialogBinding =
                FragmentTravellersInfoDialogBinding.inflate(requireActivity().layoutInflater)
//            val inflater = requireActivity().layoutInflater.inflate(
//                R.layout.fragment_travellers_info_dialog,
//                null
//            );
            users.add(User("asdd","dasd","sadsad","asdas",))
            val list = binding.root.findViewById<RecyclerView>(R.id.usersList)
            list.setHasFixedSize(true)
            list.apply {
                adapter = UsersAdapter(users)
                invalidate()
            }
            list.adapter!!.notifyDataSetChanged()
            builder.setView(binding.root).setTitle("Попутчики")
                .setNegativeButton("OK") { dialog, id ->
                    getDialog()?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialogBinding = FragmentTravellersInfoDialogBinding.inflate(inflater, container, false)
        return dialogBinding.root
        //return inflater.inflate(R.layout.fragment_travellers_info_dialog, container, false)
    }

}