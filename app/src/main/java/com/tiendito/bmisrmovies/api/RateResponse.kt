package com.tiendito.bmisrmovies.api

import com.google.gson.annotations.SerializedName

/**
 * Created by Mohamed Salama on 9/5/2020.
 */
data class RateResponse(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String,
    @SerializedName("success") val success: Boolean
)