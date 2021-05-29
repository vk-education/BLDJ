package com.bldj.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bldj.project.databinding.InfoLayoutBinding

/**
 * Страница с информацией о приложении и о создателях.
 */
class InfoFragment : Fragment() {

    private lateinit var infoLayoutBinding: InfoLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.info_layout, container, false)
        infoLayoutBinding = InfoLayoutBinding.inflate(inflater,container,false)

        // Обработчик нажатия на кнопку возврата на предыдущую страницу.
        infoLayoutBinding.backButton.setOnClickListener {
            if (parentFragmentManager.backStackEntryCount > 0) {
                parentFragmentManager.popBackStackImmediate();
            }
        }
        return infoLayoutBinding.root
    }
}