package com.novaengine.easyroutes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.databinding.ActivityPrenotaBinding
import com.novaengine.easyroutes.fragments.RoomSelectionFragment
import com.novaengine.easyroutes.fragments.PrenotaFragment

class PrenotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrenotaBinding
    lateinit var text: String
    private lateinit var fm: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPrenotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fm = supportFragmentManager
        val transaction = fm.beginTransaction()

        var Id: String = ""
        var Luogo: String = ""
        var Id_Struttura: String = ""
        var Struttura: String = ""
        val extras = intent.extras
        if (extras != null) {
            Id = extras.getString("Id").toString()
            Luogo = extras.getString("Luogo").toString()
            Struttura = extras.getString("Struttura").toString()
            Id_Struttura = extras.getString("Id_Struttura").toString()
        }
        Log.i("info", Id)

        var bundle = Bundle()
        bundle.putString("Id",Id)
        bundle.putString("Luogo",Luogo)
        bundle.putString("Id_Struttura",Id_Struttura)
        bundle.putString("Struttura",Struttura)

        val CSF = RoomSelectionFragment()
        CSF.arguments = bundle

        val PF = PrenotaFragment()
        PF.arguments = bundle

        transaction.add(R.id.fragmentContainerView4, PF)
        transaction.commit()

    }

}


