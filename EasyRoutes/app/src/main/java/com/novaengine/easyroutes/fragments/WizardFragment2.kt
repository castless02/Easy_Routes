package com.novaengine.easyroutes.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.WizardFragment2Binding

class WizardFragment2 :Fragment(R.layout.wizard_fragment_2)
{
    private lateinit var binding: WizardFragment2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= WizardFragment2Binding.inflate(inflater)
        val bundle = Bundle()
        val prefer1=arguments?.getString("opzione1")
        Log.d("wizardframent2", prefer1.toString())
        bundle.putString("opzione1", prefer1)

        binding.radioButton7.setOnClickListener {
            bundle.putString("opzione2", "1")
            val fragment3 = WizardFragment3()
            fragment3.arguments=bundle
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView2, fragment3)
            transaction.addToBackStack(null)
            transaction.commit()

        }
        binding.radioButton6.setOnClickListener {
            bundle.putString("opzione2", "2")
            val fragment3 = WizardFragment3()
            fragment3.arguments=bundle
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView2, fragment3)
            transaction.addToBackStack(null)
            transaction.commit()

        }
        binding.radioButton5.setOnClickListener {
            bundle.putString("opzione2", "3")
            val fragment3 = WizardFragment3()
            fragment3.arguments=bundle
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView2, fragment3)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        return binding.root

    }
}