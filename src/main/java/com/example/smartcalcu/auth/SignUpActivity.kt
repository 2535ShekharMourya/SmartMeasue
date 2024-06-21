package com.example.smartcalcu.auth

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.smartcalcu.MainActivity
import com.example.smartcalcu.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SignUpActivity : AppCompatActivity() {
    lateinit var Result:String
    private lateinit var mAuth: FirebaseAuth
    private lateinit var edtName: TextInputEditText
    private lateinit var edtEmail: TextInputEditText
  /*  private lateinit var dow: TextInputEditText
    private lateinit var height: TextInputEditText
    private lateinit var weight: TextInputEditText*/

    private lateinit var mDbRef: DatabaseReference
    private lateinit var edtPassword: TextInputEditText
    private lateinit var btnSignup: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mAuth = FirebaseAuth.getInstance()
        edtName = findViewById(R.id.edt_user)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnSignup = findViewById(R.id.btn_signup)
       /* dow=findViewById(R.id.edt_dateOfW)
        height=findViewById(R.id.Hight)
        weight=findViewById(R.id.Weight)*/
/*
        dow.setOnClickListener {
            showDatePickerDialog()
        }*/

        btnSignup.setOnClickListener {
            val name=edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
          /*  val height=height.text.toString()
            val weight=weight.text.toString()
            val dow=*/
            signup(name,email, password)
        }
    }
    private fun signup(name:String,email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                     addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent= Intent(this@SignUpActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "some error Occurs", Toast.LENGTH_SHORT).show()
                }
            }


    }
    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef= FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }
    private fun showDatePickerDialog():String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            // Months are indexed from 0 in DatePickerDialog, so add 1
            val selectedDate = Calendar.getInstance()
            selectedDate.set(selectedYear, selectedMonth, selectedDay)

            // Format the date
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)

            // Set the formatted date to your TextView or EditText
            Result=  formattedDate
        }, year, month, day)

        datePickerDialog.show()
        return Result
    }
}
