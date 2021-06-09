package com.bldj.project.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bldj.project.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Нижняя панель при нажатии на кнопку опубликовать.
 */
class BottomSheetCreateFragment : BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet_create, container, false)
    }
}