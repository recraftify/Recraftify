package com.dicoding.recraftify.data.response

import com.google.gson.annotations.SerializedName


data class WasteIdResponse(

	@field:SerializedName("data")
	val data: DataItem? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("instructions")
	val instructions: List<String?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("waste_type")
	val wasteType: String? = null
)
