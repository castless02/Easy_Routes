package com.novaengine.easyroutes.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.JsonArray
import com.novaengine.easyroutes.PrenotaActivity
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.DestinationFragmentBinding
import com.novaengine.easyroutes.serversupport.DBQueriesService


class DestinationFragment : Fragment(R.layout.destination_fragment) {

    private lateinit var binding: DestinationFragmentBinding
    private lateinit var fm:FragmentManager
    lateinit var text:String
    lateinit var sp:SharedPreferences

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Load the shared preferences
        sp = requireActivity().getSharedPreferences(getString(R.string.er_preferences_file), Context.MODE_PRIVATE)
        val Id = sp.getString("id", null).toString()

        fm = parentFragmentManager
        val transaction = fm.beginTransaction()
        var Luogo = "null"
        var Struttura = "null"

        binding = DestinationFragmentBinding.inflate(inflater)

        var Id_Struttura = arguments?.getString("Id_Struttura").toString()
        Log.i("info", Id)


        DBQueriesService().getDestinationInfo(Id_Struttura) { userInfo ->
            if (userInfo != null) {
                val jsonArray = userInfo.get("queryset") as JsonArray
                val data = jsonArray.get(0).asJsonObject
                binding.title.text = data.get("Nome").asString
                Luogo = data.get("Luogo").asString
                Struttura = data.get("Nome").asString
                Log.i("str", Struttura)
                binding.regione.text = data.get("indirizzo").asString
                binding.DestionationDesc.text = data.get("Descrizione").asString

                DBQueriesService().getProfileImage(data.get("Immagine").asString) { userProfileImage ->
                    if (userProfileImage != null) {
                        val drawable = BitmapDrawable(resources, userProfileImage)
                        binding.DestinationImage.setImageDrawable(drawable)
                    }
                }

            }
            else {

            }
        }

        binding.prenota.setOnClickListener(){
            val i = Intent(context, PrenotaActivity::class.java)
            i.putExtra("Id", Id)
            i.putExtra("Luogo", Luogo)
            i.putExtra("Struttura", Struttura)
            i.putExtra("Id_Struttura", Id_Struttura)
            startActivity(i)
        }

            binding.share.setOnClickListener() {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "image/*"
                val shareText = resources.getString(R.string.destination_get_er)
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Easy Routes Promotion")
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareText)

                // Aggiungi l'immagine da condividere
                val imageUri =
                    Uri.parse("android.resource://com.novaengine.easyroutes/" + R.drawable.palermoback)
                sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri)

                startActivity(Intent.createChooser(sharingIntent, resources.getString(R.string.destination_share)))
            }

        return binding.root


    }
}