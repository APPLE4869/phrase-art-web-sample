package PhraseArt.domain.updateRequest

interface UpdateRequestCommentRepository {
    fun nextIdentity(): UpdateRequestCommentId

    fun store(comment: UpdateRequestComment)
}
