package id.ac.ubaya.onlensop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

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
                    val q = Volley.newRequestQueue(this)
                    val url = "http://ubaya.prototipe.net/nmp160418081/register.php"
                    var stringRequest = object : StringRequest(
                        Request.Method.POST, url,
                        {
                            Log.d("apiresult", it)
                            val obj = JSONObject(it)
                            val pesan = obj.getString("message")
                            Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show()

                            if (obj.getString("result") == "OK") {
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        },
                        {
                            Log.e("apiresult", it.message.toString())
                        }
                    ) {
                        override fun getParams(): MutableMap<String, String> {
                            val params = HashMap<String, String>()
                            params["email"] = email
                            params["nama"] = nama
                            params["password"] = password

                            return params
                        }
                    }
                    q.add(stringRequest)
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