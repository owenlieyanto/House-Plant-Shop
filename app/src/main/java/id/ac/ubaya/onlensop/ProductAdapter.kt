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


class ProductAdapter (val products: ArrayList<Product>):
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){
    class ProductViewHolder(val v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.product_card_layout, parent, false)
        v.textNamaProduk.setText("Haha")
        return ProductViewHolder(v)

    }
    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int)
    {
//        val img= "image/" + products[position].image
        val img= "http://ubaya.prototipe.net/nmp160418081/image/1.jpg"
        Picasso.get().load(img).into(holder.v.imageProduk)
//        holder.v.textNamaProduk.text = products[position].name
        holder.v.textNamaProduk.text = "HIHIHIHIHI"
        Log.d("masuk onbind", "pipi")
    }
}