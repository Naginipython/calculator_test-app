package com.example.calculator_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setting Variable
        var txt = ""
        var ans = 0.0
        var prevOp = 'a'

        //Numbered Buttons
        findViewById<Button>(R.id.btnZero).setOnClickListener {
            txt += "0"
            updateText(txt)
        }
        findViewById<Button>(R.id.btnOne).setOnClickListener{
            txt += "1"
            updateText(txt)
        }
        findViewById<Button>(R.id.btnTwo).setOnClickListener{
            txt += "2"
            updateText(txt)
        }
        findViewById<Button>(R.id.btnThree).setOnClickListener {
            txt += "3"
            updateText(txt)
        }
        findViewById<Button>(R.id.btnFour).setOnClickListener {
            txt += "4"
            updateText(txt)
        }
        findViewById<Button>(R.id.btnFive).setOnClickListener {
            txt += "5"
            updateText(txt)
        }
        findViewById<Button>(R.id.btnSix).setOnClickListener {
            txt += "6"
            updateText(txt)
        }
        findViewById<Button>(R.id.btnSeven).setOnClickListener {
            txt += "7"
            updateText(txt)
        }
        findViewById<Button>(R.id.btnEight).setOnClickListener {
            txt += "8"
            updateText(txt)
        }
        findViewById<Button>(R.id.btnNine).setOnClickListener {
            txt += "9"
            updateText(txt)
        }

        //Other Buttons
        findViewById<Button>(R.id.btnPlus).setOnClickListener {
            ans = doMath(txt, ans, prevOp)
            txt = ""
            prevOp = '+'
            updateText(ans.toString())
        }
        findViewById<Button>(R.id.btnMinus).setOnClickListener {
            ans = doMath(txt, ans, prevOp)
            txt = ""
            prevOp = '-'
            updateText(ans.toString())
        }
        findViewById<Button>(R.id.btnMult).setOnClickListener {
            ans = doMath(txt, ans, prevOp)
            txt = ""
            prevOp = '*'
            updateText(ans.toString())
        }
        findViewById<Button>(R.id.btnDivide).setOnClickListener {
            ans = doMath(txt, ans, prevOp)
            txt = ""
            prevOp = '/'
            updateText(ans.toString())
        }
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            txt = ""
            ans = 0.0
            prevOp = 'a'
            updateText(txt)
        }

        //Equals Button
        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            ans = doMath(txt, ans, prevOp)
            txt = ""
            prevOp = 'a'
            updateText(ans.toString())
        }

    }

    private fun updateText(txt: String) {
        val txtArea = findViewById<TextView>(R.id.textView)
        txtArea.text = txt
    }

    private fun doMath(txt: String, ans: Double, op: Char): Double {
        if (op != 'a') {
            when (op) {
                '+' -> return ans+txt.toDouble()
                '-' -> return ans-txt.toDouble()
                '*' -> return ans*txt.toDouble()
                '/' -> return ans/txt.toDouble()
            }
        }
        return txt.toDouble()
    }
}