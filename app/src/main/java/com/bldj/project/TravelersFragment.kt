package com.bldj.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bldj.project.adapters.AdAdapter
import com.bldj.project.adapters.UsersAdapter
import com.bldj.project.databinding.FragmentTravelersBinding
import data.User

/**
 * Страница с попутчиками
 */
class TravelersFragment : Fragment() {
    private lateinit var travelersBinding: FragmentTravelersBinding
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var users: MutableList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        users = mutableListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        travelersBinding = FragmentTravelersBinding.inflate(inflater, container, false)
        travelersBinding.usersList.setHasFixedSize(true)
        usersAdapter = UsersAdapter(users)
        users.add(User("aapetropavlovskiy@edu.hse.ru", "Андрей Петропавловский", "БПИ198"))
        users.add(User("aapetropavlovskiy@edu.hse.ru", "Андрей Петропавловский", "БПИ198"))
        users.add(User("aapetropavlovskiy@edu.hse.ru", "Андрей Петропавловский", "БПИ198"))
        users.add(User("aapetropavlovskiy@edu.hse.ru", "Андрей Петропавловский", "БПИ198"))
//        val dividerItemDecoration = DividerItemDecoration(travelersBinding.usersList.context, RecyclerView.VERTICAL)
//        travelersBinding.usersList.addItemDecoration(dividerItemDecoration)
        travelersBinding.usersList.layoutManager = LinearLayoutManager(context)
        travelersBinding.apply {
            travelersBinding.usersList.adapter = usersAdapter
            invalidateAll()
        }
        return travelersBinding.root
        //return inflater.inflate(R.layout.fragment_travelers, container, false)
    }


}