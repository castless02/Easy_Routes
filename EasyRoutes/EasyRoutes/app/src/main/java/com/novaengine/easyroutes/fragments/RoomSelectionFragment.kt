package com.novaengine.easyroutes.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.novaengine.easyroutes.RoomFragment
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.RoomSelectionFragmentBinding
import com.novaengine.easyroutes.recyclerviews.RoomsEntryModel
import com.novaengine.easyroutes.recyclerviews.RoomsAdapter
import com.novaengine.easyroutes.serversupport.DBQueriesService

class RoomSelectionFragment: Fragment(R.layout.room_selection_fragment)
{
    private lateinit var fm: FragmentManager
    private lateinit var binding: RoomSelectionFragmentBinding
    private lateinit var roomsAdapter: RoomsAdapter
    private lateinit var resultsData: ArrayList<RoomsEntryModel>
    private lateinit var sp: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Load the shared preferences
        sp = requireActivity().getSharedPreferences(getString(R.string.er_preferences_file), Context.MODE_PRIVATE)
        val Id = sp.getString("id", null).toString()

        // Layout inflater
        binding = RoomSelectionFragmentBinding.inflate(inflater)

        // Fragment Manager
        fm = parentFragmentManager
        val transaction = fm.beginTransaction()

        // Get arguments from the previous bundle
        var Eta = arguments?.getString("Eta").toString()
        var Struttura = arguments?.getString("Struttura").toString()
        var Luogo = arguments?.getString("Luogo").toString()
        var Persone = arguments?.getString("Persone").toString()
        var DataI = arguments?.getString("DataI").toString()
        var DataF = arguments?.getString("DataF").toString()
        var Id_Struttura = arguments?.getString("Id_Struttura").toString()
        var Mezzo = arguments?.getString("Mezzo").toString()
        var prezzo = "zero"

        // Recycler view for room selection
        binding.recycledefinitiva.layoutManager = LinearLayoutManager(context)
        resultsData = ArrayList<RoomsEntryModel>()
        roomsAdapter = RoomsAdapter(resultsData)
        binding.recycledefinitiva.adapter = roomsAdapter

        DBQueriesService().getRooms(Id_Struttura) { results ->
            if (results != null && results.has("queryset")) {
                val jsonArray = results.get("queryset") as JsonArray

                for (i in 0 until jsonArray.size()) {
                    val jsonObject = jsonArray.get(i).asJsonObject
                    val Id_camera = jsonObject.get("Id_camera").asString
                    val tipologia = jsonObject.get("Tipologia").asString
                    val prezzo = jsonObject.get("Prezzo").asString
                    val capienza = jsonObject.get("Capienza").asString
                    val imageurl = jsonObject.get("Immagine").asString
                    DBQueriesService().getRoomInfo(Id_camera) { userInfo ->
                        if (userInfo != null) {
                            val jsonArray = userInfo.get("queryset") as JsonArray
                            val data = jsonArray.get(0).asJsonObject

                            DBQueriesService().getProfileImage(data.get("Immagine").asString) { userProfileImage ->
                                if (userProfileImage != null) {
                                    val drawable = BitmapDrawable(resources, userProfileImage)
                                    resultsData.add(RoomsEntryModel(drawable, tipologia,capienza,Id_camera))
                                    roomsAdapter.notifyDataSetChanged()
                                }
                            }

                        }
                    }
                    //DBQueriesService().getProfileImage(imageurl) { image ->

                        //if (image != null) {
                          //  var drawable = BitmapDrawable(resources, image)
                            //resultsData.add(RoomsEntryModel(drawable, tipologia,capienza,Id_camera))
                            //roomsAdapter.notifyDataSetChanged()
                        //} else {
                           // resultsData.add(RoomsEntryModel(null, tipologia, prezzo,Id_camera))
                            //roomsAdapter.notifyDataSetChanged()
                        //}
              //      }
             }
            }

            // Action to do for each entry of rooms adapter
            roomsAdapter.setOnClickListener(object : RoomsAdapter.OnClickListener {

                override fun onClick(position: Int, model: RoomsEntryModel) {

                    // Put data into a bundle
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
                    bundle.putString("Prezzo", prezzo)
                    bundle.putString("Camera", model.Id_camera)

                    // Room Fragment
                    val RF = RoomFragment()
                    RF.arguments = bundle
                    transaction.addToBackStack("RoomFragment")
                    transaction.replace(R.id.fragmentContainerView4, RF)
                    transaction.commit()

                }
            })

        }
        return binding.root
    }

}


