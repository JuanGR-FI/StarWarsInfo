package com.example.starwarsinfo.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.bumptech.glide.Glide
import com.example.starwarsinfo.R
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
    private lateinit var planetID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        planetID = bundle?.getString("id","").toString()

        tryConnection()
    }

    fun reloadConnection(view: View) {
        binding.ivError.visibility = View.GONE
        view.visibility = View.GONE

        binding.pbConexion.visibility = View.VISIBLE

        tryConnection()
    }

    private fun tryConnection() {
        val call = Constants.getRetrofit().create(CharactersApi::class.java).getPlanetDetail(planetID)

        CoroutineScope(Dispatchers.IO).launch{
            call.enqueue(object: Callback<PlanetDetail>{
                override fun onResponse(call: Call<PlanetDetail>, response: Response<PlanetDetail>) {

                    Log.d(Constants.LOGTAG, "Respuesta del servidor: ${response.toString()}")
                    Log.d(Constants.LOGTAG, "Datos: ${response.body().toString()}")

                    binding.pbConexion.visibility = View.GONE
                    binding.ivError.visibility = View.INVISIBLE
                    binding.btnReload.visibility = View.INVISIBLE

                    val urlImage: String
                    when(response.body()?.name){
                        "Tatooine" -> {
                            urlImage = "https://i.pinimg.com/564x/f1/04/7a/f1047a83404ee17270ab87c46801fcc6.jpg"
                        }
                        "Naboo" -> {
                            urlImage = "https://i.pinimg.com/564x/9f/25/5c/9f255c0efbda5b6a050906d876466086.jpg"
                        }
                        "Alderaan" -> {
                            urlImage = "https://i.pinimg.com/564x/c4/aa/54/c4aa54496e4acfb3b2f90176de852cc9.jpg"
                        }
                        "Stewjon" -> {
                            urlImage = "https://qph.cf2.quoracdn.net/main-qimg-473458d55db89d8fb2703e09a7ff711e-lq"
                        }
                        else -> {
                            urlImage = ""
                        }

                    }

                    with(binding){
                        tvName.text = response.body()?.name
                        tvRotationPeriod.text = getString(R.string.rotation_period, response.body()?.rotation_period)

                        tvOrbitalPeriod.text = getString(R.string.orbital_period, response.body()?.orbital_period)
                        tvDiameter.text = getString(R.string.diameter, response.body()?.diameter)
                        tvClimate.text = getString(R.string.climate, response.body()?.climate)
                        tvTerrain.text = getString(R.string.terrain, response.body()?.terrain)
                        tvPopulation.text = getString(R.string.population, response.body()?.population)

                        Glide.with(this@PlanetDetails)
                            .load(urlImage)
                            .into(ivImage)
                    }

                }

                override fun onFailure(call: Call<PlanetDetail>, t: Throwable) {
                    binding.pbConexion.visibility = View.GONE
                    binding.ivError.visibility = View.VISIBLE
                    binding.btnReload.visibility = View.VISIBLE

                    Toast.makeText(this@PlanetDetails, getString(R.string.connection_error, t.message), Toast.LENGTH_SHORT).show()

                }

            })
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Animatoo.animateSlideLeft(this@PlanetDetails)
    }
}