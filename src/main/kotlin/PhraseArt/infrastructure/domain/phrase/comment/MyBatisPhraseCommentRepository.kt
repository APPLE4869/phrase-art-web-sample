package PhraseArt.infrastructure.domain.phrase.comment

import PhraseArt.domain.phrase.PhraseComment
import PhraseArt.domain.phrase.PhraseCommentId
import PhraseArt.domain.phrase.PhraseCommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class MyBatisPhraseCommentRepository(
        @Autowired private val phraseCommentMapper: PhraseCommentDomainMapper
) : PhraseCommentRepository {
    override fun nextIdentity(): PhraseCommentId {
        return PhraseCommentId(UUID.randomUUID().toString().toUpperCase())
    }

    override fun store(comment: PhraseComment) {
        if (phraseCommentMapper.selectOneById(comment.id.value) == null)
            phraseCommentMapper.insertComment(comment.id.value, comment.userId.value, comment.phraseId.value, comment.content)
        else
            phraseCommentMapper.updateComment(comment.id.value, comment.content)
    }
}
