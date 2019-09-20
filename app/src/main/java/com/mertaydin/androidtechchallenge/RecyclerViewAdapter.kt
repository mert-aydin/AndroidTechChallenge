package com.mertaydin.androidtechchallenge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.orders_list_item.view.*

class RecyclerViewAdapter(private val orderList: List<Order>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    companion object {
        private var monthNames: HashMap<String, String> = HashMap()
    }

    init {
        monthNames["01"] = "Ocak"
        monthNames["02"] = "Şubat"
        monthNames["03"] = "Mart"
        monthNames["04"] = "Nisan"
        monthNames["05"] = "Mayıs"
        monthNames["06"] = "Haziran"
        monthNames["07"] = "Temmuz"
        monthNames["08"] = "Ağustos"
        monthNames["09"] = "Eylül"
        monthNames["10"] = "Ekim"
        monthNames["11"] = "Kasım"
        monthNames["12"] = "Aralık"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.orders_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(orderList[position])
        holder.itemView.setOnClickListener {
            holder.itemView.rv_order_details.visibility =
                if (holder.itemView.rv_order_details.visibility == View.VISIBLE)
                    View.GONE
                else
                    View.VISIBLE

            if (position + 1 == itemCount) {
                OrdersActivity.scrollToBottom()
            }
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val context: Context = itemView.context

        fun bindItems(order: Order) {
            itemView.rv_day_tv.text = order.date
            itemView.rv_month_tv.text = monthNames[order.month]
            itemView.rv_market_name_tv.text = order.marketName
            itemView.rv_order_name_tv.text = order.orderName
            when {
                order.productState == context.getString(R.string.on_delivery) -> {
                    itemView.rv_product_state_icon.setBackgroundResource(R.drawable.ic_on_delivery)
                    itemView.rv_product_state_tv.text = context.getString(R.string.on_delivery)
                    itemView.rv_product_state_tv.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorOnDelivery
                        )
                    )
                }
                order.productState == context.getString(R.string.preparing) -> {
                    itemView.rv_product_state_icon.setBackgroundResource(R.drawable.ic_preparing)
                    itemView.rv_product_state_tv.text = context.getString(R.string.preparing)
                    itemView.rv_product_state_tv.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorPreparing
                        )
                    )
                }
                else -> {
                    itemView.rv_product_state_icon.setBackgroundResource(R.drawable.ic_awaiting_approval)
                    itemView.rv_product_state_tv.text =
                        context.getString(R.string.awaiting_approval)
                    itemView.rv_product_state_tv.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorAwaitingApproval
                        )
                    )
                }
            }

            itemView.rv_product_price_tv.text =
                context.getString(R.string.tl, order.productPrice.toString())
            itemView.order_detail_tv.text = order.productDetail.orderDetail
            itemView.summary_price_tv.text =
                context.getString(R.string.tl, order.productDetail.summaryPrice.toString())
        }
    }

}