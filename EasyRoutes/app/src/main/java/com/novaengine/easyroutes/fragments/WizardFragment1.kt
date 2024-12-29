package com.novaengine.easyroutes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.WizardFragment1Binding

class WizardFragment1 :Fragment(R.layout.wizard_fragment_1)
{
    private lateinit var binding: WizardFragment1Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = WizardFragment1Binding.inflate(inflater)

        val bundle = Bundle()

        binding.radioButton.setOnClickListener {
            bundle.putString("opzione1", binding.radioButton.text.toString())
            val fragment2 = WizardFragment2()
            fragment2.arguments=bundle
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView2, fragment2)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.radioButton2.setOnClickListener {
            bundle.putString("opzione1", binding.radioButton2.text.toString())
            val fragment2 = WizardFragment2()
            fragment2.arguments=bundle
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView2, fragment2)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.radioButton3.setOnClickListener {
            bundle.putString("opzione1", binding.radioButton3.text.toString())
            val fragment2 = WizardFragment2()
            fragment2.arguments=bundle
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView2, fragment2)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.radioButton4.setOnClickListener {
            bundle.putString("opzione1", binding.radioButton4.text.toString())
            val fragment2 = WizardFragment2()
            fragment2.arguments=bundle
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView2, fragment2)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return binding.root
    }
}