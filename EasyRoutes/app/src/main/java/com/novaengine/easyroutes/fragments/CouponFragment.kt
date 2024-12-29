package com.novaengine.easyroutes

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
import com.novaengine.easyroutes.databinding.CouponFragmentBinding
import com.novaengine.easyroutes.serversupport.DBQueriesService

class CouponFragment : Fragment(R.layout.coupon_fragment) {

    // Lateinit variables
    private lateinit var binding: CouponFragmentBinding
    private lateinit var fm: FragmentManager
    private lateinit var sp: SharedPreferences
    private var logTag = CouponFragment::class.java.simpleName


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Load the shared preferences
        sp = requireActivity().getSharedPreferences(getString(R.string.er_preferences_file), Context.MODE_PRIVATE)
        val Id = sp.getString("id", null).toString()

        // Layout inflater
        binding = CouponFragmentBinding.inflate(inflater)

        // Fragment Mnaager
        fm = parentFragmentManager
        val transaction = fm.beginTransaction()

        // Initialize a bundle
        var bundle = Bundle()
        bundle.putString("Id",Id)

        // Old amount
        var VecchioCredito = "0"

        // Coupon inserted fragment
        binding.inseriscicoupon.setOnClickListener {

            // Query to check coupon
            DBQueriesService().Checkcoupon(binding.couponfield.text.toString()) { result ->

                if (result != null) {
                    binding.errorecoupon.visibility = View.INVISIBLE

                    val Credito = result.toString()
                    bundle.putString("Credito",Credito)
                    DBQueriesService().getUserInformation(Id) { userInfo ->
                        if (userInfo != null) {

                            val jsonArray = userInfo.get("queryset") as JsonArray
                            val data = jsonArray.get(0).asJsonObject
                            VecchioCredito = data.get("Coupon").asString
                            Log.i("creditos", VecchioCredito + "old")

                            var NuovoCredito = (VecchioCredito.toInt() + Credito.toInt()).toString()

                            DBQueriesService( ).changeCredito(Id,NuovoCredito)

                            DBQueriesService().deleteCoupon(binding.couponfield.text.toString()) { okay ->
                                if (okay) {
                                    Log.i(logTag, "Coupon deleted.")
                                }
                                else {
                                    Log.e(logTag, "An error occurred while deleting coupon.")
                                }
                            }

                            // Inserted coupon fragment
                            val CMF = CouponMessoFragment()
                            CMF.arguments = bundle

                            transaction.addToBackStack("CouponMessoFragment")
                            transaction.replace(R.id.fragmentContainerView2, CMF)
                            transaction.commit()

                        }
                        else {
                            Log.e(logTag, "An error occured when getting user information!")
                        }
                    }

                } else {
                    binding.errorecoupon.visibility = View.VISIBLE
                }

            }
        }

        return binding.root

    }
}