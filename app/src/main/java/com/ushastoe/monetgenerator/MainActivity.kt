package com.ushastoe.monetgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.google.android.material.color.DynamicColors


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DynamicColors.applyToActivityIfAvailable(this@MainActivity)

        // background = n1_900
        // text = n1_50
        val background_light = String.format("#%06X", 0xFFFFFF and ContextCompat.getColor(this, R.color.n1_50))
        val foreground_light = String.format("#%06X", 0xFFFFFF and ContextCompat.getColor(this, R.color.n1_900))
        val textForCopy = """
            echo '#!/bin/bash

            cd ~/.termux
            
            value=${'$'}(cat colors.properties | tr -d "\\n")
            
            if [ "${'$'}value" = "background=#1E1B19foreground=#F7EFEC" ]; then
              echo -e "background=$background_light\\nforeground=$foreground_light" > colors.properties
            else
              echo -e "background=$foreground_light\\nforeground=$background_light" > colors.properties
            fi
            
            termux-reload-settings' >  ~/.termux/DayNightSwitch.sh
            
            alias sw='~/.termux/DayNightSwitch.sh'
            
            clear
            
            echo "Скрипт установлен, напишите sw, чтобы поменять тему"
        """.trimIndent()
        copyTextToClipboard(this, textForCopy)
        findViewById<TextView>(R.id.text).text = "Теперь вставьте этот код в termux, sw - для смены темы"

        val intent = Intent()
        intent.setClassName("com.termux", "com.termux.HomeActivity")
        startActivity(intent)

        Toast.makeText(applicationContext, "Просто вставьте текст в терминал", Toast.LENGTH_SHORT).show()
    }


    fun copyTextToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
    }

}
