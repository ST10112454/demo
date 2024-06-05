package com.example.appclockin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appclockin.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        firebaseRef= FirebaseDatabase.getInstance().getReference("test")
        binding.tvSendData.setOnClickListener {
            firebaseRef.setValue("Work Please ")
                .addOnCompleteListener {
                    Toast.makeText(this, "data is stored successfully", Toast.LENGTH_LONG).show()
                }
        }

        //defult
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val signUpTextView = findViewById<TextView>(R.id.signUpTextView)

        // Set an OnClickListener on the TextView
        signUpTextView.setOnClickListener {
            // Start the SignUp activity
            val intent = Intent(this, Reg::class.java)
            startActivity(intent)
        }
    }
}