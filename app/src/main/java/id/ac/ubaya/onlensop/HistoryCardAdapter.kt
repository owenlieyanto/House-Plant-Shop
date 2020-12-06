package id.ac.ubaya.onlensop

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.cart_card_layout.view.*
import kotlinx.android.synthetic.main.history_card_layout.view.*
import org.json.JSONObject

class HistoryCardAdapter(var histories: ArrayList<History>) :
    RecyclerView.Adapter<HistoryCardAdapter.HistoryViewHolder>(){

    class HistoryViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryCardAdapter.HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.history_card_layout, parent, false)
        return HistoryCardAdapter.HistoryViewHolder(view)
    }
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        var histories = histories[position]
        with(holder.view) {
            textHistoryCountQuantity.text = histories.order_count.toString()
            textHistoryOrderId.text = histories.id.toString()
            textHistoryDate.text = histories.order_date.toString()
            textHistorySumQuantity.text = histories.order_sum.toString()
            textHistoryPrice.text = histories.total.toString()
        }
    }
    override fun getItemCount() = histories.size
}