package br.com.irrigsmart.manager.view.formprincipal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import br.com.irrigsmart.manager.R
import br.com.irrigsmart.manager.databinding.ActivityFormPrincipalBinding
import br.com.irrigsmart.manager.view.conta.FormConta
import br.com.irrigsmart.manager.view.formlogin.FormLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FormPrincipal : AppCompatActivity() {

    private lateinit var binding: ActivityFormPrincipalBinding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl
            val emailVerified = it.isEmailVerified
            val uid = it.uid
            binding.textodados.text = getString(R.string.bem_vindo).plus(name.plus("!"))

        }

        // Obter dados do banco de dados
        db.collection("0").document(FirebaseAuth.getInstance().uid.toString()).addSnapshotListener { documento, error ->
            if (error != null) {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.erro))
                    .setMessage(getString(R.string.erro_ao_ler_dados).plus("\n\n".plus(error)))
                    .setNeutralButton("OK") { _, _ ->
                        finish()
                    }
                    .show()
            } else {
                if (documento != null && documento.exists()) {
                    binding.progressBarUmidade.progress = documento.getLong("umidade")!!.toInt()
                    binding.textViewProgressUmidade.text = documento.getLong("umidade").toString().plus("%")
                    val cheio = documento.getLong("reservatorio")!!.toInt() > 300
                    binding.progressBarReservatorio.progress = if (cheio) 100 else 10
                    binding.textViewProgressReservatorio.text = if (cheio) getString(R.string.reservatorio_cheio) else getString(R.string.reservatorio_vazio)
                }
            }
        }

        binding.imageaccount.setOnClickListener() {
            val intent = Intent(this, FormConta::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser == null) {
            val intent = Intent(this, FormLogin::class.java)
            startActivity(intent)
            finish()
        }
    }

}