package PhraseArt.application.UpdateRequest

import PhraseArt.domain.updateRequest.UpdateRequestRepository
import PhraseArt.domain.updateRequest.UpdateRequestCommentDomainService
import PhraseArt.domain.updateRequest.UpdateRequestCommentId
import PhraseArt.domain.updateRequest.UpdateRequestId
import PhraseArt.domain.updateRequest.UpdateRequestCommentRepository
import PhraseArt.domain.user.UserId
import PhraseArt.support.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import PhraseArt.domain.user.UserRepository
import PhraseArt.presentation.api.form.UpdateRequest.UpdateRequestCommentForm
import PhraseArt.query.Dto.UpdateRequest.UpdateRequestCommentQueryDto
import PhraseArt.support.ForbiddenException
import PhraseArt.query.UpdateRequest.UpdateRequestCommentQuery
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateRequestCommentService(
    @Autowired val userRepository : UserRepository,
    @Autowired val updateRequestRepository : UpdateRequestRepository,
    @Autowired val commentRepository : UpdateRequestCommentRepository,
    @Autowired val updateRequestCommentQuery : UpdateRequestCommentQuery,
    @Autowired val updateRequestCommentDomainService : UpdateRequestCommentDomainService
) {
    @Transactional
    fun post(form: UpdateRequestCommentForm, principalName: String) {
        val user = userRepository.userOfId(UserId(principalName)) ?:
            throw ForbiddenException("申請する権限がありません")

        val updateRequest = updateRequestRepository.requestOfId(UpdateRequestId(form.updateRequestId)) ?:
            throw NotFoundException("該当する申請がありません")

        // ユーザーがコメントできることを確認
        updateRequestCommentDomainService.assertUserCommentable(user)

        val comment = updateRequest.comment(commentRepository.nextIdentity(), user, form.content)
        return commentRepository.store(comment)
    }

    fun previousList(updateRequestId: String, offset: Int, latestCommentId: String?): List<UpdateRequestCommentQueryDto> {
        if (latestCommentId == null)
            return updateRequestCommentQuery.selectAllLatestComments(UpdateRequestId(updateRequestId))
        else
            return updateRequestCommentQuery.selectAllPreviousComments(
                UpdateRequestId(updateRequestId),
                offset,
                UpdateRequestCommentId(latestCommentId)
            )
    }

    fun followingList(updateRequestId: String, latestCommentId: String): List<UpdateRequestCommentQueryDto> {
        return updateRequestCommentQuery.selectAllFollowingComments(
            UpdateRequestId(updateRequestId),
            UpdateRequestCommentId(latestCommentId)
        )
    }
}
