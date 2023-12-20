package com.dicoding.recraftify.data.response

data class ProfileResponse(
	val data: DataProfile? = null,
	val message: String? = null
)

data class DataProfile(
	val createdAt: String? = null,
	val name: String? = null,
	val id: String? = null,
	val email: String? = null,
	val updatedAt: String? = null
)

