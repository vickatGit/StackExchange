package com.example.mathongo.data.model.QuestionListModel

import androidx.room.*
import com.google.gson.annotations.SerializedName

data class QuestionListModel(
	val quotaMax: Int,
	val quotaRemaining: Int,
	@SerializedName("has_more") val hasMore: Boolean,
	val items: List<Question>
)

@Entity
data class Question(
	@PrimaryKey(autoGenerate = false)
	@SerializedName("question_id")val questionId: Int?,
	@Embedded val owner: Owner?,
	@SerializedName("content_license")val contentLicense: String?,
	val score: Int?,
	val link: String?,
	@SerializedName("last_activity_date") val lastActivityDate: Int?,
	@SerializedName("is_answered") val isAnswered: Boolean?,
	@SerializedName("creation_date") val creationDate: Int?,
	@SerializedName("answer_count")val answerCount: Int?,
	val title: String?,
	@SerializedName("view_count") val viewCount: Int?,
	@TypeConverters(TypeConverter::class) val tags: List<String?>?,
	@SerializedName("accepted_answer_id") val acceptedAnswerId: Int?,
	@SerializedName("last_edit_date") val lastEditDate: Int?
)

data class Owner(

	@SerializedName("profile_image")val profileImage: String?,
	@SerializedName("account_id") val accountId: Int?,
	@SerializedName("user_type") val userType: String?,
	@SerializedName("user_id") val userId: Int?,
	@SerializedName("_link") val ownerLink: String?,
	val reputation: Int?,
	@SerializedName("display_name") val displayName: String?,
	@SerializedName("accept_ratee") val acceptRate: Int?
)

