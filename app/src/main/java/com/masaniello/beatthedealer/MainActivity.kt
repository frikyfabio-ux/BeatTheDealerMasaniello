package com.masaniello.beatthedealer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.masaniello.beatthedealer.databinding.ActivityMainBinding
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    // Collegamento automatico al layout activity_main.xml
    private lateinit var binding: ActivityMainBinding

    private var capitale = 0.0
    private var eventiTotali = 0
    private var eventiDaVincere = 0
    private var eventiGiocati = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Qui "accendiamo" il collegamento layout <-> codice
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pulsante AVVIA
        binding.btnStart.setOnClickListener {
            capitale = binding.etCapitale.text.toString().toDouble()
            eventiTotali = binding.etEventiTotali.text.toString().toInt()
            eventiDaVincere = binding.etEventiDaVincere.text.toString().toInt()
            eventiGiocati = 0
            aggiornaPuntata()
        }

        // Pulsante VINTA
        binding.btnVinta.setOnClickListener {
            val quota = binding.etQuota.text.toString().toDouble()
            val puntata = binding.etPuntata.text.toString().toDouble()
            capitale += puntata * (quota - 1)
            eventiDaVincere--
            eventiGiocati++
            aggiornaPuntata()
        }

        // Pulsante PERSA
        binding.btnPersa.setOnClickListener {
            val puntata = binding.etPuntata.text.toString().toDouble()
            capitale -= puntata
            eventiGiocati++
            aggiornaPuntata()
        }
    }

    private fun aggiornaPuntata() {
        if (eventiDaVincere <= 0 || eventiGiocati >= eventiTotali) {
            binding.tvStatus.text = "Ciclo terminato"
            return
        }

        val quota = binding.etQuota.text.toString().toDouble()
        val puntata = capitale / (eventiDaVincere * (quota - 1))

        binding.etPuntata.setText((round(puntata * 100) / 100).toString())
        binding.tvCapitale.text = "Capitale: €${round(capitale * 100) / 100}"
        binding.tvStatus.text = "Eventi rimanenti: ${eventiTotali - eventiGiocati}"
    }
}
