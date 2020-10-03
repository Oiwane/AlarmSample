package io.github.oiwane.alarmsample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.github.oiwane.alarmsample.R

/**
 * アラームを追加する画面
 */
class EditFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val registerButton: Button = view.findViewById(R.id.registerButton)
        val cancelButton: Button = view.findViewById(R.id.cancelButton)

        registerButton.setOnClickListener {
            findNavController().popBackStack()
        }
        cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}