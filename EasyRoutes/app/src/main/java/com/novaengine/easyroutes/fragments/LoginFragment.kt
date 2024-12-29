package com.novaengine.easyroutes.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.ExploreActivity
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.databinding.LoginFragmentBinding
import com.novaengine.easyroutes.serversupport.DBQueriesService
import com.novaengine.easyroutes.serversupport.dataclasses.LoginData

class LoginFragment : Fragment(R.layout.login_fragment) {

    // Lateinit variables
    private lateinit var binding: LoginFragmentBinding
    private lateinit var fm: FragmentManager
    private lateinit var sp: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Layout inflater
        binding = LoginFragmentBinding.inflate(inflater)

        // Fragment manager
        fm = parentFragmentManager
        val transaction = fm.beginTransaction()

        // Register button onClickListener
        binding.registerButton.setOnClickListener {
            transaction.replace(R.id.fragmentContainerView, RegisterFragment())
            transaction.addToBackStack("RegisterFragment")
            transaction.commit()
        }

        // Recover password onClickListener
        binding.recoverPasswordButton.setOnClickListener {
            transaction.replace(R.id.fragmentContainerView, RecoverPasswordFragment())
            transaction.addToBackStack("RecoverPasswordFragment")
            transaction.commit()
        }

        // Login button onClickListener
        binding.loginButton.setOnClickListener {
            val emailInput = binding.emailInput.text
            val passwordInput = binding.passwordInput.text

            binding.icon5.visibility = if (emailInput.isNotEmpty()) View.INVISIBLE else View.VISIBLE
            binding.icon6.visibility = if (passwordInput.isNotEmpty()) View.INVISIBLE else View.VISIBLE
            binding.errore.visibility = if (emailInput.isEmpty() || passwordInput.isEmpty()) View.VISIBLE else View.INVISIBLE

            if (emailInput.isNotEmpty() && passwordInput.isNotEmpty()) {
                val loginData = LoginData(emailInput.toString(), passwordInput.toString())
                DBQueriesService().login(loginData) { result ->
                    if (result != null) {
                        sp = requireActivity().getSharedPreferences(getString(R.string.er_preferences_file), Context.MODE_PRIVATE)
                        sp.edit().putString("id", result).apply()

                        val intent = Intent(context, ExploreActivity::class.java)
                        intent.putExtra("Id", result)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    } else {
                        binding.errore.visibility = View.VISIBLE
                    }
                }
            }
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // Video playback
        val packageName = requireContext().packageName
        binding.videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.clip))
        binding.videoView.setOnPreparedListener { it.isLooping = true }
        binding.videoView.start()
    }
}