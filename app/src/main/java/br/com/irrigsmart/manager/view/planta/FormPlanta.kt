package br.com.irrigsmart.manager.view.planta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import br.com.irrigsmart.manager.R
import br.com.irrigsmart.manager.databinding.ActivityFormPlantaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat

class FormPlanta : AppCompatActivity() {

    private lateinit var binding: ActivityFormPlantaBinding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormPlantaBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                    //binding.progressBarUmidade.progress = documento.getLong("umidade")!!.toInt()
                    binding.textViewPlanta.text = documento.getString("nomePlanta")
                    binding.textViewTipoPlanta.text = documento.getString("tipoPlanta")
                    binding.textViewProgressUmidade.text = documento.getLong("umidade").toString().plus("%")
                    binding.progressBarUmidade.progress = documento.getLong("umidade")!!.toInt()
                    val dataleitura = documento.getTimestamp("dataleitura")
                    val datairrigado = documento.getTimestamp("datairrigado")
                    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                    binding.textViewDataRefresh.text = "Última leitura: ".plus(dataleitura?.toDate()
                        ?.let { formatter.format(it) })
                    binding.textViewDataIrrig.text = "Última irrigação: ".plus(datairrigado?.toDate()
                        ?.let { formatter.format(it) })
                    val cheio = documento.getLong("reservatorio")!!.toInt() == 1
                    if (cheio) {
                        binding.textViewProgressReservatorio.text = getString(R.string.reservatorio_cheio)
                        binding.imageViewReservatorio.setImageResource(R.drawable.water_full)
                        binding.textViewProgressReservatorio.setTextColor(ResourcesCompat.getColor(
                            resources, R.color.white, null));
                        binding.imageViewReservatorio.setColorFilter(ResourcesCompat.getColor(
                            resources, R.color.water_blue, null));
                    }
                    else {
                        binding.textViewProgressReservatorio.text = getString(R.string.reservatorio_vazio)
                        binding.imageViewReservatorio.setImageResource(R.drawable.water_loss)
                        binding.textViewProgressReservatorio.setTextColor(ResourcesCompat.getColor(
                            resources, R.color.red_700, null));
                        binding.imageViewReservatorio.setColorFilter(ResourcesCompat.getColor(
                            resources, R.color.red_700, null));
                    }
                }
            }
        }

        binding.toolbarapp.setNavigationOnClickListener {
            finish()
        }
    }
}