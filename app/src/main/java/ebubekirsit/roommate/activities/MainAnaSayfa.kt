package ebubekirsit.roommate.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ebubekirsit.roommate.databinding.ActivityMainAnaSayfaBinding

class MainAnaSayfa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainAnaSayfaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIlanOlustur.setOnClickListener {
            intent = Intent(applicationContext, MainIlanOlustur::class.java)
            startActivity(intent)
        }

        binding.btnCikisYap.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnIlangoruntule.setOnClickListener {
            intent = Intent(applicationContext, MainIlanListele::class.java)
            startActivity(intent)
        }

    }
}