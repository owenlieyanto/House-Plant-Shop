package id.ac.ubaya.onlensop

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_card_layout.view.*


class ProductAdapter (val products: ArrayList<Product>): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){
    class ProductViewHolder(val view: View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.product_card_layout, parent, false)
        //view.textNamaProduk.setText("Haha")
        return ProductViewHolder(view)

    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val products = products[position]

        with(holder.view){
            Picasso.get().load(products.image).into(imageProduk)
            textHarga.text = products.price.toString()
            textNamaProduk.text = products.name
            textDescProd.text = products.desc
        }

        val img= "http://ubaya.prototipe.net/nmp160418081/image/" + products.image
        Picasso.get().load(img).into(holder.view.imageProduk)

    }

    override fun getItemCount()= products.size
}