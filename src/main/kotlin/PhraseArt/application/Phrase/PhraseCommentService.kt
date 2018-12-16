package PhraseArt.application.Phrase

import PhraseArt.domain.phrase.*
import PhraseArt.domain.user.UserId
import PhraseArt.support.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import PhraseArt.domain.user.UserRepository
import PhraseArt.presentation.api.form.Phrase.PhraseCommentForm
import PhraseArt.query.Dto.Phrase.PhraseCommentQueryDto
import PhraseArt.support.ForbiddenException
import PhraseArt.query.Phrase.PhraseCommentQuery
import org.springframework.transaction.annotation.Transactional

@Service
class PhraseCommentService(
    @Autowired val userRepository : UserRepository,
    @Autowired val phraseRepository : PhraseRepository,
    @Autowired val commentRepository : PhraseCommentRepository,
    @Autowired val phraseCommentQuery : PhraseCommentQuery,
    @Autowired val phraseCommentDomainService : PhraseCommentDomainService
) {
    @Transactional
    fun post(form: PhraseCommentForm, principalName: String) {
        val user = userRepository.userOfId(UserId(principalName)) ?:
            throw ForbiddenException("申請する権限がありません")

        val phrase = phraseRepository.phraseOfId(PhraseId(form.phraseId)) ?:
            throw NotFoundException("該当する名言がありません")

        // ユーザーがコメントできることを確認
        phraseCommentDomainService.assertUserCommentable(user)

        val comment = phrase.comment(commentRepository.nextIdentity(), user, form.content)
        return commentRepository.store(comment)
    }

    fun previousList(phraseId: String, offset: Int, latestCommentId: String?): List<PhraseCommentQueryDto> {
        if (latestCommentId == null)
            return phraseCommentQuery.selectAllLatestComments(PhraseId(phraseId))
        else
            return phraseCommentQuery.selectAllPreviousComments(PhraseId(phraseId), offset, PhraseCommentId(latestCommentId))
    }

    fun followingList(phraseId: String, latestCommentId: String): List<PhraseCommentQueryDto> {
        return phraseCommentQuery.selectAllFollowingComments(PhraseId(phraseId), PhraseCommentId(latestCommentId))
    }
}
