package id.ac.ubaya.onlensop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.json.JSONObject

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val product_id = intent.getIntExtra(ProductAdapter.PRODUCT_ID, 0)

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

                textViewCategory.text = product.category
                textHargaDetail.text = product.price.toString()
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
    }
}