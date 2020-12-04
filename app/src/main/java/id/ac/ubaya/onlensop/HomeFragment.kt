package id.ac.ubaya.onlensop

import android.graphics.Insets.add
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.product_card_layout.view.*
import org.json.JSONObject
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARR_PRODUCTS = "arrayproducts"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var Products: ArrayList<Product> = ArrayList()

    var v: View? = null
    fun updateList() {
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        var recyclerView = v?.findViewById<RecyclerView>(R.id.productView)
        recyclerView?.layoutManager = lm
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = ProductAdapter(Products)
    }

    fun bacadata(){
        val q = Volley.newRequestQueue(activity)
        val url = "http://ubaya.prototipe.net/nmp160418081/homeproduct.php"
        var stringRequest = StringRequest(
            Request.Method.POST, url,
            {
                Log.d("cekapihome", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")

                    for (i in 0 until data.length()) {
                        val playObj = data.getJSONObject(i)
                        val product = Product(
                            playObj.getInt("id"),
                            playObj.getString("name"),
                            playObj.getString("description"),
                            playObj.getInt("price"),
                            playObj.getString("image"),
                            playObj.getInt("stock"),
                            playObj.getInt("categories_id"),
                        )
                        Products.add(product)
                    }
                    updateList()
                    Log.d("cekisiarray", Products.toString())
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            })
        q.add(stringRequest)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bacadata();
        //products = arguments!!.getSerializable(ARR_PRODUCTS) as ArrayList<Product>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_home, container, false)
        var v = inflater.inflate(R.layout.product_card_layout, productView, false)
        return v
        bacadata();
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(prod:ArrayList<Product>) =
            HomeFragment().apply {
                arguments = Bundle().apply {
//                    putSerializable(ARR_PRODUCTS, prod as Serializable)
                }
            }
    }


}