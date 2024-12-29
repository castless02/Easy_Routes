package com.novaengine.easyroutes.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.JsonArray
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.ResocontoFragmentBinding
import com.novaengine.easyroutes.serversupport.DBQueriesService
import com.novaengine.easyroutes.serversupport.dataclasses.PrenotazioneData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class ResocontoFragment : Fragment(R.layout.resoconto_fragment) {

    private lateinit var binding: ResocontoFragmentBinding
    private lateinit var fm:FragmentManager
    private lateinit var sp: SharedPreferences
    lateinit var text:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        fm = parentFragmentManager
        val transaction = fm.beginTransaction()
        binding = ResocontoFragmentBinding.inflate(inflater)

       // var Id = arguments?.getString("Id").toString()
        sp = requireActivity().getSharedPreferences(getString(R.string.er_preferences_file), Context.MODE_PRIVATE)
        var PrezzoFinale = 0
        val Id = sp.getString("id", null).toString()
        var Mezzo = arguments?.getString("Mezzo").toString()
        var Eta = arguments?.getString("Eta").toString()
        var Struttura = arguments?.getString("Struttura").toString()
        var Luogo = arguments?.getString("Luogo").toString()
        var Persone = arguments?.getString("Persone").toString()
        var DataI = arguments?.getString("DataI").toString()
        var DataF = arguments?.getString("DataF").toString()
        var Id_Struttura = arguments?.getString("Id_Struttura").toString()
        var Carta = arguments?.getString("Carta").toString()
        var PrezzoNuovo = arguments?.getString("PrezzoNuovo").toString()
        var Id_camera = arguments?.getString("Camera").toString()
        var Credito = "0"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val DataPrenotazione = LocalDateTime.now().format(formatter)
        Log.i("Mezzo", Mezzo)
        var bundle = Bundle()
        bundle.putString("Id",Id)

        val PF = PrenotatoFragment()
        PF.arguments = bundle

        val date1 = LocalDate.parse(DataI, DateTimeFormatter.ofPattern("yyyy-M-dd"))
        val date2 = LocalDate.parse(DataF, DateTimeFormatter.ofPattern("yyyy-M-dd"))
        val days = ChronoUnit.DAYS.between(date1, date2)
        var Prezzo:Int = PrezzoNuovo.toInt() * Persone.toInt() * (days.toInt() +1)

        binding.pagamento.setOnClickListener(){

        //Log.i("abbili", Id + " " + Eta + " " + Struttura + " " + Luogo + " " + Persone + " " + DataI + " " + DataF + " " + Id_Struttura + " " + Carta + " " + DataPrenotazione + " " + Mezzo)

            val prenotazioneData = PrenotazioneData(DataPrenotazione,Eta,DataI,DataF,Luogo,Struttura,Id_Struttura, Id, PrezzoFinale,Persone,Carta,Mezzo,Id_camera)

            // Start a login data instance
            DBQueriesService( ).changeCredito(Id,"0")

            DBQueriesService().prenota(prenotazioneData) { status ->

                    transaction.replace(R.id.fragmentContainerView4, PF)
                    transaction.addToBackStack(null)
                    transaction.commit()
            }
        }
       Log.i("ciao",days.toString())
       // println(date1.until(date2, ChronoUnit.DAYS))

        binding.luogoresoconto.text = Struttura
        binding.personeresoconto.text = Persone
        binding.cartaresoconto.text = "x" + Carta.subSequence( 12, 16)
        binding.datafine.text = DataF
        binding.datainizio.text = DataI

        DBQueriesService().getUserInformation(Id) { userInfo ->
            if (userInfo != null) {
                val jsonArray = userInfo.get("queryset") as JsonArray
                val data = jsonArray.get(0).asJsonObject
                Credito = data.get("Coupon").asString
                PrezzoFinale = Prezzo - Credito.toInt()
                if(PrezzoFinale < 0){
                    PrezzoFinale = 0
                }
                binding.creditoresoconto.text = Credito + "€"
                binding.prezzofinale.text = PrezzoFinale.toString() + "€"

            } else {

            }
        }

        binding.prezzo1.text = Prezzo.toString() + "€"


        return binding.root
    }
}