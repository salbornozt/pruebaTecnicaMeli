package com.satdev.pruebameli.feature_search_product.data.model

import com.google.gson.annotations.SerializedName

data class ProductAttributesValues(
    @SerializedName("id"     ) var id     : String? = null,
    @SerializedName("name"   ) var name   : String? = null,
    @SerializedName("struct" ) var struct : String? = null,
    @SerializedName("source" ) var source : Int?    = null

)
