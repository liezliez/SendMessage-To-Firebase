package com.liez.kirimpesan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Declare View Text and Button
    private lateinit var txtMessage : EditText
    private lateinit var btnSend : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the Declared View
        txtMessage = findViewById(R.id.et_message)
        btnSend = findViewById(R.id.btn_save)
        btnSend.setOnClickListener(this)
    }

    // When button click -> send the data to Firebase
    override fun onClick(p0: View?) {
        saveData()
    }

    // Function for sending the Data
    private fun saveData(){
        val nama = txtMessage.text.toString().trim()

        // if the input is empty, set the error feedback
        if (nama.isEmpty()){
            txtMessage.error = "Message cannot be emmpty"
            return
        }

        val databaseURL = "https://pesan-ef55f-default-rtdb.asia-southeast1.firebasedatabase.app"
        // setting up the firebase database reference
        val ref = FirebaseDatabase.getInstance(databaseURL).getReference("Message")

        // make id for each data sent to database
        val usrId = ref.push().key

        // make object using the data
        val usr = Message(usrId,nama)

        // if data sent succesfully
        if (usrId != null){
            ref.child(usrId).setValue(usr).addOnCompleteListener {
                // make a toast notification that the data has been sent
                Toast.makeText(applicationContext, "Message Sent Successfully", Toast.LENGTH_SHORT).show()
                txtMessage.setText("")
            }
        }
    }
}