package id.ac.ubaya.onlensop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonSignIn.setOnClickListener {
            val email = textInputLoginEmail.text.toString()
            val password = textInputLoginPassword.text.toString()

            if (email != "" && password != "") {
                Toast.makeText(this, "Email: $email\nPassword: $password", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Email dan Password wajib diisi.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}