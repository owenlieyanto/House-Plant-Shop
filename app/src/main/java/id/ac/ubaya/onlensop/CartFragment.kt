package id.ac.ubaya.onlensop

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_cart.*
import org.json.JSONObject

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
    var totalCart = 0;

    fun countTotal2() {
        totalCart = 0
        for (cart in carts) {
            totalCart += (cart.quantity * cart.price)
            Log.d("subtotal", "subtotal cart $cart = ${(cart.quantity * cart.price)}")
        }
        Log.d("total", totalCart.toString())
        textTotalCart.text = totalCart.toString()
    }

    fun updateList() {
        val layout = LinearLayoutManager(activity)
        view?.findViewById<RecyclerView>(R.id.recyclerViewCarts)?.let {
            it.layoutManager = layout
            it.setHasFixedSize(true)
            it.adapter = CartCardAdapter(carts, this)
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
                                    getInt("product_id"),
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
                countTotal2()
            },
            {
                Log.e("apiresult", it.toString())
            }
        )
        q.add(stringRequest)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        reloadPage()

        buttonCheckoutCart.setOnClickListener {
            if (carts.size != 0) {
                if (totalCart > Global.customer.wallet) {

                    val builder = AlertDialog.Builder(activity!!)
                    builder.setMessage("Saldo anda tidak cukup untuk memesan ini, silahkan kurangi isi cart atau lakukan top-up saldo.")
                    builder.setPositiveButton("Anjay") { dialogInterface: DialogInterface, i: Int -> }
                    builder.show()
                } else {
                    val q = Volley.newRequestQueue(activity)
                    val url =
                        "http://ubaya.prototipe.net/nmp160418081/checkout.php?id=${Global.customer.id}&total=$totalCart&wallet=${Global.customer.wallet}"
                    val stringRequest = StringRequest(
                        Request.Method.GET,
                        url,
                        {
                            Log.d("url checkout", url)

                            val obj = JSONObject(it)
                            if (obj.getString("result") == "OK") {
                                carts.clear()
                                updateList()

                                val builder = AlertDialog.Builder(activity!!)
                                builder.setMessage("Selamat! Anda berhasil melakukan pemesanan!")
                                builder.setPositiveButton("Mantap") { dialogInterface: DialogInterface, i: Int -> }
                                builder.show()

                                Global.customer.wallet -= totalCart
                                reloadPage()
                            }
                        },
                        {
                            Log.e("apiresult", it.toString())
                        }
                    )
                    q.add(stringRequest)

                }
            } else {
                val builder = AlertDialog.Builder(activity!!)
                builder.setMessage("Anda belum memilih barang.")
                builder.setPositiveButton("Saya paham.") { dialogInterface: DialogInterface, i: Int -> }
                builder.show()
            }
        }
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