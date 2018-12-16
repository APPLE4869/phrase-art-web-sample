package PhraseArt.query.Phrase

import PhraseArt.domain.phrase.PhraseCommentId
import PhraseArt.domain.phrase.PhraseId
import PhraseArt.query.Dto.Phrase.PhraseCommentQueryDto

interface PhraseCommentQuery {
    fun selectAllLatestComments(phraseId: PhraseId): List<PhraseCommentQueryDto>

    fun selectAllPreviousComments(phraseId: PhraseId, offset: Int=0, commentId: PhraseCommentId): List<PhraseCommentQueryDto>

    fun selectAllFollowingComments(phraseId: PhraseId, commentId: PhraseCommentId): List<PhraseCommentQueryDto>
}
