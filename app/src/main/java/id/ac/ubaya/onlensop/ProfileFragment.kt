package id.ac.ubaya.onlensop

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONObject
import java.math.RoundingMode
import java.text.DecimalFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    fun updateProfileTexts() {

        textViewProfile.text = Global.customer.nama
        textInputProfileNama.setText(Global.customer.nama)
        textInputPasswordBaru.setText(Global.customer.password)
        textInputUlangPasswordBaru.setText(Global.customer.password)
        textInputProfileBalance.setText("")
        val df = DecimalFormat("#,###")
        df.roundingMode = RoundingMode.CEILING
        textProfileBalance.text = df.format(Global.customer.wallet).toString()
    }

    override fun onResume() {
        super.onResume()

        updateProfileTexts()

        buttonUpdateProfile.setOnClickListener {

            if (textInputPasswordBaru.text.toString() == textInputUlangPasswordBaru.text.toString()) {
                // TODO: pake api
                val namaBaru = textInputProfileNama.text.toString()
                val passwordBaru = textInputPasswordBaru.text.toString()

                val q = Volley.newRequestQueue(context)
                val url =
                    "http://ubaya.prototipe.net/nmp160418081/updateProfile.php"
                val stringRequest = object : StringRequest(
                    Method.POST,
                    url,
                    {
                        Log.d("apiresult", it)

                        val obj = JSONObject(it)
                        if (obj.getString("result") == "OK") {
                            val message = obj.getString("message")

                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                            Global.customer.nama = namaBaru
                            Global.customer.password = passwordBaru

                            updateProfileTexts()
                        }
                    },
                    {
                        Log.e("apiresult", it.toString())
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()

                        params["id"] = Global.customer.id.toString()
                        params["namaBaru"] = namaBaru
                        params["passwordBaru"] = passwordBaru

                        return params
                    }
                }
                q.add(stringRequest)
            } else {
                Toast.makeText(
                    context,
                    "Coba ulangi passwordnya. Masih belum sama tuh.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        buttonProfileTopup.setOnClickListener {
            var jumlahTopup = textInputProfileBalance?.text.toString().toInt()

            if (jumlahTopup > 0) {
                val q = Volley.newRequestQueue(context)
                val url =
                    "http://ubaya.prototipe.net/nmp160418081/topUp.php"
                val stringRequest = object : StringRequest(
                    Method.POST,
                    url,
                    {
                        Log.d("apiresult", it)

                        val obj = JSONObject(it)

                        if (obj.getString("result") == "OK") {
                            var message = obj.getString("message")

                            Global.customer.wallet += jumlahTopup

                            updateProfileTexts()
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                        }

                    },
                    {
                        Log.e("apiresult", it.toString())
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()

                        params["id"] = Global.customer.id.toString()
                        params["jumlahTopup"] = jumlahTopup.toString()

                        return params
                    }
                }
                q.add(stringRequest)
            }else{
                Toast.makeText(context, "Nominal harus diisi lebih dari 0", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}