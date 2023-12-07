package com.dicoding.recraftify.data.response

import com.google.gson.annotations.SerializedName

data class WasteResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String
)

data class DataItem(

	@field:SerializedName("metode_pemilahan")
	val metodePemilahan: String,

	@field:SerializedName("petunjuk_langkah")
	val petunjukLangkah: List<String>,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("jenis_sampah")
	val jenisSampah: String,

	@field:SerializedName("url")
	val url: String
)
