package com.jostin.conversorlogin

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.google.android.material.textfield.TextInputEditText
import com.jostin.conversorlogin.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.NumberFormat
import java.util.*
import androidx.navigation.findNavController
import com.jostin.conversorlogin.R

class ConversorActivity : AppCompatActivity() {

    private lateinit var amountEditText: TextInputEditText
    private lateinit var usdRadioButton: RadioButton
    private lateinit var eurRadioButton: RadioButton
    private lateinit var mxnRadioButton: RadioButton
    private lateinit var convertButton: Button
    private lateinit var resultTextView: TextView

    private val api by lazy {
        Retrofit.Builder()
            .baseUrl("https://v6.exchangerate-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeRateApi::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversor)
        amountEditText = findViewById(R.id.amountEditText)
        usdRadioButton = findViewById(R.id.usdRadioButton)
        eurRadioButton = findViewById(R.id.eurRadioButton)
        mxnRadioButton = findViewById(R.id.mxnRadioButton)
        convertButton = findViewById(R.id.convertButton)
        resultTextView = findViewById(R.id.resultTextView)

        convertButton.setOnClickListener {
            convertCurrency()
        }


    }

    private fun convertCurrency() {
        val amount = amountEditText.text.toString().toDoubleOrNull()
        if (amount == null) {
            resultTextView.text = "Por favor, ingrese un monto válido"
            return
        }

        val selectedCurrency = when {
            usdRadioButton.isChecked -> "USD"
            eurRadioButton.isChecked -> "EUR"
            mxnRadioButton.isChecked -> "MXN"
            else -> {
                resultTextView.text = "Por favor, seleccione una moneda"
                return
            }
        }

        lifecycleScope.launch {
            try {
                val response = api.getLatestRates(selectedCurrency)
                val rate = response.conversion_rates[selectedCurrency]
                if (rate != null) {
                    val convertedAmount = amount * rate
                    val formattedAmount = NumberFormat.getCurrencyInstance().apply {
                        currency = Currency.getInstance(selectedCurrency)
                    }.format(convertedAmount)
                    resultTextView.text = formattedAmount
                } else {
                    resultTextView.text = "Tasa de conversión no disponible"
                }
            } catch (e: Exception) {
                resultTextView.text = "Error al obtener las tasas de cambio"
            }
        }

    }

}