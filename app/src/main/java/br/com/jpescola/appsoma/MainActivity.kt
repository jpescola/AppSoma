package br.com.jpescola.appsoma

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random
import android.widget.Toast.makeText as makeText1

class MainActivity : AppCompatActivity() {

    // componentes
    lateinit var conta: TextView
    lateinit var pontuacao: TextView
    lateinit var b0: Button
    lateinit var b1: Button
    lateinit var b2: Button

    // respostas possíveis
    private var r0 = 0
    private var r1 = 0
    private var r2 = 0

    // valores da pergunta
    private var v0 = 0
    private var v1 = 0

    // pontuação do usuário
    private var pontos = 0
    private var record = 0
    private var respCorreta: Int = 0
    private var p: Int = 0 // posicao da resposta correta

    override fun onDestroy() {
        super.onDestroy()

        // salva o record ao fechar
        this.getPreferences(Context.MODE_PRIVATE).edit().putInt("RECORD", record).commit()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // inicialização
        conta = findViewById(R.id.conta)
        pontuacao = findViewById(R.id.pontuacao)
        b0 = findViewById(R.id.button0)
        b1 = findViewById(R.id.button1)
        b2 = findViewById(R.id.button2)

        // recupera o record
        record = this.getPreferences(Context.MODE_PRIVATE).getInt("RECORD", 0)

        novoJogo()

    }

    private fun novoJogo() {
        sorteio()

        pontuacao.text = "Pontos: $pontos Record: $record"
        conta.text = "$v0 + $v1 = ?"
        b0.text = "$r0"
        b1.text = "$r1"
        b2.text = "$r2"

        b0.setOnClickListener { checkAnswer(0) }
        b1.setOnClickListener { checkAnswer(1) }
        b2.setOnClickListener { checkAnswer(2) }

    }

    private fun sorteio() {
        v0 = Random.nextInt(10)
        v1 = Random.nextInt(10)
        do {
            r0 = Random.nextInt(20)
            r1 = Random.nextInt(20)
            r2 = Random.nextInt(20)
            p = Random.nextInt(3)
            pontuacao.text = "$p"
            respCorreta = v0 + v1
        } while (
            respCorreta == r0 || respCorreta == r1 || respCorreta == r2 ||
            r0 == r1 || r0 == r2 || r1 == r2
        )

        when (p) {
            0 -> r0 = respCorreta
            1 -> r1 = respCorreta
            else -> r2 = respCorreta
        }
    }

    private fun checkAnswer(r: Int) {
        var correct = false
        if (r == p) {
            toast("Correto")
            pontos++

            // atualiza o record
            if (pontos > record)
                record = pontos

        } else
            toast("Tente novamente...")
        novoJogo()
    }

    private fun toast(s: String) {
        var toast = Toast.makeText(this@MainActivity, s, Toast.LENGTH_SHORT)
        toast.show()
    }

}