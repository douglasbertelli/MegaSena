package com.example.megasena

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.Random


class MainActivity : AppCompatActivity() {

	// ativando o banco de dados local (chave e valor)
	private lateinit var prefs: SharedPreferences

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContentView(R.layout.activity_main)

		// pegando as referencias dos objeto da tela.
		val editText: EditText = findViewById(R.id.edit_number)
		val txtResult: TextView = findViewById(R.id.txt_result)
		val btnGenerate: Button = findViewById(R.id.btn_generate)

		// banco de dados local do tipo chave e valor.
		prefs = getSharedPreferences("db", Context.MODE_PRIVATE)

		// pegando o dados no banco
		val result = prefs.getString("result", null)

		// verificando se é diferente de vázio, caso seja TRUE passe o dados do banco para variavel txtResult.
		if (result != null) {
			txtResult.text = "Último resultado: $result"
		}

		btnGenerate.setOnClickListener {
			// pegando o numero digitado.
			val text = editText.text.toString()

			// invocando a função.
			numberGenerator(text, txtResult)
		}

	}

	private fun numberGenerator(text: String, txtResult: TextView) {
		// verificando se o campo está vázio
		if (text.isEmpty()) {
			Toast.makeText(this, "Informe um número entre 6 e 15.", Toast.LENGTH_LONG).show()

			return
		}

		// convertendo de string para int
		val qtd = text.toInt()

		// verificando se é menor que 6 ou maior que 15.
		if (qtd < 6 || qtd > 15) {
			Toast.makeText(this, "Informe um número entre 6 e 15.", Toast.LENGTH_LONG).show()

			return
		}

		// o mutableSetOf é uma lista que não permite que numeros sejam iguais.
		val numbers = mutableSetOf<Int>()

		// gerando números aleatórios
		val random = Random()

		while (true) {
			val number = random.nextInt(60)

			numbers.add(number + 1)

			if (numbers.size == qtd) {
				break
			}
		}

		// passando os numeros para a variável.
		txtResult.text = numbers.joinToString(" - ")

		// ...
		val editor = prefs.edit()

		// setando a chave e o valor para o armazenamento
		editor.putString("result", txtResult.text.toString())

		// de fato salvando...
		editor.apply()


	}


}