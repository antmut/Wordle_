package com.example.wordle_

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var wordToGuess = FourLetterWordList.getRandomFourLetterWord()
    private var counter = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordToGuess = FourLetterWordList.getRandomFourLetterWord() // pick a random word
        // declarations
        val a = "Guess #1                              "
        val a1 = "Guess #1 check               "
        val b = "Guess #2                           "
        val b1 = "Guess #2 check               "
        val c = "Guess #3                           "
        val c1 = "Guess #3 check               "

        val guess1 = findViewById<TextView>(R.id.guess1)
        val guess1_check = findViewById<TextView>(R.id.guess1_check)
        val guess2 = findViewById<TextView>(R.id.guess2)
        val guess2_check = findViewById<TextView>(R.id.guess2_check)
        val guess3 = findViewById<TextView>(R.id.guess3)
        val guess3_check = findViewById<TextView>(R.id.guess3_check)

        val button = findViewById<Button>(R.id.button)
        val reset = findViewById<Button>(R.id.reset)
        var userInput = findViewById<EditText>(R.id.userInput) as EditText   // get the input
        var guessValue: String                          // store as a string
        val answer = findViewById<TextView>(R.id.answer)
        var match: String


        // Type your guess and press the Guess button
        button.setOnClickListener {
            hideKeyboard()

            if (counter == 0) {
                userInput = findViewById<EditText>(R.id.userInput) as EditText
                guessValue = userInput.text.toString()
                guess1.text = "$a $guessValue"
                match = checkGuess(guessValue.uppercase())
                guess1_check.text = "$a1     $match"
                guess1.visibility = View.VISIBLE
                guess1_check.visibility = View.VISIBLE
                userInput.setText("")   // clear input area

                if (guessValue == wordToGuess){
                    answer.text = wordToGuess           // show correct word
                    answer.visibility = View.VISIBLE
                    Toast.makeText(it.context, "That is correct!", Toast.LENGTH_SHORT).show()
                }
            }

            else if (counter == 1) {
                userInput = findViewById<EditText>(R.id.userInput) as EditText
                guessValue = userInput.text.toString()
                guess2.text = "$b     $guessValue"
                match = checkGuess(guessValue.uppercase())
                guess2_check.text = "$b1     $match"
                guess2.visibility = View.VISIBLE
                guess2_check.visibility = View.VISIBLE
                userInput.setText("")   // clear input area
            }

            else if (counter == 2) {
                userInput = findViewById<EditText>(R.id.userInput) as EditText
                guessValue = userInput.text.toString()
                guess3.text = "$c     $guessValue"
                match = checkGuess(guessValue.uppercase())
                guess3_check.text = "$c1     $match"
                guess3.visibility = View.VISIBLE
                guess3_check.visibility = View.VISIBLE
                userInput.setText("")               // clear input area

                answer.text = wordToGuess           // show correct word
                answer.visibility = View.VISIBLE
            }

            else {
                button.visibility = View.INVISIBLE
                reset.visibility = View.VISIBLE
                Toast.makeText(it.context, "Number of guesses exceeded!", Toast.LENGTH_SHORT).show()
            }

            counter++
            userInput.setText("")
        }

        reset.setOnClickListener(){
            counter = 0
            userInput.setText("")
            wordToGuess = FourLetterWordList.getRandomFourLetterWord()
            guess1.visibility = View.INVISIBLE
            guess1_check.visibility = View.INVISIBLE
            guess2.visibility = View.INVISIBLE
            guess2_check.visibility = View.INVISIBLE
            guess3.visibility = View.INVISIBLE
            guess3_check.visibility = View.INVISIBLE
            answer.visibility = View.INVISIBLE
            button.visibility = View.VISIBLE
            reset.visibility = View.INVISIBLE
        }
    }
    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String): String {
        var result = ""
        for (i in 0..3) {
            result += if (guess[i] == wordToGuess[i]) {
                "O"
            } else if (guess[i] in wordToGuess) {
                "+"
            } else {
                "X"
            }
        }
        return result
    }

    // function to hide the keyboard appearance
    fun hideKeyboard(){
        val view = this.currentFocus
        if (view != null){
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(view.windowToken, 0)
        }
        else{
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
    }
}
