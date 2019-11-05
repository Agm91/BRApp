package com.agm91.brapp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.agm91.brapp.R
import com.agm91.brapp.databinding.ActivityShowStoreBinding
import com.agm91.brapp.view.fragment.StoreFragment

class GenericActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityShowStoreBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_show_store)

        val newFragment = StoreFragment.newInstance(intent.extras)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, newFragment)
        transaction.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
