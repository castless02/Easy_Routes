package com.novaengine.easyroutes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.JsonObject
import com.novaengine.easyroutes.databinding.MezziFragmentBinding
import com.novaengine.easyroutes.fragments.RoomSelectionFragment


class MezziFragment : Fragment(R.layout.mezzi_fragment) {

    private lateinit var binding: MezziFragmentBinding
    private lateinit var fm:FragmentManager
    lateinit var text:String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val packageName = requireContext().packageName
        fm = parentFragmentManager
        val transaction = fm.beginTransaction()
        var jsonObject: JsonObject? = null

        var Id = arguments?.getString("Id").toString()
        var Eta = arguments?.getString("Eta").toString()
        var Struttura = arguments?.getString("Struttura").toString()
        var Luogo = arguments?.getString("Luogo").toString()
        var Persone = arguments?.getString("Persone").toString()
        var DataI = arguments?.getString("DataI").toString()
        var DataF = arguments?.getString("DataF").toString()
        var Id_Struttura = arguments?.getString("Id_Struttura").toString()
        var Mezzo:String = "null"



        binding = MezziFragmentBinding.inflate(inflater)


        binding.vaialpagamento.setOnClickListener(){

            if(binding.auto.isChecked){
                Mezzo = "Automobile"
                var bundle = Bundle()
                bundle.putString("Id",Id)
                bundle.putString("Eta",Eta)
                bundle.putString("Struttura",Struttura)
                bundle.putString("Luogo",Luogo)
                bundle.putString("Persone",Persone)
                bundle.putString("DataI",DataI)
                bundle.putString("DataF",DataF)
                bundle.putString("Id_Struttura",Id_Struttura)
                bundle.putString("Mezzo",Mezzo)

                val CSF = RoomSelectionFragment()
                CSF.arguments = bundle

                transaction.replace(R.id.fragmentContainerView4, CSF)
                transaction.addToBackStack(null)
                transaction.commit()
                Log.i("Mezzo", Mezzo)
            }
            if(binding.moto.isChecked){
            Mezzo = "Ciclomotore"
                var bundle = Bundle()
                bundle.putString("Id",Id)
                bundle.putString("Eta",Eta)
                bundle.putString("Struttura",Struttura)
                bundle.putString("Luogo",Luogo)
                bundle.putString("Persone",Persone)
                bundle.putString("DataI",DataI)
                bundle.putString("DataF",DataF)
                bundle.putString("Id_Struttura",Id_Struttura)
                bundle.putString("Mezzo",Mezzo)

                val CSF = RoomSelectionFragment()
                CSF.arguments = bundle
                Log.i("Mezzo", Mezzo)
                transaction.replace(R.id.fragmentContainerView4, CSF)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            if(binding.bici.isChecked){
            Mezzo = "Bicicletta"
                var bundle = Bundle()
                bundle.putString("Id",Id)
                bundle.putString("Eta",Eta)
                bundle.putString("Struttura",Struttura)
                bundle.putString("Luogo",Luogo)
                bundle.putString("Persone",Persone)
                bundle.putString("DataI",DataI)
                bundle.putString("DataF",DataF)
                bundle.putString("Id_Struttura",Id_Struttura)
                bundle.putString("Mezzo",Mezzo)

                val CSF = RoomSelectionFragment()
                CSF.arguments = bundle
                Log.i("Mezzo", Mezzo)
                transaction.replace(R.id.fragmentContainerView4, CSF)
                transaction.addToBackStack(null)
                transaction.commit()
        }


        }

        binding.procedisenza.setOnClickListener(){


            Mezzo = "No"

            var bundle = Bundle()
            bundle.putString("Id",Id)
            bundle.putString("Eta",Eta)
            bundle.putString("Struttura",Struttura)
            bundle.putString("Luogo",Luogo)
            bundle.putString("Persone",Persone)
            bundle.putString("DataI",DataI)
            bundle.putString("DataF",DataF)
            bundle.putString("Id_Struttura",Id_Struttura)
            bundle.putString("Mezzo",Mezzo)

            val CSF = RoomSelectionFragment()
            CSF.arguments = bundle

            transaction.replace(R.id.fragmentContainerView4, CSF)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        return binding.root
    }
}