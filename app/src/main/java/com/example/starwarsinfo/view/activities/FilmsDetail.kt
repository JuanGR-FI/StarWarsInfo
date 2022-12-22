package com.example.starwarsinfo.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarsinfo.databinding.ActivityFilmsDetailBinding
import com.example.starwarsinfo.model.CharacterDetail
import com.example.starwarsinfo.model.CharactersApi
import com.example.starwarsinfo.model.Film
import com.example.starwarsinfo.model.FilmDetail
import com.example.starwarsinfo.util.Constants
import com.example.starwarsinfo.view.adapters.Adapter
import com.example.starwarsinfo.view.adapters.AdapterFilm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmsDetail : AppCompatActivity() {
    private lateinit var binding: ActivityFilmsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        val filmsURLs = bundle?.getStringArrayList("filmsurls")

        for(film in filmsURLs!!)
            Toast.makeText(this@FilmsDetail, film.substring(28,  film.length-1), Toast.LENGTH_SHORT).show()

        var films =  arrayListOf<Film>()

        val call = Constants.getRetrofit().create(CharactersApi::class.java).getFilmDetail("films/")
        CoroutineScope(Dispatchers.IO).launch {
            call.enqueue(object: Callback<FilmDetail>{
                override fun onResponse(call: Call<FilmDetail>, response: Response<FilmDetail>) {
                    Log.d(Constants.LOGTAG, "Respuesta del servidor: ${response.toString()}")
                    Log.d(Constants.LOGTAG, "Datos: ${response.body().toString()}")
                    //films.add(response.body()!!)
                    films = characterFilms(filmsURLs, response.body()!!.results)

                    binding.rvMenu.layoutManager = LinearLayoutManager(this@FilmsDetail)
                    binding.rvMenu.adapter = AdapterFilm(this@FilmsDetail, films)

                    binding.pbConexion.visibility = View.GONE
                    binding.ivError.visibility = View.INVISIBLE
                }

                override fun onFailure(call: Call<FilmDetail>, t: Throwable) {
                    Toast.makeText(this@FilmsDetail, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                    binding.pbConexion.visibility = View.GONE
                    binding.ivError.visibility = View.VISIBLE
                }

            })

        }

    }

    fun characterFilms(filmsURLS: ArrayList<String>, films: ArrayList<Film>): ArrayList<Film>{
        val res: ArrayList<Film> = arrayListOf()
        for(url in filmsURLS){
            val id = url.substring(28,  url.length-1)
            when(id.toInt()){
                1 -> {
                        res.add(films[0])
                }
                2 -> {
                    res.add(films[1])
                }
                3 -> {
                    res.add(films[2])
                }
                4 -> {
                    res.add(films[3])
                }
                5 -> {
                    res.add(films[4])
                }
                6 -> {
                    res.add(films[5])
                }
            }
        }
        return res
    }

}