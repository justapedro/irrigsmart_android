package br.com.irrigsmart.manager.view.formprincipal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import br.com.irrigsmart.manager.R
import br.com.irrigsmart.manager.databinding.ActivityFormPrincipalBinding
import br.com.irrigsmart.manager.view.conta.FormConta
import br.com.irrigsmart.manager.view.formlogin.FormLogin
import br.com.irrigsmart.manager.view.planta.FormPlanta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

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
            val photoUrl = it.photoUrl

            if (name != null) {
                //binding.textodados.text = getString(R.string.bem_vindo).plus(name.plus("!"))
            }
            else {
                //binding.textodados.text = getString(R.string.bem_vindo).plus(getString(R.string.usuario).plus("!"))
            }

            if (photoUrl != null) {
                binding.imageViewConta.setImageURI(photoUrl)
            }
            else {
                binding.imageViewConta.setImageResource(R.drawable.account_circle)
            }
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
                    binding.textViewUmidade.text = documento.getLong("umidade").toString().plus("%")
                    binding.imageviewUmidade.setImageDrawable(if (documento.getLong("umidade")!! <= 30) ResourcesCompat.getDrawable(resources, R.drawable.humidity_low_48px, null) else if (documento.getLong("umidade")!! >= 60) ResourcesCompat.getDrawable(resources, R.drawable.humidity_high_48px, null) else ResourcesCompat.getDrawable(resources, R.drawable.humidity_mid_48px, null))
                    binding.textViewReservatorio.text = if (documento.getLong("reservatorio")!!.toInt() == 1) getString(R.string.reservatorio_cheio) else getString(R.string.reservatorio_vazio)
                    binding.imageViewReservatorio.setImageDrawable(if (documento.getLong("reservatorio")!!.toInt() == 1) ResourcesCompat.getDrawable(resources, R.drawable.water_full, null) else ResourcesCompat.getDrawable(resources, R.drawable.water_loss, null))

                    val timestamp = documento.getTimestamp("dataleitura")
                    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                    binding.textViewData.text = "Atualizado em ".plus(formatter.format(timestamp?.toDate()))
                }
            }
        }

        //Abrir página de conta
        binding.imageViewConta.setOnClickListener() {
            val intent = Intent(this, FormConta::class.java)
            startActivity(intent)
        }

        //Abrir página de planta
        binding.linear14.setOnClickListener() {
            val intent = Intent(this, FormPlanta::class.java)
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