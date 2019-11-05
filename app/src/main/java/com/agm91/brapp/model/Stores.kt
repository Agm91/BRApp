package com.agm91.brapp.model

import com.google.gson.annotations.SerializedName

data class Stores(
    @SerializedName("stores") val stores: List<Store>
)