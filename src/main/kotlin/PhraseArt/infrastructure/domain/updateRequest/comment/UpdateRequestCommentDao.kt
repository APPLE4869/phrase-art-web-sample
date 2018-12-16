package PhraseArt.infrastructure.domain.updateRequest.comment

data class UpdateRequestCommentDao(
    val id: String,
    val userId: String,
    val updateRequestId: String,
    val content: String
)
