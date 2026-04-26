package com.example.ford_catalogue

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class FordCarAdapter(
    private val context: Context,
    private val cars: List<FordCar>
) : BaseAdapter() {

    private val favoriteCars = mutableSetOf<String>()

    override fun getCount(): Int {
        return cars.size
    }

    override fun getItem(position: Int): FordCar {
        return cars[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_car_card, parent, false)

            holder = ViewHolder(
                tvCarName = view.findViewById(R.id.tvCardCarName),
                tvMeta = view.findViewById(R.id.tvCardMeta),
                tvTagline = view.findViewById(R.id.tvCardTagline),
                tvPower = view.findViewById(R.id.tvCardPower),
                tvFuel = view.findViewById(R.id.tvCardFuel),
                tvSeats = view.findViewById(R.id.tvCardSeats),
                tvCategory = view.findViewById(R.id.tvCardCategory),
                tvPrice = view.findViewById(R.id.tvCardPrice),
                tvFavorite = view.findViewById(R.id.tvFavorite)
            )

            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val car = cars[position]

        holder.tvCarName.text = car.name
        holder.tvMeta.text = "${car.category} • ${car.engine}"
        holder.tvTagline.text = car.tagline
        holder.tvPower.text = car.power
        holder.tvFuel.text = car.fuel
        holder.tvSeats.text = car.seats
        holder.tvCategory.text = car.category.uppercase()
        holder.tvPrice.text = car.price

        val isFavorite = favoriteCars.contains(car.name)

        if (isFavorite) {
            holder.tvFavorite.text = "♥"
            holder.tvFavorite.setTextColor(
                ContextCompat.getColor(context, R.color.ford_red)
            )
        } else {
            holder.tvFavorite.text = "♡"
            holder.tvFavorite.setTextColor(
                ContextCompat.getColor(context, R.color.ford_text_soft)
            )
        }

        holder.tvFavorite.setOnClickListener {
            if (favoriteCars.contains(car.name)) {
                favoriteCars.remove(car.name)
                Toast.makeText(context, "${car.name} dihapus dari favorit", Toast.LENGTH_SHORT).show()
            } else {
                favoriteCars.add(car.name)
                Toast.makeText(context, "${car.name} masuk ke garage favorit", Toast.LENGTH_SHORT).show()
            }

            notifyDataSetChanged()
        }

        return view
    }

    private data class ViewHolder(
        val tvCarName: TextView,
        val tvMeta: TextView,
        val tvTagline: TextView,
        val tvPower: TextView,
        val tvFuel: TextView,
        val tvSeats: TextView,
        val tvCategory: TextView,
        val tvPrice: TextView,
        val tvFavorite: TextView
    )
}