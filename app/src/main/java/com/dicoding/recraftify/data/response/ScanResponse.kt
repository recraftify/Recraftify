package com.dicoding.recraftify.data.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class ScanResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)
@Parcelize
data class Data(

	@field:SerializedName("result")
	val result: String
):Parcelable
