package com.example.starwarsinfo.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.starwarsinfo.databinding.ActivityPlanetDetailsBinding
import com.example.starwarsinfo.model.CharactersApi
import com.example.starwarsinfo.model.PlanetDetail
import com.example.starwarsinfo.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

        CoroutineScope(Dispatchers.IO).launch{
            call.enqueue(object: Callback<PlanetDetail>{
                override fun onResponse(call: Call<PlanetDetail>, response: Response<PlanetDetail>) {

                    Log.d(Constants.LOGTAG, "Respuesta del servidor: ${response.toString()}")
                    Log.d(Constants.LOGTAG, "Datos: ${response.body().toString()}")

                    binding.pbConexion.visibility = View.GONE
                    binding.ivError.visibility = View.INVISIBLE
                    var urlImage = ""
                    if(response.body()?.name.equals("Tatooine")){
                        urlImage = "https://i.pinimg.com/564x/f1/04/7a/f1047a83404ee17270ab87c46801fcc6.jpg"
                    }else if(response.body()?.name.equals("Naboo")){
                        urlImage = "https://i.pinimg.com/564x/9f/25/5c/9f255c0efbda5b6a050906d876466086.jpg"
                    }else if(response.body()?.name.equals("Alderaan")){
                        urlImage = "https://i.pinimg.com/564x/c4/aa/54/c4aa54496e4acfb3b2f90176de852cc9.jpg"
                    }else if(response.body()?.name.equals("Stewjon")){
                        urlImage = "https://qph.cf2.quoracdn.net/main-qimg-473458d55db89d8fb2703e09a7ff711e-lq"
                    }

                    with(binding){
                        tvName.text = response.body()?.name
                        tvRotationPeriod.text = "Rotation Period: " + response.body()?.rotation_period
                        tvOrbitalPeriod.text = "Orbital Period: " + response.body()?.orbital_period
                        tvDiameter.text = "Diameter: " + response.body()?.diameter
                        tvClimate.text = "Climate: " + response.body()?.climate
                        tvTerrain.text = "Terrain: " + response.body()?.terrain
                        tvPopulation.text = "Population: " + response.body()?.population
                        btnBack.visibility = View.VISIBLE
                        Glide.with(this@PlanetDetails)
                            .load(urlImage)
                            .into(ivImage)
                    }



                }

                override fun onFailure(call: Call<PlanetDetail>, t: Throwable) {
                    binding.pbConexion.visibility = View.GONE
                    binding.ivError.visibility = View.VISIBLE
                    Toast.makeText(this@PlanetDetails, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()

                }

            })
        }


    }
}