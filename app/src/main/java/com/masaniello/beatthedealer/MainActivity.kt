package com.masaniello.beatthedealer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    private var capitale = 0.0
    private var capitaleIniziale = 0.0
    private var eventiTotali = 0
    private var eventiDaVincere = 0
    private var eventiGiocati = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart.setOnClickListener {
            capitale = etCapitale.text.toString().toDouble()
            capitaleIniziale = capitale
            eventiTotali = etEventiTotali.text.toString().toInt()
            eventiDaVincere = etEventiDaVincere.text.toString().toInt()
            eventiGiocati = 0
            aggiornaPuntata()
        }

        btnVinta.setOnClickListener {
            val quota = etQuota.text.toString().toDouble()
            val puntata = etPuntata.text.toString().toDouble()
            capitale += puntata * (quota - 1)
            eventiDaVincere--
            eventiGiocati++
            aggiornaPuntata()
        }

        btnPersa.setOnClickListener {
            val puntata = etPuntata.text.toString().toDouble()
            capitale -= puntata
            eventiGiocati++
            aggiornaPuntata()
        }
    }

    private fun aggiornaPuntata() {
        if (eventiDaVincere <= 0 || eventiGiocati >= eventiTotali) {
            tvStatus.text = "Ciclo terminato\nUtile finale: €${String.format("%.2f", capitale - capitaleIniziale)}"
            return
        }

        val quota = etQuota.text.toString().toDouble()
        val puntata = capitale / (eventiDaVincere * (quota - 1))
        etPuntata.setText(String.format("%.2f", puntata))

        tvCapitale.text = "Capitale: €${String.format("%.2f", capitale)}"
        tvStatus.text = "Eventi rimanenti: ${eventiTotali - eventiGiocati}\n" +
                "Utile previsto: €${String.format("%.2f", calcolaUtilePrevisto(quota, puntata))}"
    }

    private fun calcolaUtilePrevisto(quota: Double, puntata: Double): Double {
        val profittoTotale = eventiDaVincere * puntata * (quota - 1)
        val perditeAttuali = capitaleIniziale - capitale
        return profittoTotale - perditeAttuali
    }
}
