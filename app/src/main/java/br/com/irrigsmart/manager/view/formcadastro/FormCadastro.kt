package br.com.irrigsmart.manager.view.formcadastro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.irrigsmart.manager.R
import br.com.irrigsmart.manager.databinding.ActivityFormCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btncadastrar.setOnClickListener {view ->

            val email = binding.editEmail.text.toString()
            val senha = binding.editEmail.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                val snackbar = Snackbar.make(view,"Preencha todos os campos", Snackbar.LENGTH_LONG)
                snackbar.show()
            } else {
                auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener {cadastro ->
                    if (cadastro.isSuccessful) {
                        val snackbar = Snackbar.make(
                            view,
                            "Cadastro realizado com sucesso",
                            Snackbar.LENGTH_LONG
                        )
                        snackbar.show()
                        binding.editEmail.setText("")
                        binding.editSenha.setText("")
                    }
                }.addOnFailureListener() {erro ->
                    val mensagemErro = when(erro){
                        is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caracteres."
                        is FirebaseAuthInvalidCredentialsException -> "Digite um e-mail válido."
                        is FirebaseAuthUserCollisionException -> "Uma conta já está utilizando este e-mail."
                        is FirebaseNetworkException -> "Sem conexão com a internet."
                        else -> "Ocorreu um erro ao cadastrar: ".plus(erro)
                    }
                    val snackbar = Snackbar.make(view, mensagemErro, Snackbar.LENGTH_LONG)
                    snackbar.show()
                }
            }

        }
    }
}