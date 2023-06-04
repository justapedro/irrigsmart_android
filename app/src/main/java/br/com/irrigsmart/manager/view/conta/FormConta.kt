package br.com.irrigsmart.manager.view.conta

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.irrigsmart.manager.R
import br.com.irrigsmart.manager.databinding.ActivityFormContaBinding
import br.com.irrigsmart.manager.view.formlogin.FormLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FormConta : AppCompatActivity() {

    private lateinit var binding: ActivityFormContaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormContaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // Check if user's email is verified
            val emailVerified = it.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = it.uid

            if (name != null) {
                binding.textViewNome.text = name
            }
            else {
                binding.textViewNome.text = getString(R.string.usuario)
            }

            if (photoUrl != null) {
                binding.imageViewPerfil.setImageURI(photoUrl)
            }
            else {
                binding.imageViewPerfil.setImageResource(R.drawable.account_circle)
            }
            binding.textViewEmail.text = email
        }



        binding.toolbarapp.setNavigationOnClickListener {
            finish()
        }

        binding.linearAlterarSenha.setOnClickListener {

        }
        binding.linearSair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, FormLogin::class.java)
            startActivity(intent)
            finish()
        }

    }
}