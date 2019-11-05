package com.agm91.brapp.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.agm91.brapp.BR
import com.agm91.brapp.R
import com.agm91.brapp.databinding.FragmentStoreBinding
import com.agm91.brapp.model.Store

const val ARG_STORE = "store"

class StoreFragment : Fragment() {
    private var store: Store? = null
    private var binding: FragmentStoreBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            store = it.get(ARG_STORE) as Store?
        }

        val actionbar = (activity as AppCompatActivity).supportActionBar
        actionbar?.let {
            actionbar.apply {
                title = store?.name
                setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false)
        bind()

        return binding?.root
    }

    private fun bind() {
        binding?.setVariable(BR.store, store)
        binding?.setVariable(BR.handlers, Handlers())
        binding?.executePendingBindings()
    }

    inner class Handlers {
        fun onClickAddress(view: View) {
            val gmmIntentUri = Uri.parse("geo:" + store?.latitude + "," + store?.longitude)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(activity?.packageManager) != null) {
                startActivity(mapIntent)
            }
        }

        fun onClickPhone(view: View) {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + store?.phone))
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) =
            StoreFragment().apply {
                arguments = bundle
            }
    }
}
