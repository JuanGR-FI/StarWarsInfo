package com.example.starwarsinfo.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.starwarsinfo.R
import com.example.starwarsinfo.databinding.ActivityMainBinding
import com.example.starwarsinfo.model.Character
import com.example.starwarsinfo.model.CharacterDetail
import com.example.starwarsinfo.model.CharactersApi
import com.example.starwarsinfo.util.Constants
import com.example.starwarsinfo.view.adapters.Adapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tryConnection()

    }

    fun selectedPlanet(character: Character) {
        val planetID = character.homeworld!!.substring(22,  character.homeworld.length-1)
        //Toast.makeText(this@MainActivity, "ID del planeta: $planetID", Toast.LENGTH_SHORT).show()
        val parametros = Bundle()
        parametros.apply {
            putString("id", planetID)
        }

        val intent = Intent(this@MainActivity, PlanetDetails::class.java)

        intent.putExtras(parametros)

        startActivity(intent)

        Animatoo.animateSlideRight(this@MainActivity)

    }

    fun selectedFilms(character: Character) {
        val parametros = Bundle()
        parametros.apply {
            putStringArrayList("filmsurls", character.films)
        }

        val intent = Intent(this@MainActivity, FilmsDetail::class.java)

        intent.putExtras(parametros)

        startActivity(intent)

        Animatoo.animateSlideLeft(this@MainActivity)
    }

    fun reloadConnection(view: View) {
        binding.ivError.visibility = View.GONE
        view.visibility = View.GONE

        binding.pbConexion.visibility = View.VISIBLE

        tryConnection()
    }

    private fun tryConnection(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = Constants.getRetrofit().create(CharactersApi::class.java).getCharacters(Constants.CHARACTERS_URL)
            call.enqueue(object: Callback<CharacterDetail>{
                override fun onResponse(
                    call: Call<CharacterDetail>,
                    response: Response<CharacterDetail>
                ) {
                    Log.d(Constants.LOGTAG, "Respuesta del servidor: $response")
                    Log.d(Constants.LOGTAG, "Datos: ${response.body().toString()}")

                    binding.rvMenu.layoutManager = LinearLayoutManager(this@MainActivity)
                    binding.rvMenu.adapter = Adapter(this@MainActivity, response.body()!!)

                    binding.pbConexion.visibility = View.GONE
                    binding.ivError.visibility = View.INVISIBLE
                    binding.btnReload.visibility = View.INVISIBLE


                }

                override fun onFailure(call: Call<CharacterDetail>, t: Throwable) {
                    binding.pbConexion.visibility = View.GONE
                    binding.ivError.visibility = View.VISIBLE
                    binding.btnReload.visibility = View.VISIBLE

                    Toast.makeText(this@MainActivity, getString(R.string.connection_error, t.message), Toast.LENGTH_SHORT).show()


                }

            })
        }
    }

}