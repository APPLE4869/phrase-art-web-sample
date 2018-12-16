package PhraseArt.infrastructure.domain.phrase.phrase

data class PhraseDao(
    val id: String,
    val categoryId: String,
    val subcategoryId: String,
    val authorName: String,
    val content: String
)
