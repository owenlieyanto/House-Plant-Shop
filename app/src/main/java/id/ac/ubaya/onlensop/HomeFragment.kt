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
import kotlinx.android.synthetic.main.fragment_home.*
//import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    var products: ArrayList<Product> = ArrayList()
    var v: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val q = Volley.newRequestQueue(activity)
        val url = "http://ubaya.prototipe.net/nmp160418081/homeproduct.php"
        val stringRequest = StringRequest(
            Request.Method.POST,
            url,
            {
                Log.d("cekapihome", it)

                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")

                    for (i in 0 until data.length()) {
                        val playObj = data.getJSONObject(i)

                        with(playObj) {
                            products.add(
                                Product(
                                    getInt("id"),
                                    getString("name"),
                                    getString("description"),
                                    getInt("price"),
                                    getString("image"),
                                    getInt("stock"),
                                    getString("category_name")
                                )
                            )
                        }
                    }
                    updateList()
                    Log.d("cekisiarray", products.toString())
                }
            },
            {
                Log.e("apiresult", it.toString())
            }
        )
        q.add(stringRequest)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //disini masukkan code tambahkan cart
    }

    override fun onResume() {
        super.onResume()

        textHomeBalance.text = Global.customer.wallet.toString()
    }

    private fun updateList() {

        val layout = LinearLayoutManager(activity)
        view?.findViewById<RecyclerView>(R.id.productsView)?.let {
            it.layoutManager = layout
            it.setHasFixedSize(true)
            //it.adapter = PlaylistAdapter(playlists, activity!!.applicationContext)
            it.adapter = HomeCardAdapter(products)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
//                    putSerializable(ARR_PRODUCTS, prod as Serializable)
                }
            }
    }


}