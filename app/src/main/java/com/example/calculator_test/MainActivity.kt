package com.example.calculator_test

import android.os.Bundle
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setting Variable
        var txt = ""
        var ans = 0.0
        var prevOp = 'a'
        var clearCounter = 0

        //Initial text screen set
        updateText(ans.toString())

        //Update number function
        fun updateNum(num: String) {
            if (txt.length > (13*3)-1) {
                Toast.makeText(applicationContext, "Number is too long", Toast.LENGTH_SHORT).show()
            } else {
                txt += num
                updateText(txt)
                clearCounter = 0
            }
        }

        //Numbered Buttons
        findViewById<Button>(R.id.btnZero).setOnClickListener {
            updateNum("0")
        }
        findViewById<Button>(R.id.btnOne).setOnClickListener{
            updateNum("1")
        }
        findViewById<Button>(R.id.btnTwo).setOnClickListener{
            updateNum("2")
        }
        findViewById<Button>(R.id.btnThree).setOnClickListener {
            updateNum("3")
        }
        findViewById<Button>(R.id.btnFour).setOnClickListener {
            updateNum("4")
        }
        findViewById<Button>(R.id.btnFive).setOnClickListener {
            updateNum("5")
        }
        findViewById<Button>(R.id.btnSix).setOnClickListener {
            updateNum("6")
        }
        findViewById<Button>(R.id.btnSeven).setOnClickListener {
            updateNum("7")
        }
        findViewById<Button>(R.id.btnEight).setOnClickListener {
            updateNum("8")
        }
        findViewById<Button>(R.id.btnNine).setOnClickListener {
            updateNum("9")
        }

        //function for common lines in ops and equals
        fun resetOp(op: Char) {
            if (txt.isNotEmpty()) ans = doMath(txt, ans, prevOp)
            txt = ""
            prevOp = op
            clearCounter = 0
            updateText(ans.toString())
        }

        //Other Buttons
        findViewById<Button>(R.id.btnPlus).setOnClickListener {
            resetOp('+')
        }
        findViewById<Button>(R.id.btnMinus).setOnClickListener {
            resetOp('-')
        }
        findViewById<Button>(R.id.btnMult).setOnClickListener {
            resetOp('*')
        }
        findViewById<Button>(R.id.btnDivide).setOnClickListener {
            resetOp('/')
        }

        //Equals Button
        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            resetOp('a')
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            clearCounter++
            when (clearCounter) {
                1 -> {
                    txt = ""
                    updateText(ans.toString())
                    Toast.makeText(applicationContext, "Click once more to clear everything", Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    txt = ""
                    ans = 0.0
                    prevOp = 'a'
                    updateText(txt)
                }
            }

        }

    }

    private fun updateText(txt: String) {
        val txtArea = findViewById<TextView>(R.id.textView)
        txtArea.text = txt

        //Moves scrollView to the right after update
        var scrollView = findViewById<HorizontalScrollView>(R.id.scroll)
        scrollView.postDelayed(
            Runnable { scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT) },
            100L
        )
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