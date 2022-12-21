package com.example.starwarsinfo.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.starwarsinfo.databinding.ActivityPlanetDetailsBinding
import com.example.starwarsinfo.model.CharactersApi
import com.example.starwarsinfo.model.PlanetDetail
import com.example.starwarsinfo.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlanetDetails : AppCompatActivity() {
    private lateinit var binding: ActivityPlanetDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        val planetID = bundle?.getString("id","")

        val call = Constants.getRetrofit().create(CharactersApi::class.java).getPlanetDetail(planetID)

        call.enqueue(object: Callback<PlanetDetail>{
            override fun onResponse(call: Call<PlanetDetail>, response: Response<PlanetDetail>) {

            }

            override fun onFailure(call: Call<PlanetDetail>, t: Throwable) {

            }

        })
    }
}