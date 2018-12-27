package PhraseArt.infrastructure.query.Phrase.PhraseCommentQuery

import PhraseArt.domain.phrase.PhraseCommentId
import PhraseArt.domain.phrase.PhraseId
import PhraseArt.query.Dto.Phrase.PhraseCommentQueryDto
import PhraseArt.query.Phrase.PhraseCommentQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.temporal.ChronoUnit

@Component
class MyBatisPhraseCommentQuery(
    @Autowired private val phraseCommentQueryMapper: PhraseCommentQueryMapper
) : PhraseCommentQuery {
    @Value("\${aws.bucket_name}")
    val bucketName: String = ""

    @Value("\${aws.s3_root_url}")
    val s3RootUrl: String = ""

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
        val image =
            if (dao.userImagePath != null)
                "${s3RootUrl}/${bucketName}/${dao.userImagePath}"
            else
                null

        return PhraseCommentQueryDto(
            dao.commentId,
            dao.userId,
            dao.username,
            image,
            dao.content,
            dao.createAt.toLocalDateTime().truncatedTo(ChronoUnit.SECONDS)
        )
    }
}
