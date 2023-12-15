package com.dicoding.recraftify.data.response

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)
data class Data(

	@field:SerializedName("result")
	val result: String
)
