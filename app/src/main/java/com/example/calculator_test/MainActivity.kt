package com.example.calculator_test

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
//rhino implementation
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setting Variable
        var txt = ""
        var ans = 0.0

        //Textview number updater
        fun updateText(addTxt: String) {
            val txtArea = findViewById<TextView>(R.id.textView)
            val lastChar = if(txt.isNotEmpty()) txt.substring(txt.length-1, txt.length) else ""
            var minusException = false

            //Check prev input. If its a operator (except minus) we replace
            if (lastChar=="+"||lastChar=="*"||lastChar=="/")
                if (addTxt=="+"||addTxt=="*"||addTxt=="/")
                    txt = txt.substring(0, txt.length-1)

            //Remove triple minus signs
            val secondLastChar = if(txt.length>2) txt.substring(txt.length-2, txt.length-1) else ""
            if (lastChar=="-")
                if (secondLastChar=="+"||secondLastChar=="-"||secondLastChar=="*"||secondLastChar=="/")
                    when (addTxt) {
                        "+" -> { txt = txt.substring(0, txt.length-2) + "+-"; minusException = true }
                        "-" -> { txt = txt.substring(0, txt.length-2) + "--"; minusException = true }
                        "*" -> { txt = txt.substring(0, txt.length-2) + "*-"; minusException = true }
                        "/" -> { txt = txt.substring(0, txt.length-2) + "/-"; minusException = true }
                    }

            //Minus exception already changes text, so this isn't needed then
            if (!minusException) {
                //Check if only number is 0
                if ((txt.length == 1 && lastChar == "0") || txt == "0.0")
                    txt = addTxt
                else
                    txt += addTxt
            }

            txtArea.text = txt

            //Moves scrollView to the right after update
            val scrollView = findViewById<HorizontalScrollView>(R.id.scroll)
            scrollView.postDelayed(
                Runnable { scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT) },
                100L
            )
        }

        //Initial text screen set
        updateText("0")

        //Numbered Buttons
        findViewById<Button>(R.id.btnZero).setOnClickListener {
            updateText("0")
        }
        findViewById<Button>(R.id.btnOne).setOnClickListener{
            updateText("1")
        }
        findViewById<Button>(R.id.btnTwo).setOnClickListener{
            updateText("2")
        }
        findViewById<Button>(R.id.btnThree).setOnClickListener {
            updateText("3")
        }
        findViewById<Button>(R.id.btnFour).setOnClickListener {
            updateText("4")
        }
        findViewById<Button>(R.id.btnFive).setOnClickListener {
            updateText("5")
        }
        findViewById<Button>(R.id.btnSix).setOnClickListener {
            updateText("6")
        }
        findViewById<Button>(R.id.btnSeven).setOnClickListener {
            updateText("7")
        }
        findViewById<Button>(R.id.btnEight).setOnClickListener {
            updateText("8")
        }
        findViewById<Button>(R.id.btnNine).setOnClickListener {
            updateText("9")
        }

        //Other Buttons
        findViewById<Button>(R.id.btnPlus).setOnClickListener {
            updateText("+")
        }
        findViewById<Button>(R.id.btnMinus).setOnClickListener {
            updateText("-")
        }
        findViewById<Button>(R.id.btnMult).setOnClickListener {
            updateText("*")
        }
        findViewById<Button>(R.id.btnDivide).setOnClickListener {
            updateText("/")
        }

        //Equals Button
        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            //setup for js math parser
            val context = Context.enter()
            context.optimizationLevel = -1
            val scope: Scriptable = context.initStandardObjects()
            val result = context.evaluateString(scope, txt, "<cmd>", 1, null)
            txt = ""
            updateText(result.toString())
            Log.d("JS Math", "" + result)
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
//            when (clearCounter) {
//                1 -> {
//                    txt = ""
//                    updateText(ans.toString())
//                    Toast.makeText(applicationContext, "Click once more to clear everything", Toast.LENGTH_SHORT).show()
//                }
//                2 -> {
//                    txt = ""
//                    ans = 0.0
//                    updateText(txt)
//                }
//            }

        }
    }
}