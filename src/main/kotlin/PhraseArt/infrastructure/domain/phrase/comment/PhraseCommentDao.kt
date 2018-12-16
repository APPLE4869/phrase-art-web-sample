package PhraseArt.infrastructure.domain.phrase.comment

data class PhraseCommentDao(
    val id: String,
    val userId: String,
    val phraseId: String,
    val content: String
)
