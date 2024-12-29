package com.novaengine.easyroutes.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.PaymentFragmentBinding
import com.novaengine.easyroutes.serversupport.DBQueriesService
import java.util.Calendar


class PaymentFragment : Fragment(R.layout.payment_fragment) {

    private lateinit var binding: PaymentFragmentBinding
    private lateinit var fm:FragmentManager
    lateinit var text:String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val packageName = requireContext().packageName
        fm = parentFragmentManager
        val transaction = fm.beginTransaction()
        var jsonObject: JsonObject? = null
//passaggio di paramentricini
        var Id = arguments?.getString("Id").toString()
        var Id_camera = arguments?.getString("Camera").toString()
        var Mezzo = arguments?.getString("Mezzo").toString()
        var Eta = arguments?.getString("Eta").toString()
        var Struttura = arguments?.getString("Struttura").toString()
        var Luogo = arguments?.getString("Luogo").toString()
        var Persone = arguments?.getString("Persone").toString()
        var DataI = arguments?.getString("DataI").toString()
        var DataF = arguments?.getString("DataF").toString()
        var Id_Struttura = arguments?.getString("Id_Struttura").toString()
        var PrezzoNuovo = arguments?.getString("PrezzoNuovo").toString()

        binding = PaymentFragmentBinding.inflate(inflater)

        DBQueriesService().getPaymentsInfo(Id) { userInfo ->
            if (userInfo != null) {
                val jsonArray = userInfo.get("queryset") as JsonArray

                if (!jsonArray.isJsonNull && jsonArray.size() > 0) {
                    jsonObject = jsonArray.get(0).asJsonObject
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle(resources.getString(R.string.paymentinfo_title))
                        .setMessage(resources.getString(R.string.paymentinfo_message))
                        .setPositiveButton(resources.getString(R.string.user_book_delete_message_confirm)) { dialog, which ->



                            binding.intestatario.setText(jsonObject?.get("Intestatario")?.asString)
                            binding.carta.setText(jsonObject?.get("Num_carta")?.asString)
                            binding.cvv.setText(jsonObject?.get("CVV")?.asString)
                            binding.datascadenzatext.setText(jsonObject?.get("Data_scadenza")?.asString)
                            binding.radioButton11.isVisible = false
                            binding.radioButton12.isVisible = false
                            binding.textView14.isVisible = false

                        }
                        .setNegativeButton("No") { dialog, which ->
                            Log.i("BookInfoActivity", "Action canceled.")
                        }

                    val alertDialog = builder.create()
                    alertDialog.show()





                }


                }
            }

        binding.datecard.setOnClickListener {
            // on below line we are getting
            // the instance of our calendar.
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = context?.let { it1 ->
                DatePickerDialog(
                    it1,
                    { view, year, monthOfYear, dayOfMonth ->
                        // on below line we are setting
                        // date to our text view.
                        binding.datascadenzatext.text = ((monthOfYear + 1).toString() + "-" + year.toString())
                    },
                    // on below line we are passing year, month
                    // and day for the selected date in our date picker.
                    year,
                    month,
                    day
                )
            }
            if (datePickerDialog != null) {
                datePickerDialog.show()
            }
        }

binding.vaialriepilogo.setOnClickListener(){

    if(binding.intestatario.text.isNotEmpty() && binding.carta.text.length == 16 && binding.datascadenzatext.text.isNotEmpty() && binding.cvv.text.length == 3) {

        if(binding.radioButton11.isChecked){

            DBQueriesService().salvacarta(binding.intestatario.text.toString(),binding.carta.text.toString(),binding.cvv.text.toString(),binding.datascadenzatext.text.toString(),Id) { status ->
                Log.i("info" , "query fatta:")
            }

            //if(android.strudel.isNotBugged){
            binding.erroreccv.isVisible = false
            var Carta: String = binding.carta.text.toString()
            var bundle = Bundle()
            bundle.putString("Id",Id)
            bundle.putString("Eta",Eta)
            bundle.putString("Mezzo",Mezzo)
            bundle.putString("Struttura",Struttura)
            bundle.putString("Luogo",Luogo)
            bundle.putString("Persone",Persone)
            bundle.putString("Camera",Id_camera)
            bundle.putString("DataI",DataI)
            bundle.putString("DataF",DataF)
            bundle.putString("Id_Struttura",Id_Struttura)
            bundle.putString("Carta",Carta)
            bundle.putString("PrezzoNuovo",PrezzoNuovo)

            val RF = ResocontoFragment()
            RF.arguments = bundle

            transaction.replace(R.id.fragmentContainerView4, RF)
            transaction.addToBackStack(null)
            transaction.commit()
        }else{
            Log.i("Mezzo", Mezzo)
            //if(android.strudel.isNotBugged){
            binding.erroreccv.isVisible = false
            var Carta: String = binding.carta.text.toString()
            var bundle = Bundle()
            bundle.putString("Id",Id)
            bundle.putString("Eta",Eta)
            bundle.putString("Struttura",Struttura)
            bundle.putString("Camera",Id_camera)
            bundle.putString("Luogo",Luogo)
            bundle.putString("Persone",Persone)
            bundle.putString("Mezzo",Mezzo)
            bundle.putString("DataI",DataI)
            bundle.putString("DataF",DataF)
            bundle.putString("Id_Struttura",Id_Struttura)
            bundle.putString("Carta",Carta)
            bundle.putString("PrezzoNuovo",PrezzoNuovo)

            val RF = ResocontoFragment()
            RF.arguments = bundle

            transaction.replace(R.id.fragmentContainerView4, RF)
            transaction.addToBackStack(null)
            transaction.commit()
        }



}else{
        binding.erroreintestatario.isVisible = binding.intestatario.text.isEmpty()
        binding.erroredata.isVisible = binding.datascadenzatext.text.isEmpty()
        binding.erroreccv.isVisible = binding.cvv.text.length != 3
        binding.errorenumero.isVisible = binding.carta.text.length != 16
    }



}
        return binding.root
    }
}