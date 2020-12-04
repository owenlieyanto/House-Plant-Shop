package id.ac.ubaya.onlensop

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_card_layout.view.*


class ProductAdapter(val products: ArrayList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    companion object {
        val PRODUCT_ID = "produk"
    }

    class ProductViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.product_card_layout, parent, false)
        //view.textNamaProduk.setText("Haha")
        return ProductViewHolder(view)

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val products = products[position]

        with(holder.view) {
            Picasso.get().load(products.image).into(imageProduk)
            textHarga.text = products.price.toString()
            textNamaProduk.text = products.name
            textDescProd.text = products.desc

            cardViewProduct.setOnClickListener {
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra(PRODUCT_ID, products.id)
                context.startActivity(intent)
            }

            // TODO: cart feature
            buttonAddToCart.setOnClickListener {
                Toast.makeText(context, "Produk ditambahakan ke cart", Toast.LENGTH_SHORT).show()
            }
        }

        val img = "http://ubaya.prototipe.net/nmp160418081/image/" + products.image
        Picasso.get().load(img).into(holder.view.imageProduk)

    }

    override fun getItemCount() = products.size
}