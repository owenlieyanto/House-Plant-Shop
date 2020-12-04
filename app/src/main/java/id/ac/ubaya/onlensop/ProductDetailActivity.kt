package id.ac.ubaya.onlensop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.product_card_layout.*
import kotlinx.android.synthetic.main.product_card_layout.view.*

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val product = intent.getSerializableExtra(ProductAdapter.PRODUCT) as Product

        textHargaDetail.text = product.price.toString()
        textNamaDetail.text = product.name
        textDescDetail.text = product.desc

        val img = "http://ubaya.prototipe.net/nmp160418081/image/" + product.image
        Picasso.get().load(img).into(imageViewDetilGambar)
    }
}