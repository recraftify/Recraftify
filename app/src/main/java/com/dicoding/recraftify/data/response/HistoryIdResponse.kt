package com.dicoding.recraftify.data.response

import com.google.gson.annotations.SerializedName

data class HistoryIdResponse(

	@field:SerializedName("data")
	val data: DataHistory? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataHistory(

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("trash_type")
	val trashType: String? = null,

	@field:SerializedName("recommendation")
	val recommendation: List<RecommendationItem?>? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("uploaded_image")
	val uploadedImage: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("image_result")
	val imageResult: String? = null
)

data class RecommendationItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
