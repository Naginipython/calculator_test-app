package com.example.calculator_test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
//rhino implementation
import org.mozilla.javascript.Context
import org.mozilla.javascript.EcmaError
import org.mozilla.javascript.Scriptable


class MainActivity : AppCompatActivity() {
    //Necessary for passing to next Activity (I think)
    private var history = mutableListOf<Equation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setting Variable
        var txt = ""
        var ans = 0.0

        history.addAll(recoverHistory())
        Log.d("Current History:", history.toString())

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
                if (txt.isEmpty()&&isOp(addTxt, true))
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
        updateText("")

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

        //Other buttons
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

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            txt = if (txt == ans.toString())
                ""
            else
                if (txt.isNotEmpty()) txt.substring(0, txt.length-1) else ""
            updateText("")
        }

        //Equals Button
        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            //setup for js math parser
            val context = Context.enter()
            context.optimizationLevel = -1
            val scope: Scriptable = context.initStandardObjects()
            try {
                //Answer:
                val result = context.evaluateString(scope, txt, "<cmd>", 1, null)
                Log.d("JS Math", result.toString())

                //Saving results:
                val addToFront = mutableListOf<Equation>(Equation(txt, result.toString().toDouble()))
                addToFront.addAll(recoverHistory())
                history = addToFront
                saveHistory(history)

                //resetting:
                txt = ""
                ans = result.toString().toDouble()
                updateText(result.toString())

                //Moves scrollView to the left after update
                val scrollView = findViewById<HorizontalScrollView>(R.id.scroll)
                scrollView.postDelayed({
                    scrollView.fullScroll(HorizontalScrollView.FOCUS_LEFT) },100L)
            } catch (e: EcmaError) {
//                Toast.makeText(this, "Can't evaluate expression", Toast.LENGTH_LONG).show()

                //Created a custom Toast to try it out
                val inflater = layoutInflater
                val layout: View = inflater.inflate(
                    R.layout.activity_main_toast,
                    null
                )
                val toast = Toast(this)
                toast.apply {
                    duration = Toast.LENGTH_LONG
                    setView(layout)
                    show()
                }
            }
        }
    }
    //verride functions
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.itHistory -> Intent(this, HistoryActivity::class.java).also {
                it.putExtra("EXTRA_HISTORY", Gson().toJson(history))
                startActivity(it)
            }
            R.id.itSettings -> Intent(this, SettingsActivity::class.java).also {
                startActivity(it)
            }
        }
        return true
    }

    //My Functions
    private fun saveHistory(history: List<Equation>) {
        val save = applicationContext.getSharedPreferences("history", 0)
        save.edit().also {
            it.putString("historyString", Gson().toJson(history))
            Log.d("Saved Data: ", history.toString())
            it.apply()
        }
    }
    private fun recoverHistory(): List<Equation> {
        val save = applicationContext.getSharedPreferences("history", 0)
        val json = save.getString("historyString", null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<Equation>>() {}.type
            val data = Gson().fromJson<List<Equation>>(json, type)
            Log.d("Receieved Data: ", data.toString())
            data
        } else {
            mutableListOf<Equation>()
        }
    }
}