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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.JsonArray
import com.novaengine.easyroutes.MainActivity
import com.novaengine.easyroutes.ExploreActivity
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.MainProfileFragmentBinding
import com.novaengine.easyroutes.serversupport.DBQueriesService

class MainProfileFragment : Fragment(R.layout.main_profile_fragment) {

    private lateinit var binding: MainProfileFragmentBinding
    private lateinit var sp: SharedPreferences
    private lateinit var fm: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fm = parentFragmentManager
        val transaction = fm.beginTransaction()

        // Binding layout inflater
        binding = MainProfileFragmentBinding.inflate(inflater)

        // User identification
        val Id = arguments?.getString("Id").toString()
        val bundle = Bundle()
        bundle.putString("Id", Id)

        // Get user information callback
        DBQueriesService().getUserInformation(Id) { userInfo ->
            if (userInfo != null) {
                val jsonArray = userInfo.get("queryset") as JsonArray
                val data = jsonArray.get(0).asJsonObject
                binding.AccountName.text = data.get("Nome").asString
                binding.AccountCognome.text = data.get("Cognome").asString
                binding.AccountTelefono.text = data.get("Telefono").asString
                binding.AccountEmail.text = data.get("Email").asString
                binding.AccountResidenza.text = data.get("Residenza").asString
                binding.AccountSesso.text = data.get("Sesso").asString
                binding.credito.text = data.get("Coupon").asString + "€"

                DBQueriesService().getProfileImage(data.get("Foto_profilo").asString) { userProfileImage ->
                    if (userProfileImage != null) {
                        val drawable = BitmapDrawable(resources, userProfileImage)
                        binding.userProfileImage.setImageDrawable(drawable)
                    }
                }

            }
            else {
                Log.e("MainProfileFragment", "An error occurred when getting user information!")
            }
        }

        // Edit password section
        val EPF = EditPasswordFragment()
        EPF.arguments = bundle

        binding.eliminadatipagamento.setOnClickListener() {


            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(resources.getString(R.string.deletepaymentinfo_title))
                .setMessage(resources.getString(R.string.deletepaymentinfo_message))
                .setPositiveButton(resources.getString(R.string.user_book_delete_message_confirm)) { dialog, which ->


                    DBQueriesService().deletePaymentsId(Id) { okay ->
                        if (okay) {
                            Toast.makeText(requireContext(), resources.getString(R.string.deletepaymentinfo_success), Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(requireContext(), resources.getString(R.string.deletepaymentinfo_fail), Toast.LENGTH_SHORT).show()
                        }
                    }

                }
                .setNegativeButton("No") { dialog, which ->
                    Log.i("BookInfoActivity", "Action canceled.")
                }

            val alertDialog = builder.create()
            alertDialog.show()


        }

        binding.modificaPass.setOnClickListener() {
            transaction.replace(R.id.fragmentContainerView, EPF)
            transaction.addToBackStack("EPF")
            transaction.commit()
        }

        // Edit user profile picture section
        val EIF = EditIconFragment()
        EIF.arguments = bundle

        binding.matita.setOnClickListener() {
            transaction.replace(R.id.fragmentContainerView, EIF)
            transaction.addToBackStack("EIF")
            transaction.commit()
        }

        // Edit email section
        val EUMF = EditUserEmailFragment()
        EUMF.arguments = bundle

        binding.modificaemail.setOnClickListener() {
            transaction.replace(R.id.fragmentContainerView, EUMF)
            transaction.addToBackStack("EUMF")
            transaction.commit()
        }

        // Edit user data section
        val EUDF = EditUserDataFragment()
        EUDF.arguments = bundle

        binding.modificadati.setOnClickListener() {
            transaction.replace(R.id.fragmentContainerView, EUDF)
            transaction.addToBackStack("EUDF")
            transaction.commit()
        }

        val DF = EditUserDataFragment()
        DF.arguments = bundle

        val EF = EditUserEmailFragment()
        EF.arguments = bundle

        // Logout button
        binding.logoutbutton.setOnClickListener() {

            // Delete the id from the shared preferences
            sp = requireActivity().getSharedPreferences(getString(R.string.er_preferences_file), Context.MODE_PRIVATE)
            val spEditor = sp.edit()
            spEditor.remove("id")
            spEditor.apply()

            // Start the Main Activity
            val i = Intent(context, MainActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)

        }

        binding.indietro5.setOnClickListener() {
            val i = Intent(context, ExploreActivity::class.java)
            i.putExtra("Loggato", "vero")
            i.putExtra("Id", Id)
            startActivity(i)
        }

        return binding.root
    }

}