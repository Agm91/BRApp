package com.agm91.brapp.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Store(
    @SerializedName("address") val address: String,
    @SerializedName("city") val city: String,
    @SerializedName("name") val name: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("zipcode") val zipcode: Int,
    @SerializedName("storeLogoURL") val storeLogoURL: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("storeID") val storeID: Int,
    @SerializedName("state") val state: String
) : Serializable {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String) {
            Glide.with(view.context).load(url).into(view)
        }
    }
}