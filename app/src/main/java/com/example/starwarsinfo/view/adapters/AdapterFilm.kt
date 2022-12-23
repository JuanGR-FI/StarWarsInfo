package com.example.starwarsinfo.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.starwarsinfo.R
import com.example.starwarsinfo.databinding.CharacterElementBinding
import com.example.starwarsinfo.databinding.FilmElementBinding
import com.example.starwarsinfo.model.CharacterDetail
import com.example.starwarsinfo.model.Film
import com.example.starwarsinfo.model.FilmDetail

class AdapterFilm(private val contexto: Context, private val films: ArrayList<Film>): RecyclerView.Adapter<AdapterFilm.ViewHolder>() {
    class ViewHolder(view: FilmElementBinding): RecyclerView.ViewHolder(view.root) {
        val tvTitle = view.tvTitle
        val tvDirector = view.tvDirector
        val tvProducer = view.tvProducer
        val tvDate = view.tvDate
        val ivThumbnail = view.ivThumbnail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFilm.ViewHolder {
        val binding = FilmElementBinding.inflate(LayoutInflater.from(contexto))
        return AdapterFilm.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterFilm.ViewHolder, position: Int) {
        holder.tvTitle.text = films[position].title
        holder.tvDirector.text = contexto.getString(R.string.director, films[position].director)

        holder.tvProducer.text = contexto.getString(R.string.producer, films[position].producer)
        holder.tvDate.text = contexto.getString(R.string.release_date, films[position].release_date)

        var imageURL = ""
        when(films[position].title){
            "A New Hope" -> {
                imageURL = "https://aulavirtual.amaurypm.com/cm2023-1/sw1.jpg"
            }
            "The Empire Strikes Back" -> {
                imageURL = "https://aulavirtual.amaurypm.com/cm2023-1/sw2.jpg"
            }
            "Return of the Jedi" -> {
                imageURL = "https://aulavirtual.amaurypm.com/cm2023-1/sw3.jpg"
            }
            "The Phantom Menace" -> {
                imageURL = "https://aulavirtual.amaurypm.com/cm2023-1/sw4.jpg"
            }
            "Attack of the Clones" -> {
                imageURL = "https://aulavirtual.amaurypm.com/cm2023-1/sw5.jpg"
            }
            "Revenge of the Sith" -> {
                imageURL = "https://aulavirtual.amaurypm.com/cm2023-1/sw6.jpg"
            }
            else -> {
                imageURL = ""
            }
        }

        Glide.with(contexto)
            .load(imageURL)
            .into(holder.ivThumbnail)

    }

    override fun getItemCount(): Int = films.size
}