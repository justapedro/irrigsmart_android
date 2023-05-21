package br.com.irrigsmart.manager.view.formlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.irrigsmart.manager.R
import br.com.irrigsmart.manager.databinding.ActivityFormLoginBinding
import br.com.irrigsmart.manager.view.formcadastro.FormCadastro
import br.com.irrigsmart.manager.view.formprincipal.FormPrincipal
import br.com.irrigsmart.manager.view.formrecuperarsenha.FormRecuperar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding
    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnentrar.setOnClickListener {view ->

            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                val snackbar = Snackbar.make(view, "Preencha todos os campos", Snackbar.LENGTH_LONG)
                snackbar.show()
            } else {
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener {login ->
                    if (login.isSuccessful) {
                        navegarTelaPrincipal()
                    }
                }.addOnFailureListener {erro ->
                    val mensagemErro = when(erro){
                        is FirebaseAuthInvalidCredentialsException -> "E-mail ou senha incorretos."
                        else -> "Ocorreu um erro ao cadastrar: ".plus(erro)
                    }
                    val snackbar = Snackbar.make(view, mensagemErro, Snackbar.LENGTH_LONG)
                    snackbar.show()
                }
            }
        }

        binding.btncadastrar.setOnClickListener {
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }

        binding.txtrecuperarsenha.setOnClickListener {
            val intent = Intent(this, FormRecuperar::class.java)
            startActivity(intent)
        }
    }

    private fun navegarTelaPrincipal(){
        val intent = Intent(this, FormPrincipal::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        auth.currentUser?.let {
            navegarTelaPrincipal()
        }
    }
}