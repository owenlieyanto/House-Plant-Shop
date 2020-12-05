package id.ac.ubaya.onlensop

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONArray
import org.json.JSONObject

@Suppress("NAME_SHADOWING")
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonSignIn.setOnClickListener {
            val email = textInputLoginEmail.text.toString()
            val password = textInputLoginPassword.text.toString()

            if (email != "" && password != "") {
                val q = Volley.newRequestQueue(this)
                val url = "http://ubaya.prototipe.net/nmp160418081/login.php"
                var stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    {
                        Log.d("apiresult", it)
                        val obj = JSONObject(it)

                        if (obj.getString("result") == "OK") {
                            val data = obj.getJSONArray("data")

                            val id = data.getJSONObject(0).getInt("id")
                            val email = data.getJSONObject(0).getString("email")
                            val nama = data.getJSONObject(0).getString("nama")
                            val password = data.getJSONObject(0).getString("password")
                            val wallet = data.getJSONObject(0).getString("wallet")

                            val customer = Customer(id, email, nama, password, wallet.toInt())

                            val intent = Intent(this, MainActivity::class.java)
                            Global.customer = customer
                            startActivity(intent)
                            finish()
                        } else {
                            val pesan = obj.getString("message")
                            Snackbar.make(
                                contraintLayoutLogin,
                                pesan,
                                Snackbar.LENGTH_LONG
                            ).show()
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
                q.add(stringRequest)
            } else {
                //                Toast.makeText(this, "Email dan Password wajib diisi.", Toast.LENGTH_SHORT).show()
                Snackbar.make(
                    contraintLayoutLogin,
                    "Email dan Password wajib diisi.",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}