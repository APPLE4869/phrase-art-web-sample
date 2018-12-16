package PhraseArt.query.Dto.Phrase

data class PhraseQueryDto(
    val id: String,
    val categoryId: String,
    val categoryName: String,
    val subcategoryId: String?,
    val subcategoryName: String?,
    val content: String,
    val authorName: String,
    val likeCount: Int,
    val favoriteCount: Int,
    val commentCount: Int,
    val currentUserLiked: Boolean,
    val currentUserFavorited: Boolean
)
