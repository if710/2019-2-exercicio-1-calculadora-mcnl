package br.ufpe.cin.android.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.util.Log

class MainActivity : AppCompatActivity() {
    var globalformula: CharSequence? = null
    var globalresult: CharSequence? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var formula: TextView = findViewById(R.id.text_calc)
        var resText: TextView = findViewById(R.id.text_info)

        globalformula = formula.text
        //This lines will be used to finish task 2(For testing, use Nexus 6 with API 23 (Android 6))
        val btn1 = findViewById(R.id.btn_1) as Button
            btn1.setOnClickListener {
            formula.text = "${formula.text}1"
                globalformula = formula.text
        }
        val btn2 = findViewById(R.id.btn_2) as Button
            btn2.setOnClickListener {
                formula.text = "${formula.text}2"
                globalformula = formula.text
        }
        val btn3 = findViewById(R.id.btn_3) as Button
            btn3.setOnClickListener {
                formula.text = "${formula.text}3"
                globalformula = formula.text
        }
        val btn4 = findViewById(R.id.btn_4) as Button
            btn4.setOnClickListener {
                formula.text = "${formula.text}4"
                globalformula = formula.text
        }
        val btn5 = findViewById(R.id.btn_5) as Button
            btn5.setOnClickListener {
                formula.text = "${formula.text}5"
                globalformula = formula.text
        }
        val btn6 = findViewById(R.id.btn_6) as Button
            btn6.setOnClickListener {
                formula.text = "${formula.text}6"
                globalformula = formula.text
        }
        val btn7 = findViewById(R.id.btn_7) as Button
            btn7.setOnClickListener {
                formula.text = "${formula.text}7"
                globalformula = formula.text
        }
        val btn8 = findViewById(R.id.btn_8) as Button
            btn8.setOnClickListener {
                formula.text = "${formula.text}8"
                globalformula = formula.text
        }
        val btn9 = findViewById(R.id.btn_9) as Button
            btn9.setOnClickListener {
                formula.text = "${formula.text}9"
                globalformula = formula.text
        }
        val btn0 = findViewById(R.id.btn_0) as Button
            btn0.setOnClickListener {
                formula.text = "${formula.text}0"
                globalformula = formula.text
        }
        val btnadd = findViewById(R.id.btn_Add) as Button
            btnadd.setOnClickListener {
                formula.text = "${formula.text}+"
                globalformula = formula.text
        }
        val btndivide = findViewById(R.id.btn_Divide) as Button
            btndivide.setOnClickListener {
                formula.text = "${formula.text}/"
                globalformula = formula.text
        }
        val btnmultiply = findViewById(R.id.btn_Multiply) as Button
            btnmultiply.setOnClickListener {
                formula.text = "${formula.text}*"
                globalformula = formula.text
        }
        val btnsubtract = findViewById(R.id.btn_Subtract) as Button
            btnsubtract.setOnClickListener {
                formula.text = "${formula.text}-"
                globalformula = formula.text
        }
        val btndot = findViewById(R.id.btn_Dot) as Button
            btndot.setOnClickListener {
                formula.text = "${formula.text}."
                globalformula = formula.text
        }
        val btnpower = findViewById(R.id.btn_Power) as Button
            btnpower.setOnClickListener {
                formula.text = "${formula.text}^"
                globalformula = formula.text
        }
        val btnLpar = findViewById(R.id.btn_LParen) as Button
            btnLpar.setOnClickListener {
                formula.text = "${formula.text}("
                globalformula = formula.text
        }
        val btnRpar = findViewById(R.id.btn_RParen) as Button
            btnRpar.setOnClickListener {
                formula.text = "${formula.text})"
                globalformula = formula.text
        }
        val btnclear = findViewById(R.id.btn_Clear) as Button
            btnclear.setOnClickListener {
                formula.text = ""
                globalformula = formula.text
        }
        val btnequal = findViewById(R.id.btn_Equal) as Button
            btnequal.setOnClickListener {
                try {
                    var result = eval(formula.text.toString())
                    resText.text = result.toString()

                }
                catch (e: RuntimeException){
                    Toast.makeText(this, "Invalid Expression", Toast.LENGTH_SHORT).show()
                }
                globalresult = resText.text
        }

        if(savedInstanceState != null){
            formula.text = savedInstanceState.getCharSequence("formula")
            resText.text = savedInstanceState.getString("result")

        }

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putCharSequence("result", globalresult)
        outState.putCharSequence("formula", globalformula)
    }


    //Como usar a função:
    // eval("2+2") == 4.0
    // eval("2+3*4") = 14.0
    // eval("(2+3)*4") = 20.0
    //Fonte: https://stackoverflow.com/a/26227947
    fun eval(str: String): Double {
        return object : Any() {
            var pos = -1
            var ch: Char = ' '
            fun nextChar() {
                val size = str.length
                ch = if ((++pos < size)) str.get(pos) else (-1).toChar()
            }

            fun eat(charToEat: Char): Boolean {
                while (ch == ' ') nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < str.length) throw RuntimeException("Caractere inesperado: " + ch)
                return x
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            // | number | functionName factor | factor `^` factor
            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    if (eat('+'))
                        x += parseTerm() // adição
                    else if (eat('-'))
                        x -= parseTerm() // subtração
                    else
                        return x
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    if (eat('*'))
                        x *= parseFactor() // multiplicação
                    else if (eat('/'))
                        x /= parseFactor() // divisão
                    else
                        return x
                }
            }

            fun parseFactor(): Double {
                if (eat('+')) return parseFactor() // + unário
                if (eat('-')) return -parseFactor() // - unário
                var x: Double
                val startPos = this.pos
                if (eat('(')) { // parênteses
                    x = parseExpression()
                    eat(')')
                } else if ((ch in '0'..'9') || ch == '.') { // números
                    while ((ch in '0'..'9') || ch == '.') nextChar()
                    x = java.lang.Double.parseDouble(str.substring(startPos, this.pos))
                } else if (ch in 'a'..'z') { // funções
                    while (ch in 'a'..'z') nextChar()
                    val func = str.substring(startPos, this.pos)
                    x = parseFactor()
                    if (func == "sqrt")
                        x = Math.sqrt(x)
                    else if (func == "sin")
                        x = Math.sin(Math.toRadians(x))
                    else if (func == "cos")
                        x = Math.cos(Math.toRadians(x))
                    else if (func == "tan")
                        x = Math.tan(Math.toRadians(x))
                    else
                        throw RuntimeException("Função desconhecida: " + func)
                } else {
                    throw RuntimeException("Caractere inesperado: " + ch.toChar())
                }
                if (eat('^')) x = Math.pow(x, parseFactor()) // potência
                return x
            }
        }.parse()
    }
}
