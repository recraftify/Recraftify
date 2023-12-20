package com.dicoding.recraftify.data.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("data")
	val data: List<DataItemHistory?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItemHistory(

	@field:SerializedName("trash_type")
	val trashType: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("uploaded_image")
	val uploadedImage: String? = null
)
