package br.com.irrigsmart.manager.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.irrigsmart.manager.R
import br.com.irrigsmart.manager.databinding.PlantaItemBinding
import br.com.irrigsmart.manager.view.model.Planta

class AdapterPlantas(private val context: Context, private val listaPlantas: MutableList<Planta>):
    RecyclerView.Adapter<AdapterPlantas.PlantaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantaViewHolder {
        val itemLista = PlantaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlantaViewHolder(itemLista)
    }

    override fun onBindViewHolder(holder: PlantaViewHolder, position: Int) {
        val planta = listaPlantas[position]
        holder.txNome.text = planta.nome
    }

    override fun getItemCount(): Int {
        return listaPlantas.size
    }

    inner class PlantaViewHolder(binding: PlantaItemBinding): RecyclerView.ViewHolder(binding.root) {
        val txNome = binding.tvNomePlanta
    }

}