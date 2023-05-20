package br.com.irrigsmart.manager.view.formprincipal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.irrigsmart.manager.R
import br.com.irrigsmart.manager.databinding.ActivityFormPrincipalBinding
import br.com.irrigsmart.manager.view.formlogin.FormLogin
import com.google.firebase.auth.FirebaseAuth

class FormPrincipal : AppCompatActivity() {

    private lateinit var binding: ActivityFormPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoDeslogar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, FormLogin::class.java)
            startActivity(intent)
            finish()
        }
    }

}