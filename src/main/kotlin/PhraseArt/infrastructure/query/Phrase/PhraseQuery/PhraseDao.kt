package PhraseArt.infrastructure.query.Phrase.PhraseQuery

data class PhraseDao(
    val phraseId: String,
    val categoryId: String,
    val categoryName: String,
    val categorySequence: Int,
    val subcategoryId: String?,
    val subcategoryName: String?,
    val subcategoryImagePath: String?,
    val subcategoryIntroduction: String?,
    val subcategorySequence: Int?,
    val phraseContent: String,
    val authorName: String,
    val likeCount: Long,
    val favoriteCount: Long,
    val commentCount: Long,
    val currentUserLiked: Int,
    val currentUserFavorited: Int
)
