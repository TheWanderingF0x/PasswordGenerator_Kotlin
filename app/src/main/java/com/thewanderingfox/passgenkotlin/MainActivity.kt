package com.thewanderingfox.passgenkotlin
// this is for a commit
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
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

    private lateinit var myClipboard: ClipboardManager
    private lateinit var myClip: ClipData

    @SuppressLint("VisibleForTests")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvPassword = findViewById(R.id.tvPassword)
        btnGenerate = findViewById(R.id.btnGenerate)
        btnCopy = findViewById(R.id.btnCopy)
        txbCharLength = findViewById(R.id.txbCharLength)

        btnGenerate.setOnClickListener {
            val value = txbCharLength.text.toString()
            var length = 10

            if (TextUtils.isEmpty(value)) {
                Toast.makeText(applicationContext, "Please enter a number", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    length = Integer.parseInt(value)
                    tvPassword.text = getPassword(length)
                } catch (e: NumberFormatException) {
                    Toast.makeText(applicationContext, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                }
            }
        }
        btnCopy.setOnClickListener {
            myClipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val text = tvPassword.text.toString()
            myClip = ClipData.newPlainText("text", text)
            myClipboard.setPrimaryClip(myClip)
            Toast.makeText(applicationContext, "Password Copied", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getPassword(length: Int): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+{[]}:;'?/"
        val charArray = chars.toCharArray()
        val stringBuilder = StringBuilder()

        val rand = Random()

        for (i in 0 until length) {
            val c = charArray[rand.nextInt(charArray.size)]
            stringBuilder.append(c)
        }
        return stringBuilder.toString()
    }
}