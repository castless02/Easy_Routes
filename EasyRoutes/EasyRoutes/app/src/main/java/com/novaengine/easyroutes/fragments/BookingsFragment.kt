package com.novaengine.easyroutes.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.novaengine.easyroutes.BookInfoActivity
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.BookingsFragmentBinding
import com.novaengine.easyroutes.recyclerviews.BookingsAdapter
import com.novaengine.easyroutes.recyclerviews.BookingsEntryModel
import com.novaengine.easyroutes.serversupport.DBQueriesService

class BookingsFragment : Fragment(R.layout.bookings_fragment) {

    private lateinit var binding: BookingsFragmentBinding
    private lateinit var fm: FragmentManager
    private lateinit var sp: SharedPreferences
    private val logTag = BookingsFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Load the parent fragment manager
        fm = parentFragmentManager
        binding = BookingsFragmentBinding.inflate(inflater)

        return binding.root

    }

    override fun onResume() {
        super.onResume()

        // Load the shared preferences
        sp = requireActivity().getSharedPreferences(getString(R.string.er_preferences_file), Context.MODE_PRIVATE)
        val Id = sp.getString("id", null).toString()

        // User bookings recycler view
        binding.userBookingsRecyclerView.layoutManager = LinearLayoutManager(context)
        val userBookingsData = ArrayDeque<BookingsEntryModel>()
        val userBookingsAdapter = BookingsAdapter(userBookingsData)
        binding.userBookingsRecyclerView.adapter = userBookingsAdapter

        // Query to get user bookings
        DBQueriesService().getUserBookings(Id) { result ->
            if (result != null && result.has("queryset")) {

                val jsonArray = result.get("queryset") as JsonArray

                for (i in 0 until jsonArray.size()) {
                    val jsonObject = jsonArray.get(i).asJsonObject
                    val bookId = jsonObject.get("Id_Viaggio").asInt

                    val destinationName = when {
                        jsonObject.get("Nome").isJsonNull -> "Senza nome"
                        else -> jsonObject.get("Nome").asString
                    }

                    val startDate = when {
                        jsonObject.get("Data_andata").isJsonNull -> "Data non specificata"
                        else -> jsonObject.get("Data_andata").asString
                    }

                    val endDate = when {
                        jsonObject.get("Data_ritorno").isJsonNull -> "Data non specificata"
                        else -> jsonObject.get("Data_ritorno").asString
                    }

                    val location = when {
                        jsonObject.get("Luogo").isJsonNull -> "Luogo non specificato"
                        else -> jsonObject.get("Luogo").asString
                    }

                    userBookingsData.addFirst(BookingsEntryModel(bookId, destinationName, location, startDate, endDate))
                    userBookingsAdapter.notifyDataSetChanged()
                }
            }
            else {
                Log.e(logTag, "An error occured while getting user bookings...")
            }
        }

        // Action to do for each entry of the recycler view
        userBookingsAdapter.setOnClickListener(object: BookingsAdapter.OnClickListener {
            override fun onClick(position: Int, model: BookingsEntryModel) {
                val i = Intent(context, BookInfoActivity::class.java)
                i.putExtra("bookId", model.bookId.toString())
                i.putExtra("destinationName", model.destinationName)
                i.putExtra("startDate", model.startDate)
                i.putExtra("endDate", model.endDate)
                i.putExtra("location", model.location)
                startActivity(i)
            }
        })
    }

}