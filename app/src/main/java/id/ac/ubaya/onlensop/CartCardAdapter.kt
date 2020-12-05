package id.ac.ubaya.onlensop

import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.cart_card_layout.view.*
import org.json.JSONObject
import java.math.RoundingMode
import java.text.DecimalFormat

class CartCardAdapter(val carts: ArrayList<Cart>) :
    RecyclerView.Adapter<CartCardAdapter.ProductViewHolder>() {

    class ProductViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.cart_card_layout, parent, false)
        return ProductViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val df = DecimalFormat("#,###")
        df.roundingMode = RoundingMode.CEILING

        val carts = carts[position]

        with(holder.view) {
            textHarga.text = df.format(carts.price * carts.quantity).toString()
            textNamaProduk.text = carts.name.take(15) + "…"
            textJumlahCart.setText(carts.quantity.toString())

            buttonKurangCart.setOnClickListener {
                val q2 = Volley.newRequestQueue(context)
                val url2 =
                    "http://ubaya.prototipe.net/nmp160418081/updateCart.php?" +
                            "customers_id=${Global.customer.id}&" +
                            "products_id=${carts.product_id}&" +
                            "quantity=-1"
                val stringRequest2 = StringRequest(
                    Request.Method.GET,
                    url2,
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
                q2.add(stringRequest2)

                // TODO: refresh fragment parent

            }

            buttonTambahCart.setOnClickListener {
                val q2 = Volley.newRequestQueue(context)
                val url2 =
                    "http://ubaya.prototipe.net/nmp160418081/updateCart.php?" +
                            "customers_id=${Global.customer.id}&" +
                            "products_id=${carts.product_id}&" +
                            "quantity=1"
                val stringRequest2 = StringRequest(
                    Request.Method.GET,
                    url2,
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
                q2.add(stringRequest2)

                // TODO: refresh fragment parent

            }

            // TODO: card cart di click
//            cardViewProduct.setOnClickListener {
//                val intent = Intent(context, ProductDetailActivity::class.java)
////                intent.putExtra(CartCardAdapter.PRODUCT_ID, products.id)
//                context.startActivity(intent)
//            }
        }

        val img = "http://ubaya.prototipe.net/nmp160418081/image/" + carts.image
        Picasso.get().load(img).into(holder.view.imageProduk)
    }

    override fun getItemCount() = carts.size
}