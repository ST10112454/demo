import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appclockin.DisplayUserActivity
import com.example.appclockin.databinding.ActivityRegBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Reg : AppCompatActivity() {
    private lateinit var binding: ActivityRegBinding
    private lateinit var firebaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase
        firebaseRef = FirebaseDatabase.getInstance().getReference("users")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonSignUp.setOnClickListener {
            val userName = binding.UserName.text.toString().trim()
            val email = binding.Email.text.toString().trim()
            val password = binding.Password.text.toString().trim()
            val confirmPassword = binding.PasswordConfirm.text.toString().trim()

            // Validate input
            if (userName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Set user data to display on the register page
            binding.UserName.setText(userName)
            binding.Email.setText(email)

            val user = User(userName, email, password)
            firebaseRef.child(userName).setValue(user)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "User registered successfully", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, DisplayUserActivity::class.java))
                    } else {
                        Toast.makeText(this, "Failed to register user", Toast.LENGTH_LONG).show()
                    }
                }
        }

        binding.buttonViewUsers.setOnClickListener {
            startActivity(Intent(this, DisplayUserActivity::class.java))
        }
    }

    data class User(val userName: String, val email: String, val password: String)
}
