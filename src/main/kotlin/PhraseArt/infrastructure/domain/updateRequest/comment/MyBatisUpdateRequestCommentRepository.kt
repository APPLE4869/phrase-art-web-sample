package PhraseArt.infrastructure.domain.updateRequest.comment

import PhraseArt.domain.updateRequest.UpdateRequestComment
import PhraseArt.domain.updateRequest.UpdateRequestCommentId
import PhraseArt.domain.updateRequest.UpdateRequestCommentRepository
import PhraseArt.infrastructure.domain.phrase.comment.PhraseCommentDomainMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class MyBatisUpdateRequestCommentRepository(
    @Autowired private val updateRequestCommentDomainMapper: UpdateRequestCommentDomainMapper
) : UpdateRequestCommentRepository {
    override fun nextIdentity(): UpdateRequestCommentId {
        return UpdateRequestCommentId(UUID.randomUUID().toString().toUpperCase())
    }

    override fun store(comment: UpdateRequestComment) {
        if (updateRequestCommentDomainMapper.selectOneById(comment.id.value) == null)
            updateRequestCommentDomainMapper.insertComment(
                comment.id.value,
                comment.userId.value,
                comment.updateRequestId.value,
                comment.content
            )
        else
            updateRequestCommentDomainMapper.updateComment(comment.id.value, comment.content)
    }
}
