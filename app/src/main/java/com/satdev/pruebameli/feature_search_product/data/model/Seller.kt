package com.satdev.pruebameli.feature_search_product.data.model

import com.google.gson.annotations.SerializedName

data class Seller(
    @SerializedName("id"       ) var id       : Long?    = null,
    @SerializedName("nickname" ) var nickname : String? = null
)
