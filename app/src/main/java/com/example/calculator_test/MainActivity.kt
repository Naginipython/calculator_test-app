package com.example.calculator_test

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//rhino implementation
import org.mozilla.javascript.Context
import org.mozilla.javascript.EcmaError
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
            //variable declarations
            val txtArea = findViewById<TextView>(R.id.textView)
            val lastChar = if(txt.isNotEmpty()) txt.substring(txt.length-1, txt.length) else ""
            var minusException = false
            var prevAnswered = false

            //quick operator checking function
            fun isOp(str: String, hasMinus: Boolean): Boolean {
                return str=="+"||str=="*"||str=="/"|| if(hasMinus)str=="-" else false
            }

            //Check prev input. If its a operator (except minus) we replace
            if (isOp(lastChar, false))
                if (isOp(addTxt,false))
                    txt = txt.substring(0, txt.length-1)

            //Remove triple minus signs
            val secondLastChar = if(txt.length>2) txt.substring(txt.length-2, txt.length-1) else ""
            if (lastChar=="-")
                if (isOp(secondLastChar, true))
                    when (addTxt) {
                        "+" -> { txt = txt.substring(0, txt.length-2) + "+-"; minusException = true }
                        "-" -> { txt = txt.substring(0, txt.length-2) + "--"; minusException = true }
                        "*" -> { txt = txt.substring(0, txt.length-2) + "*-"; minusException = true }
                        "/" -> { txt = txt.substring(0, txt.length-2) + "/-"; minusException = true }
                    }

            //If calc was prev answered, write over text unless its operators
            if (txt == ans.toString() && !isOp(addTxt, true)) {
                txt = addTxt; prevAnswered = true
            }

            //Minus exception already changes text, so this isn't needed then
            if (!minusException && !prevAnswered) {
                //Check if only number is 0
                if (txt.length == 1 && lastChar == "0")
                    txt = addTxt
                else if (txt.isEmpty()&&isOp(addTxt, true))
                    txt = "0$addTxt"
                else
                    txt += addTxt
            }

            txtArea.text = txt

            //Moves scrollView to the right after update
            val scrollView = findViewById<HorizontalScrollView>(R.id.scroll)
            scrollView.postDelayed({
                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT) },100L)
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

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            txt = ""
            ans = 0.0
            updateText("")
        }
        findViewById<Button>(R.id.btnPeriod).setOnClickListener {
            updateText(".")
        }

        findViewById<Button>(R.id.btnLeftPara).setOnClickListener {
            updateText("(")
        }

        findViewById<Button>(R.id.btnRightPara).setOnClickListener {
            updateText(")")
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            if (txt == ans.toString())
                txt = ""
            else
                txt = if (txt.isNotEmpty()) txt.substring(0, txt.length-1) else ""
            updateText("")
        }

        //Equals Button
        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            //setup for js math parser
            val context = Context.enter()
            context.optimizationLevel = -1
            val scope: Scriptable = context.initStandardObjects()
            try {
                val result = context.evaluateString(scope, txt, "<cmd>", 1, null)
                Log.d("JS Math", "" + result)
                txt = ""
                ans = result.toString().toDouble()
                findViewById<TextView>(R.id.textView).text = result.toString()

                //Moves scrollView to the left after update
                val scrollView = findViewById<HorizontalScrollView>(R.id.scroll)
                scrollView.postDelayed({
                    scrollView.fullScroll(HorizontalScrollView.FOCUS_LEFT) },100L)
            } catch (e: EcmaError) {
                Toast.makeText(this, "Can't evaluate expression", Toast.LENGTH_SHORT).show()
            }
        }


    }
}