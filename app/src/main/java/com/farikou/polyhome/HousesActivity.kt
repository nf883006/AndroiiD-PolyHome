package com.farikou.polyhome

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HousesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_houses)

        val rvHouses = findViewById<RecyclerView>(R.id.rvHouses)
        rvHouses.layoutManager = LinearLayoutManager(this)

        val prefs = getSharedPreferences("polyhome_prefs", MODE_PRIVATE)
        val token = prefs.getString("token", null)

        val api = Api()
        api.get<List<House>>("https://polyhome.lesmoulinsdudev.com/api/houses", { code, houses ->
            runOnUiThread {
                if (code == 200 && houses != null ) {
                    rvHouses.adapter = HousesAdapter(houses) { house ->
                        // 1. Message de confirmation
                        Toast.makeText(this, "Ouverture de la maison n°${house.houseId}", Toast.LENGTH_SHORT).show()

                        // 2. Redirection vers l'écran des équipements
                        val intent = Intent(this, DevicesActivity::class.java)
                        intent.putExtra("HOUSE_ID", house.houseId) // On transmet l'ID de la maison
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this, "Erreur API : $code", Toast.LENGTH_SHORT).show()
                }
            }
        }, token)
    }
}
