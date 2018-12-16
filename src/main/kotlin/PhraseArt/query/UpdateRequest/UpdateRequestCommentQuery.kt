package PhraseArt.query.UpdateRequest

import PhraseArt.domain.updateRequest.UpdateRequestCommentId
import PhraseArt.domain.updateRequest.UpdateRequestId
import PhraseArt.query.Dto.UpdateRequest.UpdateRequestCommentQueryDto

interface UpdateRequestCommentQuery {
    fun selectAllLatestComments(updateRequestId: UpdateRequestId): List<UpdateRequestCommentQueryDto>

    fun selectAllPreviousComments(updateRequestId: UpdateRequestId, offset: Int=0, commentId: UpdateRequestCommentId): List<UpdateRequestCommentQueryDto>

    fun selectAllFollowingComments(updateRequestId: UpdateRequestId, commentId: UpdateRequestCommentId): List<UpdateRequestCommentQueryDto>
}
