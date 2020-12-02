package id.ac.ubaya.onlensop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        buttonSignUp.setOnClickListener {
            val email = textInputRegisterEmail.text.toString()
            val nama = textInputRegisterNama.text.toString()
            val password = textInputRegisterPassword.text.toString()
            val rePassword = textInputRegisterRetypePassword.text.toString()

            if (email != "" && nama != "" && password != "" && rePassword != "") {
                if (password == rePassword) {
                    Toast.makeText(
                        this,
                        "Email: $email\nNama: $nama\nPassword: $password",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "Password & retype password belum sama.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Gagal. Pastikan semua data diatas sudah terisi.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}