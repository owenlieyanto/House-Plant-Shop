package id.ac.ubaya.onlensop

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_card_layout.view.*
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
            textHarga.text = df.format(carts.price).toString()
            textNamaProduk.text = carts.name.take(30) + "…"
            textJumlahCart.setText(carts.quantity.toString())

            // TODO: card cart di click
//            cardViewProduct.setOnClickListener {
//                val intent = Intent(context, ProductDetailActivity::class.java)
////                intent.putExtra(CartCardAdapter.PRODUCT_ID, products.id)
//                context.startActivity(intent)
//            }

            // TODO: buttonKurangCart

            // TODO: buttonTambahCart
        }

        val img = "http://ubaya.prototipe.net/nmp160418081/image/" + carts.image
        Picasso.get().load(img).into(holder.view.imageProduk)
    }

    override fun getItemCount() = carts.size
}