package com.example.smartcalcu

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.example.smartcalcu.databinding.ActivityBmiactivityBinding
import java.util.Locale

class BMIActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    lateinit var tts: TextToSpeech
    lateinit var binding: ActivityBmiactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the binding instance
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        // Set the content view to the root view of the binding
        setContentView(binding.root)
        tts = TextToSpeech(this, this)
        binding.weightPicker.minValue = 30
        binding.weightPicker.maxValue = 150

        binding.heightPicker.minValue = 100
        binding.heightPicker.maxValue = 250

        binding.weightPicker.setOnValueChangedListener{ _,_,_ ->
            calculateBMI()
        }

        binding.heightPicker.setOnValueChangedListener{ _,_,_ ->
            calculateBMI()
        }

    }

    private fun calculateBMI()
    {
        val height = binding.heightPicker.value
        val doubleHeight = height.toDouble() / 100

        val weight = binding.weightPicker.value

        val bmi = weight.toDouble() / (doubleHeight * doubleHeight)

        binding.resultsTV.text = String.format("Your BMI is: %.2f", bmi)
        binding.healthyTV.text = String.format("Considered: %s", healthyMessage(bmi))

    }

    private fun healthyMessage(bmi: Double): String
    {
        if (bmi < 18.5){
            speakOut("Underweight")
            binding.layout.setBackgroundColor(Color.parseColor("#Ffff00"))
            return "Underweight"
        }
        if(bmi < 25.0){
            binding.layout.setBackgroundColor(Color.parseColor("#008000"))
        speakOut("Healthy")
            return "Healthy"}
        if (bmi < 30.0) {
            binding.layout.setBackgroundColor(Color.parseColor("#FF0000"))
            speakOut("Overweight")
            return "Overweight"
        }
        binding.layout.setBackgroundColor(Color.parseColor("#87CEEB"))
        speakOut("Obese")
        return "Obese"
    }
    fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, null, "")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Set the language
            val result = tts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Handle the error
            } else {
                // TTS is ready to use
                speakOut("Welcome to Smart Measure App")
            }
        } else {
            // Initialization failed
        }


    }
    override fun onDestroy() {
        // Shutdown TTS when activity is destroyed
        if (tts.isSpeaking) {
            tts.stop()
        }
        tts.shutdown()
        super.onDestroy()
    }
}