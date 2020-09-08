package com.tiendito.bmisrmovies.api

import com.google.gson.annotations.SerializedName

/**
 * Created by Mohamed Salama on 9/5/2020.
 */

data class GuestSessionResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("guest_session_id")val guestSessionId: String
)