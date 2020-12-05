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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.json.JSONObject
import java.math.RoundingMode
import java.text.DecimalFormat

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {

    var carts: ArrayList<Cart> = ArrayList()
    var v: View? = null

    fun updateList() {

        val layout = LinearLayoutManager(activity)
        view?.findViewById<RecyclerView>(R.id.recyclerViewCarts)?.let {
            it.layoutManager = layout
            it.setHasFixedSize(true)
            it.adapter = CartCardAdapter(carts)
        }
    }

    fun reloadPage() {
        carts.clear()

        val q = Volley.newRequestQueue(activity)
        val url = "http://ubaya.prototipe.net/nmp160418081/usercart.php?id=${Global.customer.id}"
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                Log.d("apiresult", it)

                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")

                    for (i in 0 until data.length()) {
                        val playObj = data.getJSONObject(i)

                        with(playObj) {
                            carts.add(
                                Cart(
                                    getString("name"),
                                    getInt("price"),
                                    getString("image"),
                                    getInt("quantity")
                                )
                            )
                        }
                    }
                    updateList()
                    Log.d("apiresult", carts.toString())
                }
            },
            {
                Log.e("apiresult", it.toString())
            }
        )
        q.add(stringRequest)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reloadPage()
    }

    override fun onResume() {
        super.onResume()

        reloadPage()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }


    companion object {
        fun newInstance() =
            CartFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}