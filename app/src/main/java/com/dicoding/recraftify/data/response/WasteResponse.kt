package com.dicoding.recraftify.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class WasteResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String
)

@Parcelize
data class DataItem(

	@field:SerializedName("instructions")
	val instructions: String,

	@field:SerializedName("method")
	val method: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("waste_type")
	val wasteType: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("url")
	val url: String
): Parcelable
