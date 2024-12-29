package com.novaengine.easyroutes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.databinding.ActivityAccountBinding
import com.novaengine.easyroutes.fragments.MainProfileFragment

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding
    private lateinit var fa: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var Id = ""
        val extras = intent.extras

        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fa = supportFragmentManager
        val transaction = fa.beginTransaction()

        if (extras != null) {
            Id = extras.getString("Id").toString()
        }
        var bundle = Bundle()
        bundle.putString("Id",Id)

        val m = MainProfileFragment()
        m.arguments = bundle

        transaction.add(R.id.fragmentContainerView, m)
        transaction.commit()

    }

}