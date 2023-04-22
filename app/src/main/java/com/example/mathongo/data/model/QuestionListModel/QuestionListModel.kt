package com.example.mathongo.data.model.QuestionListModel

import androidx.room.Entity
import androidx.room.PrimaryKey

data class QuestionListModel(
	val quotaMax: Int,
	val quotaRemaining: Int,
	val hasMore: Boolean,
	val items: List<Question>
)

@Entity
data class Question(
	@PrimaryKey(autoGenerate = false)
	val questionId: Int?,
//	val owner: Owner,
	val contentLicense: String?,
	val score: Int?,
	val link: String?,
	val lastActivityDate: Int?,
	val isAnswered: Boolean?,
	val creationDate: Int?,
	val answerCount: Int?,
	val title: String?,
	val viewCount: Int?,
//	val tags: List<String>,
	val acceptedAnswerId: Int?,
	val lastEditDate: Int?
)

//data class Owner(
//	val profileImage: String,
//	val accountId: Int,
//	val userType: String,
//	val userId: Int,
//	val link: String,
//	val reputation: Int,
//	val displayName: String,
//	val acceptRate: Int
//)

