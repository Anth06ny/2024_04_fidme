package com.amonteiro.a2024_04_fidme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amonteiro.a2024_04_fidme.databinding.RowStudentBinding
import com.amonteiro.a2024_04_fidme.model.CarBean

class CarListAdapter : ListAdapter<CarBean, CarListAdapter.ViewHolder>(Comparator()) {

    //1 Class référençant les composants graphiques
    class ViewHolder(val bind: RowStudentBinding) : RecyclerView.ViewHolder(bind.root)

    //2 Permet de comparer des listes pour les animations
    class Comparator : DiffUtil.ItemCallback<CarBean>() {
        override fun areItemsTheSame(oldItem: CarBean, newItem: CarBean)
                = oldItem === newItem

        override fun areContentsTheSame(oldItem: CarBean, newItem: CarBean)
                = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ViewHolder(RowStudentBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val car = getItem(position)
        holder.bind.tvville.text = car.model
        holder.bind.tvtemp.text = car.marque

        holder.bind.main.setOnClickListener {
            println(car)
        }
    }

}