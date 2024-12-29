package com.novaengine.easyroutes.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.novaengine.easyroutes.AccountActivity
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.ExploreFragmentBinding
import com.novaengine.easyroutes.recyclerviews.CategoriesAdapter
import com.novaengine.easyroutes.recyclerviews.CategoriesEntryModel
import com.novaengine.easyroutes.recyclerviews.RecommendedDestinationsEntryModel
import com.novaengine.easyroutes.recyclerviews.RecommendedDestinationsAdapter
import com.novaengine.easyroutes.serversupport.DBQueriesService

class ExploreFragment : Fragment(R.layout.activity_explore) {

    private lateinit var binding: ExploreFragmentBinding
    private lateinit var fm: FragmentManager
    private lateinit var sp: SharedPreferences
    lateinit var text:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Load the shared preferences
        sp = requireActivity().getSharedPreferences(getString(R.string.er_preferences_file), Context.MODE_PRIVATE)
        val Id = sp.getString("id", null).toString()

        var bundle = Bundle()
        bundle.putString("Id",Id)

        val MF = ExploreFragment()
        MF.arguments = bundle

        val SF = SearchFragment()
        SF.arguments = bundle

        fm = parentFragmentManager
        val transaction = fm.beginTransaction()

        // Binding layout inflater
        binding = ExploreFragmentBinding.inflate(inflater)

        // Get user information callback
        DBQueriesService().getUserInformation(Id) { userInfo ->
            if (userInfo != null) {
                val jsonArray = userInfo.get("queryset") as JsonArray
                val data = jsonArray.get(0).asJsonObject
                binding.accountNameText.text = data.get("Nome").asString

                DBQueriesService().getProfileImage(data.get("Foto_profilo").asString) { userProfileImage ->
                    if (userProfileImage != null) {
                        val drawable = BitmapDrawable(resources, userProfileImage)
                        binding.account.setImageDrawable(drawable)
                    }
                }

            }
            else {
                Log.e("MenuFragment", "An error occured while getting user information.")
            }
        }

        // Recommended destinations recycler view
        binding.recommendedDestinationsRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val recommendedDestinationsData = ArrayList<RecommendedDestinationsEntryModel>()
        val recommendedDestinationsAdapter = RecommendedDestinationsAdapter(recommendedDestinationsData)
        binding.recommendedDestinationsRecyclerView.adapter = recommendedDestinationsAdapter

        DBQueriesService().getMostRequestedDestinations { result ->
            if (result != null && result.has("queryset")) {
                val jsonArray = result.get("queryset") as JsonArray

                for (i in 0 until jsonArray.size()) {
                    val jsonObject = jsonArray.get(i).asJsonObject
                    val data = jsonObject.get("Luogo").asString
                    val image = jsonObject.get("Immagine_luogo").asString
                    DBQueriesService().getMeteImage(image) { image ->
                        if (image != null) {
                            var drawable = BitmapDrawable(resources, image)
                            recommendedDestinationsData.add(RecommendedDestinationsEntryModel(drawable, data))
                            recommendedDestinationsAdapter.notifyDataSetChanged()
                        }
                        else{
                            recommendedDestinationsData.add(RecommendedDestinationsEntryModel(R.drawable.ic_launcher_background.toDrawable(), data))
                            recommendedDestinationsAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
            else {
                Log.e("ExploreFragment", "An error occured while getting recommended destinations.")
            }
        }

        recommendedDestinationsAdapter.setOnClickListener(object: RecommendedDestinationsAdapter.OnClickListener {
            override fun onClick(position: Int, model: RecommendedDestinationsEntryModel) {
                val transaction = fm.beginTransaction()
                var Luogo = model.destinationName
                bundle.putString("searchLocation", model.destinationName.toString())
                bundle.putString("searchQuery", "SELECT S1.*, (SELECT AVG(S.Valutazione) FROM Stelle S INNER JOIN S_S V ON V.Id_valutazione = S.Id_valutazione WHERE V.Id_struttura = S1.Id_struttura) AS average_value FROM Struttura S1 WHERE S1.Categoria = '${model.destinationName}';")
                bundle.putString("searchQuery", "SELECT S1.*, (SELECT AVG(S.Valutazione) FROM Stelle S INNER JOIN S_S V ON V.Id_valutazione = S.Id_valutazione WHERE V.Id_struttura = S1.Id_struttura) AS average_value FROM Struttura S1 WHERE S1.Luogo = '${model.destinationName}' AND Categoria <> 'Promozioni';")
                transaction.replace(R.id.fragmentContainerView2, SF)
                transaction.addToBackStack("SearchFragment")
                transaction.commit()
            }
        })

        // Categories recycler view
        binding.categoriesRecyclerView.layoutManager = GridLayoutManager(context, 1)
        val categoriesData = ArrayList<CategoriesEntryModel>()
        val categoriesAdapter = CategoriesAdapter(categoriesData)
        binding.categoriesRecyclerView.adapter = categoriesAdapter

        DBQueriesService().getCategories { result ->
            if (result != null && result.has("queryset")) {
                val jsonArray = result.get("queryset") as JsonArray
                for (i in 0 until jsonArray.size()) {
                    val jsonObject = jsonArray.get(i).asJsonObject
                    val data = jsonObject.get("Categoria").asString
                    val ImmagineCategoria = jsonObject.get("Immagine_categoria").asString

                    DBQueriesService().getMeteImage(ImmagineCategoria) { image ->
                        if (image != null) {
                            var drawable = BitmapDrawable(resources, image)
                            categoriesData.add(CategoriesEntryModel(data,drawable))
                            categoriesAdapter.notifyDataSetChanged()
                        } else {
                            categoriesData.add(CategoriesEntryModel(data,R.drawable.ic_launcher_background.toDrawable() ))
                            categoriesAdapter.notifyDataSetChanged()
                        }
                    }
                }

                }

        }

        categoriesAdapter.setOnClickListener(object: CategoriesAdapter.OnClickListener {
            override fun onClick(position: Int, model: CategoriesEntryModel) {
                val transaction = fm.beginTransaction()
                var searchLocation = "null"

                var Categoria = model.categoryName
                bundle.putString("searchCategory", model.categoryName.toString())
                bundle.putString("searchLocation", searchLocation)
                bundle.putString("searchQuery", "SELECT S1.*, (SELECT AVG(S.Valutazione) FROM Stelle S INNER JOIN S_S V ON V.Id_valutazione = S.Id_valutazione WHERE V.Id_struttura = S1.Id_struttura) AS average_value FROM Struttura S1 WHERE S1.Categoria = '${model.categoryName}';")
                transaction.replace(R.id.fragmentContainerView2, SF)
                transaction.addToBackStack("SearchFragment")
                transaction.commit()
            }
        })

        binding.account.setOnClickListener {
            val i = Intent(context, AccountActivity::class.java)
            i.putExtra("Id", Id)
            startActivity(i)
        }

        binding.searchButton.setOnClickListener {
            val transaction = fm.beginTransaction()
            transaction.replace(R.id.fragmentContainerView2, SearchFragment())
            transaction.addToBackStack("SearchFragment")
            transaction.commit()
        }

        return binding.root
    }
}