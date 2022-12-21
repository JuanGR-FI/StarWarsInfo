package com.example.starwarsinfo.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
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

        CoroutineScope(Dispatchers.IO).launch {
            val call = Constants.getRetrofit().create(CharactersApi::class.java).getCharacters("api/people/")
            call.enqueue(object: Callback<CharacterDetail>{
                override fun onResponse(
                    call: Call<CharacterDetail>,
                    response: Response<CharacterDetail>
                ) {
                    Log.d(Constants.LOGTAG, "Respuesta del servidor: ${response.toString()}")
                    Log.d(Constants.LOGTAG, "Datos: ${response.body().toString()}")

                    /*val characterTmp: Character
                    for(characterTmp in response.body()!!.results)
                        Toast.makeText(this@MainActivity, "Nombre del personaje: ${characterTmp.name}", Toast.LENGTH_SHORT).show()*/

                    binding.rvMenu.layoutManager = LinearLayoutManager(this@MainActivity)
                    binding.rvMenu.adapter = Adapter(this@MainActivity, response.body()!!)

                    binding.pbConexion.visibility = View.GONE
                    binding.ivError.visibility = View.INVISIBLE


                }

                override fun onFailure(call: Call<CharacterDetail>, t: Throwable) {
                    binding.pbConexion.visibility = View.GONE
                    binding.ivError.visibility = View.VISIBLE
                    Toast.makeText(this@MainActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()


                }

            })
        }




    }

    fun selectedPlanet(character: Character) {
        val planetID = character.homeworld?.get((character.homeworld?.length)?.minus(2) ?: 1)
        Toast.makeText(this@MainActivity, "ID del planeta: ${planetID}", Toast.LENGTH_SHORT).show()
        val parametros = Bundle()
        parametros.apply {
            putString("id", planetID.toString())
        }

        val intent = Intent(this@MainActivity, PlanetDetails::class.java)

        intent.putExtras(parametros)

        startActivity(intent)

    }

    fun selectedFilms(character: Character) {

    }
}