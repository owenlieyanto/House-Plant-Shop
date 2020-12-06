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
import kotlinx.android.synthetic.main.fragment_history.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var histories: ArrayList<History> = ArrayList()
    var v: View? = null

    fun updateList() {
        val layout = LinearLayoutManager(activity)
        view?.findViewById<RecyclerView>(R.id.recyclerViewHistory)?.let {
            it.layoutManager = layout
            it.setHasFixedSize(true)
            it.adapter = HistoryCardAdapter(histories)
        }
    }

    fun reloadPage() {
        histories.clear()
        val q = Volley.newRequestQueue(activity)
        val id = (Global.customer.id).toString()
        val url = "http://ubaya.prototipe.net/nmp160418081/orderhistory.php?id=${Global.customer.id}"
        Log.d("url", url )
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
                            histories.add(
                                History(
                                    getInt("id"),
                                    getInt("total"),
                                    getString("orderDate"),
                                    getInt("order_count"),
                                    getInt("order_sum")
                                )
                            )
                        }
                    }
                    updateList()
                    Log.d("apiresult", histories.toString())
                }
                else if (obj.getString("result") == "KOSONG")
                {

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
//        reloadPage()

    }

    override fun onResume() {
        super.onResume()
        reloadPage()
        if (histories.count() == 0) {
            textHistoryInfo.text = "history kosong."
        }
        else
        {
            textHistoryInfo.text = ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}