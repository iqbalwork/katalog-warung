package com.uziro.katalogwarung.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductData(
    var key: String = "",
    @SerialName("barcode") var barcode: String? = null,
    @SerialName("hargabeli") var hargabeli: Int? = null,
    @SerialName("hargajual") var hargajual: Int? = null,
    @SerialName("nama") var nama: String? = null,
    @SerialName("satuan") var satuan: String? = null,
    @SerialName("stok") var stok: Int? = null,
    @SerialName("kategori") var kategori: String? = null
) {
    constructor() : this("", "", 0, 0, "", "", 0, "")
}
