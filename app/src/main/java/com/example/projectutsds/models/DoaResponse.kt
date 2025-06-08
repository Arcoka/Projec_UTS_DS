package com.example.projectutsds.models

import com.google.gson.annotations.SerializedName

data class DoaResponse(
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("data")
    val data: List<DoaItem>
)

data class DoaItem(
    @SerializedName("id")
    val id: String,

    @SerializedName("doa")
    val doa: String,

    @SerializedName("ayat")
    val ayat: String,

    @SerializedName("latin")
    val latin: String,

    @SerializedName("artinya")
    val artinya: String
)