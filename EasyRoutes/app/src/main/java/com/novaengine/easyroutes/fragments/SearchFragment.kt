package com.novaengine.easyroutes.fragments

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.SearchFragmentBinding
import com.novaengine.easyroutes.recyclerviews.CategoriesSearchAdapter
import com.novaengine.easyroutes.recyclerviews.CategoriesSearchEntryModel
import com.novaengine.easyroutes.recyclerviews.SearchResultsAdapter
import com.novaengine.easyroutes.recyclerviews.SearchResultsEntryModel
import com.novaengine.easyroutes.serversupport.DBQueriesService

class SearchFragment : Fragment(R.layout.search_fragment) {

    private lateinit var binding: SearchFragmentBinding
    private lateinit var fm: FragmentManager
    private lateinit var resultsAdapter: SearchResultsAdapter
    private lateinit var resultsData: ArrayList<SearchResultsEntryModel>
    var destinationId = "null"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        var Id = arguments?.getString("Id").toString()
        var searchCategory = arguments?.getString("searchCategory").toString()
        var searchLocation = arguments?.getString("searchLocation").toString()

        var Id_struttura = "null"
        // Get search query
        val searchQuery = arguments?.getString("searchQuery").toString()

        var bundle = Bundle()
        bundle.putString("Id",Id)
        bundle.putString("searchCategory", searchCategory)
        bundle.putString("searchLocation", searchLocation)

        val MF = ExploreFragment()
        MF.arguments = bundle

        fm = parentFragmentManager
        val transaction = fm.beginTransaction()
        binding = SearchFragmentBinding.inflate(inflater)

        binding.searchView.requestFocus()

        // RecyclerView to get horizontal categories
        binding.categories2.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val categoriesData = ArrayList<CategoriesSearchEntryModel>()
        val categoriesAdapter = CategoriesSearchAdapter(categoriesData)

        binding.categories2.adapter = categoriesAdapter

        DBQueriesService().getCategories { result ->
            if (result != null && result.has("queryset")) {
                val jsonArray = result.get("queryset") as JsonArray
                for (i in 0 until jsonArray.size()) {
                    val jsonObject = jsonArray.get(i).asJsonObject
                    val data = jsonObject.get("Categoria").asString
                    categoriesData.add(CategoriesSearchEntryModel(data))
                }
                categoriesAdapter.notifyDataSetChanged()
            }
            else {
                Log.e("MenuFragment", "An error occurred while getting categories.")
            }
        }

        categoriesAdapter.setOnClickListener(object: CategoriesSearchAdapter.OnClickListener {
            override fun onClick(position: Int, model: CategoriesSearchEntryModel) {

                searchCategory = model.categoryName
                    val searchQuery = when {
                        searchCategory == null && searchLocation != null -> "SELECT S1.*, (SELECT AVG(S.Valutazione) FROM Stelle S INNER JOIN S_S V ON V.Id_valutazione = S.Id_valutazione WHERE V.Id_struttura = S1.Id_struttura) AS average_value FROM Struttura S1 WHERE S1.Luogo = '${searchLocation}' and S1.Categoria <> 'Promozioni';"
                        searchCategory != null && searchLocation == "null" -> "SELECT S1.*, (SELECT AVG(S.Valutazione) FROM Stelle S INNER JOIN S_S V ON V.Id_valutazione = S.Id_valutazione WHERE V.Id_struttura = S1.Id_struttura) AS average_value FROM Struttura S1 WHERE S1.Categoria = '${searchCategory}';"
                        else -> "SELECT S1.*, (SELECT AVG(S.Valutazione) FROM Stelle S INNER JOIN S_S V ON V.Id_valutazione = S.Id_valutazione WHERE V.Id_struttura = S1.Id_struttura) AS average_value FROM Struttura S1 WHERE S1.Categoria = '${searchCategory}' and S1.Luogo = '${searchLocation}';"
                    }
                    Log.v("SearchFragment", "Selected query is: ${searchQuery}")
                    Log.v("SearchFragment", searchLocation)

                    getSearchResults(searchQuery)
            }
        })

        // RecyclerView to get search results
        binding.recyclerViewrisultati.layoutManager = LinearLayoutManager(context)
        resultsData = ArrayList<SearchResultsEntryModel>()
        resultsAdapter = SearchResultsAdapter(resultsData)
        binding.recyclerViewrisultati.adapter = resultsAdapter

        getSearchResults(searchQuery)

        resultsAdapter.setOnClickListener(object: SearchResultsAdapter.OnClickListener {
            override fun onClick(position: Int, model: SearchResultsEntryModel) {
                val searchBundle = Bundle()
                searchBundle.putString("Id_Struttura", model.destination_Id.toString())
                Log.v("SearchFragment", searchBundle.getString("Id_Struttura").toString())

                val DF = DestinationFragment()
                DF.arguments = searchBundle

                transaction.addToBackStack(null)
                transaction.replace(R.id.fragmentContainerView2, DF)
                transaction.commit()

            }
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(searchInput: String?): Boolean {
                val searchQuery = "SELECT S1.*, (SELECT AVG(S.Valutazione) FROM Stelle S INNER JOIN S_S V ON V.Id_valutazione = S.Id_valutazione WHERE V.Id_struttura = S1.Id_struttura) AS average_value FROM Struttura S1 WHERE S1.Nome LIKE '%${searchInput}%';"
                Log.v("SearchFragment", "Selected query is: ${searchQuery}")
                getSearchResults(searchQuery)
                return false
            }

            override fun onQueryTextChange(searchInput: String?): Boolean {
                binding.categories2.visibility = View.GONE
                val searchQuery = "SELECT S1.*, (SELECT AVG(S.Valutazione) FROM Stelle S INNER JOIN S_S V ON V.Id_valutazione = S.Id_valutazione WHERE V.Id_struttura = S1.Id_struttura) AS average_value FROM Struttura S1 WHERE S1.Nome LIKE '%${searchInput}%';"
                Log.v("SearchFragment", "Selected query is: ${searchQuery}")
                getSearchResults(searchQuery)
                return true
            }

        })

        return binding.root
    }

    private fun getSearchResults(searchQuery: String) {

        if (!resultsData.isEmpty()) {
            resultsData.clear()
            resultsAdapter.notifyDataSetChanged()
        }

        DBQueriesService().getSearchResults(searchQuery) { results ->
            Log.v("SearchFragment", results.toString())
            if (results != null && results.has("queryset")) {
                val jsonArray = results.get("queryset") as JsonArray
                if(jsonArray.size() == 0){
                    binding.nulla.isVisible = true
                    binding.niente.isVisible = true
                }
                else{
                    binding.nulla.isVisible = false
                    binding.niente.isVisible = false
                }
                for (i in 0 until jsonArray.size()) {
                    val jsonObject = jsonArray.get(i).asJsonObject
                    val destinationId = jsonObject.get("Id_struttura").asString
                    val destinationName = jsonObject.get("Nome").asString
                    val destinationLocation = jsonObject.get("Luogo").asString

                    val destinationRating = when {
                        jsonObject.get("average_value").isJsonNull -> "0"
                        jsonObject.get("average_value").asString == "null" -> "0"
                        else -> jsonObject.get("average_value").asString
                    }

                    val destinationStars = jsonObject.get("Stelle").asString
                    val imageurl = jsonObject.get("Immagine").asString

                    DBQueriesService().getMeteImage(imageurl) { image ->
                        if (image != null) {
                            val drawable = BitmapDrawable(resources, image)
                            val entryModel = SearchResultsEntryModel( drawable, destinationName, destinationLocation, destinationId, destinationRating, destinationStars)
                            val insertIndex = resultsData.indexOfFirst { it.destinationRating < destinationRating }
                            if (insertIndex != -1) {
                                resultsData.add(insertIndex, entryModel)
                            } else {
                                resultsData.add(entryModel)
                            }
                            resultsAdapter.notifyDataSetChanged()
                        } else {
                            val entryModel = SearchResultsEntryModel( null, destinationName, destinationLocation, destinationId, destinationRating, destinationStars)
                            resultsData.add(entryModel)
                            resultsAdapter.notifyDataSetChanged()
                        }
                    }


                }

            }
        }
    }}
