package com.novaengine.easyroutes.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.JsonArray
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.EditUserDataFragmentBinding
import com.novaengine.easyroutes.serversupport.DBQueriesService

class EditUserDataFragment : Fragment(R.layout.edit_user_data_fragment) {

    private lateinit var binding: EditUserDataFragmentBinding
    private lateinit var fm: FragmentManager

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fm = parentFragmentManager
        val transaction = fm.beginTransaction()

        // Binding layout inflater
        binding = EditUserDataFragmentBinding.inflate(inflater)

        // User identification
        val Id = arguments?.getString("Id").toString()
        val bundle = Bundle()
        bundle.putString("Id", Id)

        // Get user information callback
        DBQueriesService().getUserInformation(Id) { userInfo ->
            if (userInfo != null) {
                val jsonArray = userInfo.get("queryset") as JsonArray
                val data = jsonArray.get(0).asJsonObject
                binding.accountNameText.hint = data.get("Nome").asString
                binding.accountSurnameText.hint = data.get("Cognome").asString
                binding.accountPhoneText.hint = data.get("Telefono").asString
                binding.accountLocationText.hint = data.get("Residenza").asString

                when (data.get("Sesso").asString) {
                    "M" -> binding.sexRadioButtonMan.isChecked = true
                    "F" -> binding.sexRadioButtonWoman.isChecked = true
                    else -> binding.sexRadioButtonOther.isChecked = true
                }

            }
            else {
                Log.e("EditUserDataFragment", "An error occured when getting user information!")
            }
        }

        val MP = MainProfileFragment()
        MP.arguments = bundle

        val DMF = DatiModificatiFragment()
        DMF.arguments = bundle

        binding.goBackButton.setOnClickListener(){

            transaction.replace(R.id.fragmentContainerView, MP)
            //transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.registerButton.setOnClickListener(){

            // To Do
            if(binding.accountNameText.text.isNotEmpty()){
                binding.icon1.visibility = View.INVISIBLE
                if(binding.accountSurnameText.text.isNotEmpty()){
                    binding.icon2.visibility = View.INVISIBLE
                    if(binding.accountPhoneText.text.length == 10){
                        binding.icon3.visibility = View.INVISIBLE
                        if(binding.registerButton.text.isNotEmpty()){
                            binding.icon1.visibility = View.INVISIBLE
                            binding.icon2.visibility = View.INVISIBLE
                            binding.icon3.visibility = View.INVISIBLE
                            binding.icon4.visibility = View.INVISIBLE
                            binding.errore2.visibility = View.INVISIBLE

                            DBQueriesService( ).changeNome(Id,binding.accountNameText.text.toString())
                            DBQueriesService( ).changeCognome(Id,binding.accountSurnameText.text.toString())
                            DBQueriesService( ).changeNumber(Id,binding.accountPhoneText.text.toString())
                            DBQueriesService( ).changeResidenza(Id,binding.accountLocationText.text.toString())

                            if(binding.sexRadioButtonMan.isChecked){
                              //  DbmsQuery().changeSex("M", Id)
                                DBQueriesService( ).changeSesso(Id,"M")
                            }else if(binding.sexRadioButtonWoman.isChecked){
                               // DbmsQuery().changeSex("F", Id)
                                DBQueriesService( ).changeSesso(Id,"F")
                            }else if(binding.sexRadioButtonOther.isChecked){
                               // DbmsQuery().changeSex("A", Id)
                                DBQueriesService( ).changeSesso(Id,"A")
                            }

                            transaction.replace(R.id.fragmentContainerView, DMF)
                            transaction.commit()
                        }else{
                            binding.icon4.visibility = View.VISIBLE
                            binding.errore2.visibility = View.VISIBLE

                            if(binding.accountNameText.text.isEmpty()){
                                binding.icon1.visibility = View.VISIBLE
                            }
                            else{
                                binding.icon1.visibility = View.INVISIBLE
                            }
                            if(binding.accountSurnameText.text.isEmpty()){
                                binding.icon2.visibility = View.VISIBLE
                            }
                            else{
                                binding.icon2.visibility = View.INVISIBLE
                            }
                            if(binding.accountEmail.text.isEmpty()){
                                binding.icon3.visibility = View.VISIBLE
                            }
                            else{
                                binding.icon3.visibility = View.INVISIBLE
                            }
                        }
                    }else{
                        binding.icon3.visibility = View.VISIBLE
                        binding.errore2.visibility = View.VISIBLE

                        if(binding.accountNameText.text.isEmpty()){
                            binding.icon1.visibility = View.VISIBLE
                        }
                        else{
                            binding.icon1.visibility = View.INVISIBLE
                        }
                        if(binding.accountSurnameText.text.isEmpty()){
                            binding.icon2.visibility = View.VISIBLE
                        }
                        else{
                            binding.icon2.visibility = View.INVISIBLE
                        }
                        if(binding.accountLocationText.text.isEmpty()){
                            binding.icon4.visibility = View.VISIBLE
                        }
                        else{
                            binding.icon4.visibility = View.INVISIBLE
                        }

                    }
                }else{
                    binding.icon2.visibility = View.VISIBLE
                    binding.errore2.visibility = View.VISIBLE
                    if(binding.accountNameText.text.isEmpty()){
                        binding.icon1.visibility = View.VISIBLE
                    }
                    else{
                        binding.icon1.visibility = View.INVISIBLE
                    }
                    if(binding.accountPhoneText.text.isEmpty()){
                        binding.icon3.visibility = View.VISIBLE
                    }
                    else{
                        binding.icon3.visibility = View.INVISIBLE
                    }
                    if(binding.accountLocationText.text.isEmpty()){
                        binding.icon4.visibility = View.VISIBLE
                    }
                    else{
                        binding.icon4.visibility = View.INVISIBLE
                    }
                }
            }else{
                binding.errore2.visibility = View.VISIBLE
                binding.icon1.visibility = View.VISIBLE
                if(binding.accountSurnameText.text.isEmpty()){
                    binding.icon2.visibility = View.VISIBLE
                }
                else{
                    binding.icon2.visibility = View.INVISIBLE
                }
                if(binding.accountPhoneText.text.isEmpty()){
                    binding.icon3.visibility = View.VISIBLE
                }
                else{
                    binding.icon3.visibility = View.INVISIBLE
                }
                if(binding.accountLocationText.text.isEmpty()){
                    binding.icon4.visibility = View.VISIBLE
                }
                else{
                    binding.icon4.visibility = View.INVISIBLE
                }
            }

        }

        return binding.root

    }

}