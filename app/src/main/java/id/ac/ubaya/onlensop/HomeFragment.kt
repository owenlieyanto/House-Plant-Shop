package id.ac.ubaya.onlensop

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
//import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.product_card_layout.view.*
import org.json.JSONObject

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

    var products: ArrayList<Product> = ArrayList()
    var v: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val q = Volley.newRequestQueue(activity)
        val url = "http://ubaya.prototipe.net/nmp160418081/homeproduct.php"
        val stringRequest = StringRequest(
            Request.Method.POST,
            url,
            Response.Listener {
                Log.d("cekapihome", it)

                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")

                    for (i in 0 until data.length()) {
                        val playObj = data.getJSONObject(i)

                        with(playObj){
                            products.add(
                                Product(
                                    getInt("id"),
                                    getString("name"),
                                    getString("description"),
                                    getInt("price"),
                                    getString("image"),
                                    getInt("stock"),
                                    getInt("categories_id")
                                )
                            )
                        }
                    }
                    updateList()
                    Log.d("cekisiarray", products.toString())
                }
            },
            Response.ErrorListener {
                Log.e("apiresult", it.toString())
            }
        )
        q.add(stringRequest)
        //products = arguments!!.getSerializable(ARR_PRODUCTS) as ArrayList<Product>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_home, container, false)
        v = inflater.inflate(R.layout.fragment_home, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //disini masukkan code tambahkan cart
    }

    fun updateList(){ /*
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        var rv = v?.findViewById<RecyclerView>(R.id.playlistView)
        rv?.let {
            it.layoutManager = lm
            it.setHasFixedSize(true)
            it.adapter = PlaylistAdapter(playlists)
        }*/

        val layout = LinearLayoutManager(activity)
        view?.findViewById<RecyclerView>(R.id.productsView)?.let {
            it.layoutManager = layout
            it.setHasFixedSize(true)
            //it.adapter = PlaylistAdapter(playlists, activity!!.applicationContext)
            it.adapter = ProductAdapter(products)
        }
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