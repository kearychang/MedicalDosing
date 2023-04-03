package com.bignerdranch.android.medicaldosing

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.medicaldosing.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var age: Age = Age.NEONATE
    private var weight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        WEIGHT FIELD EVENT
        binding.textFieldWeight.setOnEditorActionListener { textView, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                // Call the code to hide the keyboard
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(textView.windowToken, 0)
                // Handle the user's input here
                true // Return true to indicate that the event has been handled
            } else {
                false // Return false to indicate that the event has not been handled
            }
        }

        binding.textFieldWeight.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called to notify you that, within s, the count characters beginning at start are about to be replaced by new text with length after.
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called to notify you that, within s, the count characters beginning at start have just replaced old text that had length before.
            }
            override fun afterTextChanged(s: Editable?) {
                // This method is called to notify you that, somewhere within s, the text has been changed.
                // You can use this method to trigger a callback after the user enters a new value.
                try {
                    weight = binding.textFieldWeight.text.toString().toInt()
                    Log.d(TAG, "Weight set to $weight")
                } catch (e: java.lang.Exception) {
                }
            }
        })

//        SELECT NEW AGE DROPDOWN EVENT
        binding.ages.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                age = Age.values().first { it.value == position }
                Log.d(TAG, "Age set to $age")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // does nothing
            }
        }

//        GET DOSING BUTTON EVENT
        val tylenolDosingArr = resources.getStringArray(R.array.tylenolDosingResults)
        binding.dosingBtn.setOnClickListener {
            // force hide virtual keyboard
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            val tylenolDosingText:String = when(age) {
                Age.NEONATE -> (15*weight).toString() + tylenolDosingArr[0]
                Age.INFANT -> (15*weight).toString() + tylenolDosingArr[1]
                Age.ADOLESCENT ->
                    if (weight > 50) {
                        tylenolDosingArr[3]
                    } else {
                        (15*weight).toString() + tylenolDosingArr[2]
                    }
                Age.ADULT -> tylenolDosingArr[3]
            }
            binding.tylenolDosingText.text = tylenolDosingText
            Log.d(TAG, "Get Dosing")
        }
    }
}