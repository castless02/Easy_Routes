package com.novaengine.easyroutes

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.databinding.ActivityExploreBinding
import com.novaengine.easyroutes.fragments.AskFragment
import com.novaengine.easyroutes.fragments.ExploreFragment
import com.novaengine.easyroutes.fragments.BookingsFragment
import com.novaengine.easyroutes.fragments.WizardFragment1

class ExploreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExploreBinding
    private lateinit var fa: FragmentManager
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View binding support
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fragments support
        fa = supportFragmentManager
        val transaction = fa.beginTransaction()

        sp = getSharedPreferences(getString(R.string.er_preferences_file), Context.MODE_PRIVATE)
        val Id = sp.getString("id", null).toString()
        Log.i("ids", Id)
        var bundle = Bundle()
        bundle.putString("Id",Id)

        // Load some fragments
        val MF = ExploreFragment()
        MF.arguments = bundle
        val TF = BookingsFragment()
        TF.arguments = bundle
        val AF = AskFragment()
        AF.arguments = bundle
        val WF = WizardFragment1()
        WF.arguments = bundle

        transaction.replace(R.id.fragmentContainerView2, MF,"ExploreFragment")
        transaction.commit()

        binding.home.setOnClickListener {
            val transaction = fa.beginTransaction()
            fa.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            transaction.replace(R.id.fragmentContainerView2, MF)
            transaction.commit()
        }

        binding.trips.setOnClickListener {
            val transaction = fa.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.fragmentContainerView2, TF)
            transaction.commit()
        }

        binding.wizard.setOnClickListener {
            val transaction = fa.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.fragmentContainerView2, WF)
            transaction.commit()
        }

        binding.ask.setOnClickListener {
            val transaction = fa.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.fragmentContainerView2, AF)
            transaction.commit()
        }

    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2)
        if (fragment is ExploreFragment) {
            moveTaskToBack(true)
        } else {
            super.onBackPressed()
        }
    }


}