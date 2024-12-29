package com.novaengine.easyroutes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.novaengine.easyroutes.databinding.ActivityMainBinding
import com.novaengine.easyroutes.fragments.LoginFragment
import com.novaengine.easyroutes.serversupport.DBQueriesService
import com.novaengine.easyroutes.services.GetNotificationsService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fm: FragmentManager
    private lateinit var sp: SharedPreferences

    // Activity name as log tag
    private val logTag = MainActivity::class.java.simpleName

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            Log.i(logTag, "Permission granted!")
            startNotificationsService()
        } else {
            Log.i(logTag, "Permission denied!")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkNotificationPermission()
    }

    override fun onResume() {
        super.onResume()
        sp = getSharedPreferences(getString(R.string.er_preferences_file), Context.MODE_PRIVATE)
        val userId = sp.getString("id", null)

        DBQueriesService().getServerConnectionStatus() { okay ->
            if(okay) {
                if (userId != null) {
                    val i = Intent(this, ExploreActivity::class.java)
                    startActivity(i)
                } else {
                    fm = supportFragmentManager
                    if (fm.findFragmentById(R.id.fragmentContainerView) == null) {
                        val transaction = fm.beginTransaction()
                        transaction.add(R.id.fragmentContainerView, LoginFragment())
                        transaction.commitNow()
                    }
                }
            }
            else {
                Toast.makeText(this, "Easy Routes server is not reachable at this time.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun checkNotificationPermission() {
        val permission = android.Manifest.permission.POST_NOTIFICATIONS
        val hasPermission = checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        if (hasPermission) {
            startNotificationsService()
        } else {
            if (shouldShowRequestPermissionRationale(permission)) {
                requestPermission.launch(permission)
            } else {
                requestPermission.launch(permission)
            }
        }
    }

    private fun startNotificationsService() {
        val intent = Intent(this, GetNotificationsService::class.java)
        startService(intent)
    }
}
