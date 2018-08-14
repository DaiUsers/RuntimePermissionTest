package com.yt.runtimepermissiontest

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.ArrayMap
import android.widget.Button
import android.widget.Toast
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val callbutton = findViewById<Button>(R.id.callbutton)
        callbutton.setOnClickListener({
            if (ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.CALL_PHONE), 1)
            } else {
                callPhone()
            }
        })

        val contactButton = findViewById<Button>(R.id.contactbutton)
        contactButton.setOnClickListener({
            Contact.actionStart(this@MainActivity)
        })
    }

    private fun callPhone() {
        try {
            var intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone()
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
