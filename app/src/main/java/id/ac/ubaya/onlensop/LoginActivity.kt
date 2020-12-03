package id.ac.ubaya.onlensop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    val CUSTOMER = "customer"

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

                            var email = data.getJSONObject(0).getString("email")
                            var nama = data.getJSONObject(0).getString("nama")
                            var password = data.getJSONObject(0).getString("password")
                            var wallet = data.getJSONObject(0).getString("wallet")

                            val customer = Customer(email, nama, password, wallet.toInt())

                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra(CUSTOMER, customer)
                            startActivity(intent)
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
                Toast.makeText(this, "Email dan Password wajib diisi.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}