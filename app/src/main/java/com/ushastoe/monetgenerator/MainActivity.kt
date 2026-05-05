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

        // Helper function to get hex color
        fun getHex(resId: Int): String {
            return String.format("#%06X", 0xFFFFFF and ContextCompat.getColor(this, resId))
        }

        // Background / Foreground
        val bg_l = getHex(R.color.n1_50)
        val fg_l = getHex(R.color.n1_900)
        val bg_d = getHex(R.color.n1_900)
        val fg_d = getHex(R.color.n1_50)

        // ANSI Colors Mapping (Monet based)
        // Dark Theme Colors
        val black_d  = getHex(R.color.n1_900)
        val red_d    = getHex(R.color.a3_200)
        val green_d  = getHex(R.color.a2_200)
        val yellow_d = getHex(R.color.a2_100)
        val blue_d   = getHex(R.color.a1_200)
        val mag_d    = getHex(R.color.a3_100)
        val cyan_d   = getHex(R.color.a1_100)
        val white_d  = getHex(R.color.n1_200)
        
        val b_black_d  = getHex(R.color.n1_800)
        val b_red_d    = getHex(R.color.a3_300)
        val b_green_d  = getHex(R.color.a2_300)
        val b_yellow_d = getHex(R.color.a2_200)
        val b_blue_d   = getHex(R.color.a1_300)
        val b_mag_d    = getHex(R.color.a3_200)
        val b_cyan_d   = getHex(R.color.a1_200)
        val b_white_d  = getHex(R.color.n1_50)

        // Light Theme Colors
        val black_l  = getHex(R.color.n1_900)
        val red_l    = getHex(R.color.a3_600)
        val green_l  = getHex(R.color.a2_600)
        val yellow_l = getHex(R.color.a2_500)
        val blue_l   = getHex(R.color.a1_600)
        val mag_l    = getHex(R.color.a3_500)
        val cyan_l   = getHex(R.color.a1_500)
        val white_l  = getHex(R.color.n1_700)

        val b_black_l  = getHex(R.color.n1_600)
        val b_red_l    = getHex(R.color.a3_500)
        val b_green_l  = getHex(R.color.a2_500)
        val b_yellow_l = getHex(R.color.a2_400)
        val b_blue_l   = getHex(R.color.a1_500)
        val b_mag_l    = getHex(R.color.a3_400)
        val b_cyan_l   = getHex(R.color.a1_400)
        val b_white_l  = getHex(R.color.n1_50)

        val textForCopy = """
            mkdir -p ~/.termux
            cat << 'EOF' > ~/.termux/DayNightSwitch.sh
            #!/bin/bash
            CFG="${'$'}HOME/.termux/colors.properties"
            CUR_BG=${'$'}(grep "background=" "${'$'}CFG" 2>/dev/null | cut -d= -f2)

            if [ "${'$'}CUR_BG" = "$bg_l" ]; then
              # Switch to Dark
              echo "background=$bg_d" > "${'$'}CFG"
              echo "foreground=$fg_d" >> "${'$'}CFG"
              echo "color0=$black_d" >> "${'$'}CFG"
              echo "color1=$red_d" >> "${'$'}CFG"
              echo "color2=$green_d" >> "${'$'}CFG"
              echo "color3=$yellow_d" >> "${'$'}CFG"
              echo "color4=$blue_d" >> "${'$'}CFG"
              echo "color5=$mag_d" >> "${'$'}CFG"
              echo "color6=$cyan_d" >> "${'$'}CFG"
              echo "color7=$white_d" >> "${'$'}CFG"
              echo "color8=$b_black_d" >> "${'$'}CFG"
              echo "color9=$b_red_d" >> "${'$'}CFG"
              echo "color10=$b_green_d" >> "${'$'}CFG"
              echo "color11=$b_yellow_d" >> "${'$'}CFG"
              echo "color12=$b_blue_d" >> "${'$'}CFG"
              echo "color13=$b_mag_d" >> "${'$'}CFG"
              echo "color14=$b_cyan_d" >> "${'$'}CFG"
              echo "color15=$b_white_d" >> "${'$'}CFG"
              echo ">>> Dark Mode Applied"
            else
              # Switch to Light
              echo "background=$bg_l" > "${'$'}CFG"
              echo "foreground=$fg_l" >> "${'$'}CFG"
              echo "color0=$black_l" >> "${'$'}CFG"
              echo "color1=$red_l" >> "${'$'}CFG"
              echo "color2=$green_l" >> "${'$'}CFG"
              echo "color3=$yellow_l" >> "${'$'}CFG"
              echo "color4=$blue_l" >> "${'$'}CFG"
              echo "color5=$mag_l" >> "${'$'}CFG"
              echo "color6=$cyan_l" >> "${'$'}CFG"
              echo "color7=$white_l" >> "${'$'}CFG"
              echo "color8=$b_black_l" >> "${'$'}CFG"
              echo "color9=$b_red_l" >> "${'$'}CFG"
              echo "color10=$b_green_l" >> "${'$'}CFG"
              echo "color11=$b_yellow_l" >> "${'$'}CFG"
              echo "color12=$b_blue_l" >> "${'$'}CFG"
              echo "color13=$b_mag_l" >> "${'$'}CFG"
              echo "color14=$b_cyan_l" >> "${'$'}CFG"
              echo "color15=$b_white_l" >> "${'$'}CFG"
              echo ">>> Light Mode Applied"
            fi
            termux-reload-settings
            EOF
            chmod +x ~/.termux/DayNightSwitch.sh
            
            # Setup alias for both zsh and bash
            setup_alias() {
              local rc_file="${'$'}1"
              if [ -f "${'$'}rc_file" ]; then
                grep -q "alias sw=" "${'$'}rc_file" || echo "alias sw='~/.termux/DayNightSwitch.sh'" >> "${'$'}rc_file"
              fi
            }
            setup_alias ~/.zshrc
            setup_alias ~/.bashrc

            clear
            echo "Successfully installed! Use 'sw' to toggle themes."
            ~/.termux/DayNightSwitch.sh
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
