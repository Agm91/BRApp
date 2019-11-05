package com.agm91.brapp.view.activity

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.agm91.brapp.R
import com.agm91.brapp.databinding.ActivityMainBinding
import com.agm91.brapp.util.ConnectivityReceiver
import com.agm91.brapp.view.adapter.StoresAdapter
import com.agm91.brapp.viewmodel.StoresViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {
    private val adapter = StoresAdapter()
    private lateinit var binding: ActivityMainBinding
    private val receiver = ConnectivityReceiver(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(
            receiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val viewModel: StoresViewModel =
            ViewModelProviders.of(this).get(StoresViewModel::class.java)
        viewModel.getStores().observe(this, Observer { apiResponse ->
            if (apiResponse.error != null) {
                Toast.makeText(this@MainActivity, "Error: " + apiResponse.error, Toast.LENGTH_LONG)
                    .show()
            } else {
                apiResponse.data?.let { adapter.setData(it) }
                binding.recycler.adapter = adapter
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.text_share))
                sendIntent.type = "text/plain"

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
            R.id.action_gihub -> openUrl(getString(R.string.url_github))
            R.id.action_linkedin -> openUrl(getString(R.string.url_linkedin))
        }
        return true
    }

    private fun openUrl(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        val text = if (isConnected) {
            "You are back online!"
        } else {
            "You are offline"
        }
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
    }
}
