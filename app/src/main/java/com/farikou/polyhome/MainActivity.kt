package com.farikou.polyhome

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editLogin = findViewById<EditText>(R.id.editLogin)
        val editPassword = findViewById<EditText>(R.id.editPassword)
        val api = Api()

        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            val data = mapOf("login" to editLogin.text.toString(), "password" to editPassword.text.toString())
            api.post("https://polyhome.lesmoulinsdudev.com/api/users/register", data, { code ->
                runOnUiThread { Toast.makeText(this, if(code==200 ) "Compte créé" else "Erreur $code", Toast.LENGTH_SHORT).show() }
            })
        }

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val data = mapOf("login" to editLogin.text.toString(), "password" to editPassword.text.toString())
            api.post<Map<String, String>, AuthResponse>("https://polyhome.lesmoulinsdudev.com/api/users/auth", data, { code, result ->
                runOnUiThread {
                    if (code == 200 && result != null ) {
                        getSharedPreferences("polyhome", MODE_PRIVATE).edit().putString("token", result.token).apply()
                        startActivity(Intent(this, HousesActivity::class.java))
                    } else Toast.makeText(this, "Erreur $code", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
