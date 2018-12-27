package PhraseArt.infrastructure.query.Phrase.PhraseCommentQuery

import PhraseArt.domain.updateRequest.UpdateRequestCommentId
import PhraseArt.domain.updateRequest.UpdateRequestId
import PhraseArt.query.Dto.UpdateRequest.UpdateRequestCommentQueryDto
import PhraseArt.query.Phrase.PhraseCommentQuery
import PhraseArt.query.UpdateRequest.UpdateRequestCommentQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.temporal.ChronoUnit

@Component
class MyBatisUpdateRequestCommentQuery(
    @Autowired private val updateRequestCommentQueryMapper: UpdateRequestCommentQueryMapper
) : UpdateRequestCommentQuery {
    override fun selectAllLatestComments(updateRequestId: UpdateRequestId): List<UpdateRequestCommentQueryDto> {
        return updateRequestCommentQueryMapper.selectAllLatestComments(updateRequestId.value).map {
            daoToPhraseComment(it)
        }
    }

    override fun selectAllPreviousComments(
        updateRequestId: UpdateRequestId,
        offset: Int, latestCommentId: UpdateRequestCommentId
    ): List<UpdateRequestCommentQueryDto> {
        return updateRequestCommentQueryMapper.selectAllPreviousComments(
            updateRequestId.value,
            latestCommentId.value,
            offset, 20
        ).map {
            daoToPhraseComment(it)
        }
    }

    override fun selectAllFollowingComments(
        updateRequestId: UpdateRequestId,
        commentId: UpdateRequestCommentId
    ): List<UpdateRequestCommentQueryDto> {
        return updateRequestCommentQueryMapper.selectAllFollowingComments(updateRequestId.value, commentId.value).map {
            daoToPhraseComment(it)
        }
    }

    private fun daoToPhraseComment(dao: UpdateRequestCommentQueryDao): UpdateRequestCommentQueryDto {
        return UpdateRequestCommentQueryDto(
            dao.commentId,
            dao.userId,
            dao.username,
            dao.userImagePath,
            dao.content,
            dao.createAt.toLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
        )
    }
}
