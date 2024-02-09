package com.thewanderingfox.passgenkotlin

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Random
import kotlin.text.toCharArray

class MainActivity : AppCompatActivity() {

    private lateinit var tvPassword: TextView
    private lateinit var btnGenerate: Button
    private lateinit var btnCopy: Button
    private lateinit var txbCharLength: EditText
    private lateinit var numbers: CheckBox
    private lateinit var upletters: CheckBox
    private lateinit var lowletters: CheckBox
    private lateinit var symbols: CheckBox

    private lateinit var myClipboard: ClipboardManager
    private lateinit var myClip: ClipData

    @SuppressLint("VisibleForTests", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvPassword = findViewById(R.id.tvPassword)
        btnGenerate = findViewById(R.id.btnGenerate)
        btnCopy = findViewById(R.id.btnCopy)
        txbCharLength = findViewById(R.id.txbCharLength)

        numbers = findViewById(R.id.Numbers)
        upletters = findViewById(R.id.UpLetters)
        lowletters = findViewById(R.id.LowLetters)
        symbols = findViewById(R.id.Symbols)


        btnGenerate.setOnClickListener {
            val value = txbCharLength.text.toString()
            var length = 10

            // Determine selected character sets
            val charSets = mutableListOf<CharArray>()
            if (numbers.isChecked) charSets.add("0123456789".toCharArray())
            if (upletters.isChecked) charSets.add("ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray())
            if (lowletters.isChecked) charSets.add("abcdefghijklmnopqrstuvwxyz".toCharArray())
            if (symbols.isChecked) charSets.add("!@#$%^&*()-_=+{[]}:;'?/".toCharArray())

            // Handle user selection - generate password with all sets if none selected
            if (charSets.isEmpty()) {
                charSets.add("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+{[]}:;'?/".toCharArray())
            }

            if (TextUtils.isEmpty(value)) {
                Toast.makeText(applicationContext, "Please enter a number!", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val length = Integer.parseInt(value)
                    tvPassword.text = getPassword(length, charSets)
                } catch (e: NumberFormatException) {
                    Toast.makeText(applicationContext, "Please enter a number.", Toast.LENGTH_SHORT).show()
                }
            }

            // Toast notification
            Toast.makeText(applicationContext, "Password generated!", Toast.LENGTH_SHORT).show()

        }
        btnCopy.setOnClickListener {
            myClipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val text = tvPassword.text.toString()
            myClip = ClipData.newPlainText("text", text)
            myClipboard.setPrimaryClip(myClip)
            Toast.makeText(applicationContext, "Password Copied", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getPassword(length: Int, charSets: List<CharArray>): String {
        val stringBuilder = StringBuilder()

        // Combine all character sets into a single list
        val allChars = charSets.flatMap { it.toList() }

        // Handle potential empty character sets
        if (allChars.isEmpty()) {
            Toast.makeText(applicationContext, "No character sets selected!", Toast.LENGTH_SHORT).show()
            return ""
        }

        val rand = Random()

        // Iterate until desired password length is reached
        for (i in 0 until length) {
            val index = rand.nextInt(allChars.size)
            stringBuilder.append(allChars[index])
        }

        return stringBuilder.toString()
    }
}