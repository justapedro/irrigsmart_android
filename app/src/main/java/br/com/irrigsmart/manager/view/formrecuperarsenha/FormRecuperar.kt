package br.com.irrigsmart.manager.view.formrecuperarsenha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import br.com.irrigsmart.manager.R
import br.com.irrigsmart.manager.databinding.ActivityFormRecuperarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class FormRecuperar : AppCompatActivity() {

    private lateinit var binding: ActivityFormRecuperarBinding
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormRecuperarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnrecuperar.setOnClickListener() {
            if (binding.editEmail.text.toString().isEmpty()) {
                binding.editEmail.error = "Preencha o campo!"
                binding.editEmail.requestFocus()
            } else {
                auth.sendPasswordResetEmail(binding.editEmail.text.toString())
                    .addOnCompleteListener { task ->
                        val dialogo = AlertDialog.Builder(this)
                        if (task.isSuccessful) {
                            dialogo.setTitle("Recuperação de senha")
                            dialogo.setMessage("E-mail enviado com sucesso!")
                            dialogo.setNeutralButton("OK") { dialog, which ->
                                finish()
                            }
                            dialogo.show()
                        } else {
                            dialogo.setTitle("Recuperação de senha")
                            dialogo.setMessage("Ocorreu um erro ao enviar o e-mail!")
                            dialogo.setNeutralButton("OK") { dialog, which ->
                                binding.editEmail.text = null
                                binding.editEmail.requestFocus()
                            }
                        }
                    }.addOnFailureListener { exception ->
                        val mensagemErro = when (exception) {
                            is FirebaseAuthInvalidCredentialsException -> "E-mail ou senha incorretos."
                            else -> "Ocorreu um erro ao enviar o e-mail:\n\n ".plus(exception)
                        }
                        val dialogo = AlertDialog.Builder(this)
                        dialogo.setTitle("Recuperação de senha")
                        dialogo.setMessage(mensagemErro)
                        dialogo.setNeutralButton("OK") { dialog, which ->
                            binding.editEmail.text = null
                            binding.editEmail.requestFocus()
                        }
                    }
            }
        }
    }
}