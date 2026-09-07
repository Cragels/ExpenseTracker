package com.example.expensetracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var expenseDao: ExpenseDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtDescriptions = findViewById<EditText>(R.id.edtDescriptions)
        val edtCategories = findViewById<EditText>(R.id.edtCategories)
        val edtAmount = findViewById<EditText>(R.id.edtAmount)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnLoad = findViewById<Button>(R.id.btnLoad)
        val tvExpenses = findViewById<TextView>(R.id.tvExpenses)

        expenseDao = AppDatabase
            .getDatabase(applicationContext)
            .expenseDao()


        btnSave.setOnClickListener {
            val description = edtDescriptions.text.toString().trim()
            val category = edtCategories.text.toString().trim()
            val amount = edtAmount.text.toString().toDoubleOrNull()

            if (description.isBlank()) {
                edtDescriptions.error = "Enter a description"
                return@setOnClickListener
            }

            if (category.isBlank()) {
                edtCategories.error = "Enter a category"
                return@setOnClickListener
            }

            if (amount == null || amount <= 0) {
                edtAmount.error = "Enter a valid amount"
                return@setOnClickListener
            }

            val expense = Expense(
                description = description,
                category = category,
                amount = amount
            )

            lifecycleScope.launch {
                expenseDao.insert(expense)

                Toast.makeText(
                    this@MainActivity,
                    "Expense saved",
                    Toast.LENGTH_SHORT
                ).show()

                edtDescriptions.text.clear()
                edtCategories.text.clear()
                edtAmount.text.clear()
            }

        }





    }


}
