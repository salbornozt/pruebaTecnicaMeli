package com.satdev.pruebameli.feature_search_product.data.model

import com.google.gson.annotations.SerializedName

data class Seller(
    @SerializedName("id"       ) var id       : Int?    = null,
    @SerializedName("nickname" ) var nickname : String? = null
)
