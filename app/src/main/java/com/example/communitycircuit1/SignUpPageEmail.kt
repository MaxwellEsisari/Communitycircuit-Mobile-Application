    package com.example.communitycircuit1

    import android.os.Bundle
    import android.widget.Toast
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import com.example.communitycircuit1.databinding.ActivitySignUpPageEmailBinding
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.auth.FirebaseUser

    class SignUpPageEmail : AppCompatActivity() {
        private lateinit var binding: ActivitySignUpPageEmailBinding
        private lateinit var auth: FirebaseAuth
        override fun onCreate(savedInstanceState: Bundle?) {
            binding = ActivitySignUpPageEmailBinding.inflate(layoutInflater)
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(binding.root)
            auth = FirebaseAuth.getInstance()

            binding.loginBtn.setOnClickListener {
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                val confirmPassword = binding.etConfirmPassword.text.toString().trim()

                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                } else if (password != confirmPassword) {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                } else {
                    createUserWithEmailAndPassword(email, password)
                }
            }
        }

        private fun createUserWithEmailAndPassword(email: String, password: String) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user: FirebaseUser? = auth.currentUser
                        Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
                        // You can navigate to another activity here if needed
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext, "Sign up failed. ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }