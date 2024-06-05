package com.example.appclockin

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.*

class DisplayUserActivity : AppCompatActivity() {

    private lateinit var userDataTextView: TextView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_user)

        userDataTextView = findViewById(R.id.userDataTextView)

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("users")

        // Fetch user data from Firebase
        fetchUserData()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun fetchUserData() {
        val zuwaReference = databaseReference.child("zuwa")

        zuwaReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val stringBuilder = StringBuilder()
                val user = snapshot.getValue(Reg.User::class.java)
                if (user != null) {
                    stringBuilder.append("User Name: ${user.userName}\n")
                    stringBuilder.append("Email: ${user.email}\n")
                    stringBuilder.append("Password: ${user.password}\n\n")
                }
                userDataTextView.text = stringBuilder.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
                userDataTextView.text = "Failed to load data: ${error.message}"
            }
        })
    }

}
