package com.novaengine.easyroutes.fragments

import android.app.DownloadManager.Query
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.WizardFragment3Binding

class WizardFragment3: Fragment(R.layout.wizard_fragment_3)
{
    private lateinit var binding: WizardFragment3Binding
    private lateinit var query:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= WizardFragment3Binding.inflate(inflater)
        val bundle = Bundle()
        val prefer1=arguments?.getString("opzione1")
        val prefer2=arguments?.getString("opzione2")
        bundle.putString("opzione1", prefer1)
        bundle.putString("opzione2", prefer2)
        binding.radioButton8.setOnClickListener {
            bundle.putString("opzione3","1")
            setquery(bundle)
            bundle.putString("searchQuery", query)
            val fragment3 = SearchFragment()
            fragment3.arguments=bundle
            Log.v("ciU", bundle.getString("searchQuery").toString())
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView2, fragment3)
            transaction.addToBackStack(null)
            transaction.commit()

        }

        binding.radioButton9.setOnClickListener {
            bundle.putString("opzione3", "2")
            setquery(bundle)
            bundle.putString("searchQuery", query)
            val fragment3 = SearchFragment()
            fragment3.arguments=bundle
            Log.v("ciU", bundle.getString("searchQuery").toString())
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView2, fragment3)
            transaction.addToBackStack(null)
            transaction.commit()

        }

        binding.radioButton10.setOnClickListener {
            bundle.putString("opzione3", "3")
            setquery(bundle)
            bundle.putString("searchQuery", query)
            val fragment3 = SearchFragment()
            fragment3.arguments=bundle
            Log.v("ciU", bundle.getString("searchQuery").toString())
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView2, fragment3)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return binding.root
    }
    private fun setquery(bundle: Bundle)
    {
        val prefer1=bundle.getString("opzione1").toString()
        val prefer2=bundle.getString("opzione2").toString()
        val prefer3=bundle.getString("opzione3").toString()

        if(prefer3 == "1")
        {
            query = "SELECT S1.*, (SELECT AVG(S.Valutazione) FROM Stelle S INNER JOIN S_S V ON V.Id_valutazione = S.Id_valutazione WHERE V.Id_struttura = S1.Id_struttura) AS average_value, Camera.Disponibilita, Camera.Prezzo, S1.Tipologia_luogo FROM Struttura S1 INNER JOIN S_C ON S1.Id_struttura = S_C.Ref_id_struttura INNER JOIN Camera ON S_C.Ref_id_Camera = Camera.Id_camera WHERE S1.Tipologia_luogo = '${prefer1}' AND Camera.Disponibilita = '${prefer2}' AND Camera.Prezzo <= 250;"
        }
        else if(prefer3 == "2")
        {
            query="SELECT S1.*, (SELECT AVG(S.Valutazione) FROM Stelle S INNER JOIN S_S V ON V.Id_valutazione = S.Id_valutazione WHERE V.Id_struttura = S1.Id_struttura) AS average_value, Camera.Disponibilita, Camera.Prezzo, S1.Tipologia_luogo FROM Struttura S1 INNER JOIN S_C ON S1.Id_struttura = S_C.Ref_id_struttura INNER JOIN Camera ON S_C.Ref_id_Camera = Camera.Id_camera WHERE S1.Tipologia_luogo = '${prefer1}' AND Camera.Disponibilita = '${prefer2}' AND Camera.Prezzo > 250;"
        }
        else if(prefer3 == "3")
        {
            query="SELECT S1.*, (SELECT AVG(S.Valutazione) FROM Stelle S INNER JOIN S_S V ON V.Id_valutazione = S.Id_valutazione WHERE V.Id_struttura = S1.Id_struttura) AS average_value, Camera.Disponibilita, Camera.Prezzo, S1.Tipologia_luogo FROM Struttura S1 INNER JOIN S_C ON S1.Id_struttura = S_C.Ref_id_struttura INNER JOIN Camera ON S_C.Ref_id_Camera = Camera.Id_camera WHERE S1.Tipologia_luogo = '${prefer1}' AND Camera.Disponibilita = '${prefer2}' AND Camera.Prezzo > 500;"
        }
    }
}
