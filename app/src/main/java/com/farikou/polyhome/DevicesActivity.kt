package com.farikou.polyhome

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DevicesActivity : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private var devices = listOf<Device>()
    private val api = Api()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devices)

        val houseId = intent.getIntExtra("HOUSE_ID", -1)
        val token = getSharedPreferences("polyhome", MODE_PRIVATE).getString("token", "")

        rv = findViewById(R.id.rvDevices)
        rv.layoutManager = LinearLayoutManager(this)

        fun refresh() {
            api.get<DeviceResponse>("https://polyhome.lesmoulinsdudev.com/api/houses/$houseId/devices", { _, res ->
                runOnUiThread {
                    devices = res?.devices ?: listOf( )
                    rv.adapter = DevicesAdapter(devices) { dev, cmd ->
                        api.post("https://polyhome.lesmoulinsdudev.com/api/houses/$houseId/devices/${dev.id}/command", mapOf("command" to cmd ), { refresh() }, token)
                    }
                }
            }, token)
        }

        findViewById<Button>(R.id.btnAllOpen).setOnClickListener {
            devices.filter { it.type == "shutter" }.forEach { dev ->
                api.post("https://polyhome.lesmoulinsdudev.com/api/houses/$houseId/devices/${dev.id}/command", mapOf("command" to "OPEN" ), {}, token)
            }
            refresh()
        }

        refresh()
    }
}
