package com.novaengine.easyroutes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.novaengine.easyroutes.databinding.ActivityWizardBinding
import com.novaengine.easyroutes.fragments.WizardFragment1

class WizardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWizardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Layout inflater
        binding = ActivityWizardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fragment manager
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Wizard Fragment
        val WF = WizardFragment1()
        fragmentTransaction.add(R.id.fragmentContainerView, WF)
        fragmentTransaction.commit()

    }
}