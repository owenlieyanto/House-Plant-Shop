package id.ac.ubaya.onlensop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.json.JSONObject
import java.math.RoundingMode
import java.text.DecimalFormat

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        setSupportActionBar(toolbar)

        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        collapsingToolbar.title = ""
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()

        val product_id = intent.getIntExtra(HomeCardAdapter.PRODUCT_ID, 0)

        val q = Volley.newRequestQueue(this)
        val url = "http://ubaya.prototipe.net/nmp160418081/detailproduct.php?id=$product_id"
        var stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)

                val data = obj.getJSONArray("data")

                val id = data.getJSONObject(0).getInt("id")
                val name = data.getJSONObject(0).getString("name")
                val description = data.getJSONObject(0).getString("description")
                val price = data.getJSONObject(0).getInt("price")
                val image = data.getJSONObject(0).getString("image")
                val stock = data.getJSONObject(0).getInt("stock")
                val category_name = data.getJSONObject(0).getString("category_name")

                val product = Product(id, name, description, price, image, stock, category_name)

                val df = DecimalFormat("#,###")
                df.roundingMode = RoundingMode.CEILING

                textViewCategory.text = product.category
                textHargaDetail.text = df.format(product.price).toString()
                textNamaDetail.text = product.name
                textDescDetail.text = product.desc

                val img = "http://ubaya.prototipe.net/nmp160418081/image/" + product.image
                Picasso.get().load(img).into(imageViewDetilGambar)
            },
            {
                Log.e("apiresult", it.message.toString())
            }
        )
        q.add(stringRequest)

        buttonAddToCartDetail.setOnClickListener {
            val q2 = Volley.newRequestQueue(this)
            val url2 =
                "http://ubaya.prototipe.net/nmp160418081/updateCart.php?" +
                        "customers_id=${Global.customer.id}&" +
                        "products_id=${product_id}&" +
                        "quantity=1"
            val stringRequest2 = StringRequest(
                Request.Method.GET,
                url2,
                {
                    Log.d("apiresult", it)

                    val obj = JSONObject(it)
                    if (obj.getString("result") == "OK") {
                        val message = obj.getString("message")

                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                },
                {
                    Log.e("apiresult", it.toString())
                }
            )
            q2.add(stringRequest2)
        }
    }
}