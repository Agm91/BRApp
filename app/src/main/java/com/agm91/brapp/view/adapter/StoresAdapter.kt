package com.agm91.brapp.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.agm91.brapp.BR
import com.agm91.brapp.R
import com.agm91.brapp.databinding.ItemStoresBinding
import com.agm91.brapp.model.Store
import com.agm91.brapp.model.Stores
import com.agm91.brapp.view.activity.GenericActivity
import com.agm91.brapp.view.fragment.ARG_STORE

class StoresAdapter : RecyclerView.Adapter<StoresAdapter.ViewHolder>() {
    private var data = listOf<Store>()

    fun setData(stores: Stores) {
        this.data = stores.stores
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemStoresBinding>(
                layoutInflater,
                R.layout.item_stores,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(private val binding: ItemStoresBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(store: Store) {
            with(binding) {
                setVariable(BR.store, store)
                binding.root.setOnClickListener {
                    val context = binding.root.context
                    val intent = Intent(context, GenericActivity::class.java)
                    intent.apply {
                        putExtra(ARG_STORE, store)
                    }
                    context.startActivity(intent)
                }
                executePendingBindings()
            }
        }
    }
}