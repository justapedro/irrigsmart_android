package br.com.irrigsmart.manager.view.conta

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.irrigsmart.manager.databinding.ActivityFormContaBinding
import br.com.irrigsmart.manager.view.formlogin.FormLogin
import com.google.firebase.auth.FirebaseAuth

class FormConta : AppCompatActivity() {

    private lateinit var binding: ActivityFormContaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormContaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarapp.setNavigationOnClickListener {
            finish()
        }

        binding.btndeslogar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, FormLogin::class.java)
            startActivity(intent)
            finish()
        }

    }
}