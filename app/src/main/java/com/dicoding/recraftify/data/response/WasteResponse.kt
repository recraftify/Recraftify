package com.dicoding.recraftify.data.response

import com.google.gson.annotations.SerializedName

data class WasteResponse(

	@field:SerializedName("data")
	val data: List<DataList?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataList(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
