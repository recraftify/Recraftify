package com.dicoding.recraftify.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ScanResponse(

	@field:SerializedName("data")
	val data: DataScan? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataScan(

	@field:SerializedName("trash_type")
	val trashType: String? = null,

	@field:SerializedName("recommendation")
	val recommendation: List<Recommendation?>? = null,

	@field:SerializedName("uploaded_image")
	val uploadedImage: String? = null,

	@field:SerializedName("image_result")
	val imageResult: String? = null
)
@Parcelize
data class Recommendation(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
):Parcelable
