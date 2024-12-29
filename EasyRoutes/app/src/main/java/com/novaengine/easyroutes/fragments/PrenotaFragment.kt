package com.novaengine.easyroutes.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.MezziFragment
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.PrenotaFragmentBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar


class PrenotaFragment : Fragment(R.layout.prenota_fragment) {

    private lateinit var binding: PrenotaFragmentBinding
    private lateinit var fm:FragmentManager
    lateinit var text:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        fm = parentFragmentManager
        val transaction = fm.beginTransaction()
        binding = PrenotaFragmentBinding.inflate(inflater)

        var Id = arguments?.getString("Id").toString()
        var Luogo = arguments?.getString("Luogo").toString()
        var Struttura = arguments?.getString("Struttura").toString()
        var Id_Struttura = arguments?.getString("Id_Struttura").toString()
        Log.i("info", Id)


        binding.nomeluogo.text = Struttura


        binding.vaiapagare.setOnClickListener(){

                  if(binding.radioButton11.isChecked){

                      if(binding.data1.text.isNotEmpty()){
                          val d1 = binding.data1.text.toString()
                          if(binding.data2.text.isNotEmpty()){
                              val d2 = binding.data2.text.toString()


                              val date1 = LocalDate.parse(d1, DateTimeFormatter.ofPattern("yyyy-M-dd"))
                              val date2 = LocalDate.parse(d2, DateTimeFormatter.ofPattern("yyyy-M-dd"))

                              if(date1.isEqual(date2)){

                                  binding.erroredate.isVisible = false

                                  var Persone:String = binding.spinner.getSelectedItem().toString();
                                  var Eta:String = binding.spinner2.getSelectedItem().toString();
                                  var DataI:String = binding.data1.text.toString()
                                  var DataF:String = binding.data2.text.toString()
                                  var bundle = Bundle()
                                  var Mezzo = "No"
                                  bundle.putString("Mezzo",Mezzo)
                                  bundle.putString("Id",Id)
                                  bundle.putString("Eta",Eta)
                                  bundle.putString("Struttura",Struttura)
                                  bundle.putString("Luogo",Luogo)
                                  bundle.putString("Persone",Persone)
                                  bundle.putString("DataI",DataI)
                                  bundle.putString("DataF",DataF)
                                  bundle.putString("Id_Struttura",Id_Struttura)
                                  val MF = MezziFragment()
                                  MF.arguments = bundle

                                  val CSF = RoomSelectionFragment()
                                  CSF.arguments = bundle


                                  transaction.replace(R.id.fragmentContainerView4, MF)
                                  transaction.addToBackStack(null)
                                  transaction.commit()
                              }
                              if(date1.isBefore(date2)){

                                      binding.erroredate.isVisible = false

                                      var Persone:String = binding.spinner.getSelectedItem().toString();
                                      var Eta:String = binding.spinner2.getSelectedItem().toString();
                                      var DataI:String = binding.data1.text.toString()
                                      var DataF:String = binding.data2.text.toString()
                                      var bundle = Bundle()
                                      var Mezzo = "No"
                                      bundle.putString("Mezzo",Mezzo)
                                      bundle.putString("Id",Id)
                                      bundle.putString("Eta",Eta)
                                      bundle.putString("Struttura",Struttura)
                                      bundle.putString("Luogo",Luogo)
                                      bundle.putString("Persone",Persone)
                                      bundle.putString("DataI",DataI)
                                      bundle.putString("DataF",DataF)
                                      bundle.putString("Id_Struttura",Id_Struttura)
                                      val MF = MezziFragment()
                                      MF.arguments = bundle

                                      val CSF = RoomSelectionFragment()
                                      CSF.arguments = bundle


                                      transaction.replace(R.id.fragmentContainerView4, MF)
                                      transaction.addToBackStack(null)
                                      transaction.commit()

                                  }else{
                                      binding.erroredate.isVisible = true
                                  }






                          }else{
                              binding.erroredate.isVisible = true
                          }
                      }else{
                          binding.erroredate.isVisible = true
                      }
                  }else{
                      if(binding.data1.text.isNotEmpty()){
                          val d1 = binding.data1.text.toString()
                          if(binding.data2.text.isNotEmpty()){
                              val d2 = binding.data2.text.toString()


                              val date1 = LocalDate.parse(d1, DateTimeFormatter.ofPattern("yyyy-M-dd"))
                              val date2 = LocalDate.parse(d2, DateTimeFormatter.ofPattern("yyyy-M-dd"))

                              if(date1.isEqual(date2)) {
                                  binding.erroredate.isVisible = false

                                  var Persone:String = binding.spinner.getSelectedItem().toString();
                                  var Eta:String = binding.spinner2.getSelectedItem().toString();
                                  var DataI:String = binding.data1.text.toString()
                                  var DataF:String = binding.data2.text.toString()
                                  var bundle = Bundle()


                                  bundle.putString("Id",Id)
                                  bundle.putString("Eta",Eta)
                                  bundle.putString("Struttura",Struttura)
                                  bundle.putString("Luogo",Luogo)
                                  bundle.putString("Persone",Persone)
                                  bundle.putString("DataI",DataI)
                                  bundle.putString("DataF",DataF)
                                  bundle.putString("Mezzo", "No")
                                  bundle.putString("Id_Struttura",Id_Struttura)
                                  val PF = PaymentFragment()
                                  PF.arguments = bundle


                                  val CSF = RoomSelectionFragment()
                                  CSF.arguments = bundle

                                  transaction.replace(R.id.fragmentContainerView4, CSF)
                                  transaction.addToBackStack(null)
                                  transaction.commit()
                              }
                                  if(date1.isBefore(date2)){

                                      binding.erroredate.isVisible = false

                                      var Persone:String = binding.spinner.getSelectedItem().toString();
                                      var Eta:String = binding.spinner2.getSelectedItem().toString();
                                      var DataI:String = binding.data1.text.toString()
                                      var DataF:String = binding.data2.text.toString()
                                      var bundle = Bundle()


                                      bundle.putString("Id",Id)
                                      bundle.putString("Eta",Eta)
                                      bundle.putString("Struttura",Struttura)
                                      bundle.putString("Luogo",Luogo)
                                      bundle.putString("Persone",Persone)
                                      bundle.putString("DataI",DataI)
                                      bundle.putString("DataF",DataF)
                                      bundle.putString("Mezzo", "No")
                                      bundle.putString("Id_Struttura",Id_Struttura)
                                      Log.v("PrenotaFragment", bundle.getString("Mezzo").toString())
                                      val PF = PaymentFragment()
                                      PF.arguments = bundle


                                      val CSF = RoomSelectionFragment()
                                      CSF.arguments = bundle

                                      transaction.replace(R.id.fragmentContainerView4, CSF)
                                      transaction.addToBackStack(null)
                                      transaction.commit()

                                  }else{
                                      binding.erroredate.isVisible = true
                                  }






                          }else{
                              binding.erroredate.isVisible = true
                          }
                      }else{
                          binding.erroredate.isVisible = true
                      }
                  }



        }

        val spinner = binding.spinner
        val items = arrayOf("1", "2", "3", "4")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val spinner2 = binding.spinner2
        val items2 = (18..99).map { it.toString() }.toTypedArray()
        val adapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter2

        // click listener for our button
        binding.idBtnPickDat.setOnClickListener {
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

                        binding.data1.text = (year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString())
                    },
                    // on below line we are passing year, month
                    // and day for the selected date in our date picker.
                    year,
                    month,
                    day
                )
            }
            // at last we are calling show
            // to display our date picker dialog.
            if (datePickerDialog != null) {
                datePickerDialog?.datePicker?.minDate = c.timeInMillis
                datePickerDialog.show()
            }
        }

        binding.idBtnPickDat2.setOnClickListener {
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

                        binding.data2.text = (year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString())
                    },
                    // on below line we are passing year, month
                    // and day for the selected date in our date picker.
                    year,
                    month,
                    day
                )
            }
            // at last we are calling show
            // to display our date picker dialog.
            if (datePickerDialog != null) {
                datePickerDialog?.datePicker?.minDate = c.timeInMillis
                datePickerDialog.show()
            }
        }


        return binding.root

    }

}