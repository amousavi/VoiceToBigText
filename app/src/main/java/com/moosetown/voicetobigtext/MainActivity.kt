package com.moosetown.voicetobigtext

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.moosetown.voicetobigtext.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

private const val SPEECH_REQUEST_CODE = 42
private const val TEXT_SIZE_INCREMENT = 4

class MainActivity : AppCompatActivity(), MainHandler {

    private val uiModel = UiModel()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater).apply {
            model = uiModel
            lifecycleOwner = this@MainActivity
            handler = this@MainActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }

    override fun voiceButtonClicked(view: View) {
        uiModel.editable.value = false
        displaySpeechRecognizer()
    }

    override fun increaseTextSize(view: View) {
        val currentSize = uiModel.textSize.value!!

        uiModel.textSize.value = currentSize + TEXT_SIZE_INCREMENT
    }

    override fun decreaseTextSize(view: View) {
        val currentSize = uiModel.textSize.value!!

        val incrementedSize = currentSize - TEXT_SIZE_INCREMENT

        val newSize = if (incrementedSize > 0) {
            incrementedSize
        } else {
            currentSize
        }

        uiModel.textSize.value = newSize
    }

    override fun toggleEditability(view: View) {
        val editable = uiModel.editable.value!!

        uiModel.editable.value = !editable
    }

    // Create an intent that can start the Speech Recognizer activity
    private fun displaySpeechRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
        }
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Log.d(TAG, "data was null")
            }

            val spokenText: String? =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    results?.get(0)
                } ?: "no results"

            uiModel.text.value = spokenText
            // Do something with spokenText
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}