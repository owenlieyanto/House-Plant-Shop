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

                                // Langsung arahkan ke home
                                val q2 = Volley.newRequestQueue(this)
                                val url2 = "http://ubaya.prototipe.net/nmp160418081/login.php"
                                val stringRequest2 = object : StringRequest(
                                    Request.Method.POST, url2,
                                    {
                                        Log.d("apiresult", it)
                                        val obj2 = JSONObject(it)

                                        if (obj2.getString("result") == "OK") {
                                            val data = obj2.getJSONArray("data")

                                            val idLogin = data.getJSONObject(0).getInt("id")
                                            val emailLogin = data.getJSONObject(0).getString("email")
                                            val namaLogin = data.getJSONObject(0).getString("nama")
                                            val passwordLogin =
                                                data.getJSONObject(0).getString("password")
                                            val wallet = data.getJSONObject(0).getString("wallet")

                                            val customer =
                                                Customer(idLogin, emailLogin, namaLogin, passwordLogin, wallet.toInt())

                                            val intent = Intent(this, MainActivity::class.java)
                                            Global.customer = customer
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
                                        params["password"] = password

                                        return params
                                    }
                                }
                                q2.add(stringRequest2)

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