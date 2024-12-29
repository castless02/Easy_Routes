package com.novaengine.easyroutes

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.JsonArray
import com.novaengine.easyroutes.databinding.RoomFragmentBinding
import com.novaengine.easyroutes.fragments.PaymentFragment
import com.novaengine.easyroutes.serversupport.DBQueriesService


class RoomFragment : Fragment(R.layout.room_fragment) {

    private lateinit var binding: RoomFragmentBinding
    private lateinit var fm: FragmentManager
    lateinit var text: String
    lateinit var sp: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Load the shared preferences
        sp = requireActivity().getSharedPreferences(getString(R.string.er_preferences_file), Context.MODE_PRIVATE)
        val Id = sp.getString("id", null).toString()

        // Layout Inflater
        binding = RoomFragmentBinding.inflate(inflater)

        // Fragment Manager
        fm = parentFragmentManager
        val transaction = fm.beginTransaction()

        // Arguments got from RoomSelectionFragment
        var Eta = arguments?.getString("Eta").toString()
        var Struttura = arguments?.getString("Struttura").toString()
        var Luogo = arguments?.getString("Luogo").toString()
        var Persone = arguments?.getString("Persone").toString()
        var DataI = arguments?.getString("DataI").toString()
        var DataF = arguments?.getString("DataF").toString()
        var Id_Struttura = arguments?.getString("Id_Struttura").toString()
        var Id_camera = arguments?.getString("Camera").toString()
        var Mezzo = arguments?.getString("Mezzo").toString()
        var PrezzoNuovo = "zero"

        // Query to get room information
        DBQueriesService().getRoomInfo(Id_camera) { userInfo ->
            if (userInfo != null) {
                val jsonArray = userInfo.get("queryset") as JsonArray
                val data = jsonArray.get(0).asJsonObject
                binding.title.text = data.get("Tipologia").asString
                binding.DestionationDesc.text = data.get("Descrizione").asString
                PrezzoNuovo = data.get("Prezzo").asString

                binding.prezzo.text = PrezzoNuovo + resources.getString(R.string.room_fragment_price_per_person)

                DBQueriesService().getProfileImage(data.get("Immagine").asString) { userProfileImage ->
                    if (userProfileImage != null) {
                        val drawable = BitmapDrawable(resources, userProfileImage)
                        binding.DestinationImage.setImageDrawable(drawable)
                    }
                }

            }
        }

        // Button to book the room
        binding.prenota.setOnClickListener() {

            // Put room's information into a bundle
            var bundle = Bundle()
            bundle.putString("Id", Id)
            bundle.putString("Eta", Eta)
            bundle.putString("Struttura", Struttura)
            bundle.putString("Luogo", Luogo)
            bundle.putString("Persone", Persone)
            bundle.putString("Mezzo", Mezzo)
            bundle.putString("DataI", DataI)
            bundle.putString("DataF", DataF)
            bundle.putString("Id_Struttura", Id_Struttura)
            bundle.putString("PrezzoNuovo", PrezzoNuovo)
            bundle.putString("Camera", Id_camera)

            // Payment Fragment
            val PF = PaymentFragment()
            PF.arguments = bundle
            transaction.addToBackStack("PaymentFragment")
            transaction.replace(R.id.fragmentContainerView4, PF)
            transaction.commit()

        }

        return binding.root

    }

}
