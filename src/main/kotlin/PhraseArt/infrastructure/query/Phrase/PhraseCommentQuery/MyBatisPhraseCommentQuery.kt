package PhraseArt.infrastructure.query.Phrase.PhraseCommentQuery

import PhraseArt.domain.phrase.PhraseCommentId
import PhraseArt.domain.phrase.PhraseId
import PhraseArt.query.Dto.Phrase.PhraseCommentQueryDto
import PhraseArt.query.Phrase.PhraseCommentQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MyBatisPhraseCommentQuery(
    @Autowired private val phraseCommentQueryMapper: PhraseCommentQueryMapper
) : PhraseCommentQuery {
    override fun selectAllLatestComments(phraseId: PhraseId): List<PhraseCommentQueryDto> {
        return phraseCommentQueryMapper.selectAllLatestComments(phraseId.value).map {
            daoToPhraseComment(it)
        }
    }

    override fun selectAllPreviousComments(phraseId: PhraseId, offset: Int, latestCommentId: PhraseCommentId): List<PhraseCommentQueryDto> {
        return phraseCommentQueryMapper.selectAllPreviousComments(phraseId.value, latestCommentId.value, offset, 20).map {
            daoToPhraseComment(it)
        }
    }

    override fun selectAllFollowingComments(phraseId: PhraseId, commentId: PhraseCommentId): List<PhraseCommentQueryDto> {
        return phraseCommentQueryMapper.selectAllFollowingComments(phraseId.value, commentId.value).map {
            daoToPhraseComment(it)
        }
    }

    private fun daoToPhraseComment(dao: PhraseCommentQueryDao): PhraseCommentQueryDto {
        return PhraseCommentQueryDto(
            dao.commentId,
            dao.userId,
            dao.username,
            dao.userImagePath,
            dao.content,
            dao.createAt
        )
    }
}
