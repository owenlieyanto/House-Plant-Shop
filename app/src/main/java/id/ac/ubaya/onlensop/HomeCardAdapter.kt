package id.ac.ubaya.onlensop

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.home_card_layout.view.*
import org.json.JSONObject
import java.math.RoundingMode
import java.text.DecimalFormat


class HomeCardAdapter(val products: ArrayList<Product>) :
    RecyclerView.Adapter<HomeCardAdapter.ProductViewHolder>() {

    companion object {
        val PRODUCT_ID = "produk"
    }

    class ProductViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.home_card_layout, parent, false)
        //view.textNamaProduk.setText("Haha")
        return ProductViewHolder(view)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val df = DecimalFormat("#,###")
        df.roundingMode = RoundingMode.CEILING

        val products = products[position]

        with(holder.view) {
            Picasso.get().load(products.image).into(imageProduk)
            textHarga.text = df.format(products.price).toString()
            textNamaProduk.text = products.name.take(15) + "…"
            textDescProd.text = products.desc.take(75) + "…"

            cardViewProduct.setOnClickListener {
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra(PRODUCT_ID, products.id)
                context.startActivity(intent)
            }

            buttonAddToCart.setOnClickListener {
                val q = Volley.newRequestQueue(context)
                val url =
                    "http://ubaya.prototipe.net/nmp160418081/updateCart.php?" +
                            "customers_id=${Global.customer.id}&" +
                            "products_id=${products.id}&" +
                            "quantity=1"
                val stringRequest = StringRequest(
                    Request.Method.GET,
                    url,
                    {
                        Log.d("apiresult", it)

                        val obj = JSONObject(it)
                        if (obj.getString("result") == "OK") {
                            val message = obj.getString("message")

                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    },
                    {
                        Log.e("apiresult", it.toString())
                    }
                )
                q.add(stringRequest)

                // TODO: refresh CartFragment (update)

            }
        }

        val img = "http://ubaya.prototipe.net/nmp160418081/image/" + products.image
        Picasso.get().load(img).into(holder.view.imageProduk)

    }

    override fun getItemCount() = products.size
}