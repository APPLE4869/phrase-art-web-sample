package PhraseArt.domain.phrase

interface PhraseCommentRepository {
    fun nextIdentity(): PhraseCommentId

    fun store(comment: PhraseComment)
}
