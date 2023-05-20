package br.com.irrigsmart.manager.view.formprincipal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.irrigsmart.manager.R
import br.com.irrigsmart.manager.databinding.ActivityFormPrincipalBinding
import br.com.irrigsmart.manager.view.formlogin.FormLogin
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FormPrincipal : AppCompatActivity() {

    private lateinit var binding: ActivityFormPrincipalBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db.collection("0").document(FirebaseAuth.getInstance().uid.toString()).addSnapshotListener() { documento, error ->
            if (error != null) {
                binding.textodados.text = "Erro ao ler dados!".plus(error)
            } else {
                if (documento != null && documento.exists()) {
                    binding.textodados.text = "Logado como ".plus(documento.getString("nome").plus("!"))
                    binding.textoumidade.text = "Umidade: ".plus(documento.getLong("umidade").toString().plus("%"))
                    binding.textonivel.text = "Reservatório: ".plus(documento.getLong("reservatorio").toString())
                }
            }
        }

        binding.botaoDeslogar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, FormLogin::class.java)
            startActivity(intent)
            finish()
        }
    }

}