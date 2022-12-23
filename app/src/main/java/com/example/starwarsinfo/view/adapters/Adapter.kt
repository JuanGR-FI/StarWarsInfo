package com.example.starwarsinfo.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.starwarsinfo.R
import com.example.starwarsinfo.databinding.CharacterElementBinding
import com.example.starwarsinfo.model.CharacterDetail
import com.example.starwarsinfo.view.activities.MainActivity

class Adapter(private val contexto: Context, private val characters: CharacterDetail): RecyclerView.Adapter<Adapter.ViewHolder>(){
    class ViewHolder(view: CharacterElementBinding): RecyclerView.ViewHolder(view.root) {
        val tvName = view.tvName
        val tvHeight = view.tvHeight
        val tvBirth = view.tvBirth
        val tvGender = view.tvGender
        val ivThumbnail = view.ivThumbnail
        val btnPlanet = view.btnPlanet
        val btnFilms = view.btnFilms
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CharacterElementBinding.inflate(LayoutInflater.from(contexto))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = characters.results[position].name
        holder.tvHeight.text = contexto.getString(R.string.gender, characters.results[position].height)

        holder.tvBirth.text = contexto.getString(R.string.birthyear, characters.results[position].birth_year)

        holder.tvGender.text = contexto.getString(R.string.gender, characters.results[position].gender)

        val tmpThumbnail: String
        when(characters.results[position].name){
            "Luke Skywalker" -> {
                tmpThumbnail = "https://i.pinimg.com/564x/f5/7e/db/f57edba0746f7db9567f0a50ba3427b6.jpg"
            }
            "C-3PO" -> {
                tmpThumbnail = "https://i.pinimg.com/564x/05/28/5e/05285e3bc90577c1c62909969df64b43.jpg"
            }
            "R2-D2" -> {
                tmpThumbnail = "https://i.pinimg.com/564x/d9/3c/98/d93c9804175a7b019be7d55a1682abf4.jpg"
            }
            "Darth Vader" -> {
                tmpThumbnail = "https://i.pinimg.com/564x/7a/13/81/7a138106114d5a32f1d4586400a55338.jpg"
            }
            "Leia Organa" -> {
                tmpThumbnail = "https://i.pinimg.com/564x/55/99/74/559974b8820f92be50d6abbefbaf06d5.jpg"
            }
            "Owen Lars" -> {
                tmpThumbnail = "https://i.pinimg.com/564x/79/b5/c5/79b5c50703fb4b055e431c7c76173ff7.jpg"
            }
            "Beru Whitesun lars" -> {
                tmpThumbnail = "https://i.pinimg.com/564x/f5/b8/ca/f5b8ca268a10a6d32c8e27631f69800f.jpg"
            }
            "R5-D4" -> {
                tmpThumbnail = "https://i.pinimg.com/564x/76/a5/29/76a529a13e35b641aa5b39dffa145702.jpg"
            }
            "Biggs Darklighter" -> {
                tmpThumbnail = "https://i.pinimg.com/564x/0c/09/5e/0c095eb155aa2eabe8cd89682860d482.jpg"
            }
            "Obi-Wan Kenobi" -> {
                tmpThumbnail = "https://i.pinimg.com/564x/40/e3/e1/40e3e14ae4d08e81d7e1ef3feeca9ca2.jpg"
            }
            else -> {
                tmpThumbnail = ""
            }

        }

        Glide.with(contexto)
            .load(tmpThumbnail)
            .into(holder.ivThumbnail)

        holder.btnPlanet.setOnClickListener {
            if(contexto is MainActivity) contexto.selectedPlanet(characters.results[position])

        }

        holder.btnFilms.setOnClickListener {
            if(contexto is MainActivity) contexto.selectedFilms(characters.results[position])
        }

    }

    override fun getItemCount(): Int = characters.results.size
}