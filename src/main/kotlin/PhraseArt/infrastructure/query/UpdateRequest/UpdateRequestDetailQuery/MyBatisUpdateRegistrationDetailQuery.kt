package PhraseArt.infrastructure.query.UpdateRequest.UpdateRequestDetailQuery

import PhraseArt.domain.updateRequest.UpdateRequestId
import PhraseArt.query.Dto.UpdateRequest.PhraseRegistrationRequestQueryDto
import PhraseArt.query.Dto.UpdateRequest.PhraseModificationRequestQueryDto
import PhraseArt.query.Dto.UpdateRequest.PhraseDeletionRequestQueryDto
import PhraseArt.query.Dto.UpdateRequest.SubcategoryModificationRequestQueryDto
import PhraseArt.query.UpdateRequest.UpdateRequestDetailQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class MyBatisUpdateRegistrationDetailQuery(
    @Autowired private val updateRequestDetailQueryMapper : UpdateRequestDetailQueryMapper
) : UpdateRequestDetailQuery {
    @Value("\${aws.bucket_name}")
    val bucketName: String = ""

    @Value("\${aws.s3_root_url}")
    val s3RootUrl: String = ""

    override fun phraseRegistrationRequestOfId(id: UpdateRequestId): PhraseRegistrationRequestQueryDto? {
        return updateRequestDetailQueryMapper.selectOnePhraseRegistrationReqeustById(id.value)?.let { daoToPhraseRegistrationRequest(it) }
    }

    override fun phraseModificationRequestOfId(id: UpdateRequestId): PhraseModificationRequestQueryDto? {
        return updateRequestDetailQueryMapper.selectOnePhraseModificationReqeustById(id.value)?.let { daoToPhraseModificationRequest(it) }
    }

    override fun phraseDeletionRequestOfId(id: UpdateRequestId): PhraseDeletionRequestQueryDto? {
        return updateRequestDetailQueryMapper.selectOnePhraseDeletionReqeustById(id.value)?.let { daoToPhraseDeletionRequest(it) }
    }

    override fun subcategoryModificationRequestOfId(id: UpdateRequestId): SubcategoryModificationRequestQueryDto? {
        return updateRequestDetailQueryMapper.selectOneSubcategoryModificationReqeustById(id.value)?.let { daoToSubcategoryModificationRequest(it) }
    }

    private fun daoToPhraseRegistrationRequest(dao: PhraseRegistrationRequestDao): PhraseRegistrationRequestQueryDto {
        return PhraseRegistrationRequestQueryDto(
            dao.updateRequestId,
            dao.finished,
            dao.decisionExpiresAt.toLocalDateTime(),
            dao.finalDecisionResult,
            dao.categoryId,
            dao.categoryName,
            dao.subcategoryId,
            dao.subcategoryName,
            dao.phraseContent,
            dao.phraseAuthorName,
            dao.approvedCount.toInt(),
            dao.rejectedCount.toInt()
        )
    }

    private fun daoToPhraseModificationRequest(dao: PhraseModificationRequestDao): PhraseModificationRequestQueryDto {
        return PhraseModificationRequestQueryDto(
            dao.updateRequestId,
            dao.finished,
            dao.decisionExpiresAt.toLocalDateTime(),
            dao.finalDecisionResult,
            dao.requestedCategoryId,
            dao.requestedCategoryName,
            dao.requestedSubcategoryId,
            dao.requestedSubcategoryName,
            dao.requestedPhraseContent,
            dao.requestedPhraseAuthorName,
            dao.currentCategoryId,
            dao.currentCategoryName,
            dao.currentSubcategoryId,
            dao.currentSubcategoryName,
            dao.currentPhraseContent,
            dao.currentPhraseAuthorName,
            dao.approvedCount.toInt(),
            dao.rejectedCount.toInt()
        )
    }

    private fun daoToPhraseDeletionRequest(dao: PhraseDeletionRequestDao): PhraseDeletionRequestQueryDto {
        return PhraseDeletionRequestQueryDto(
            dao.updateRequestId,
            dao.finished,
            dao.decisionExpiresAt.toLocalDateTime(),
            dao.finalDecisionResult,
            dao.categoryId,
            dao.categoryName,
            dao.subcategoryId,
            dao.subcategoryName,
            dao.phraseContent,
            dao.phraseAuthorName,
            dao.approvedCount.toInt(),
            dao.rejectedCount.toInt()
        )
    }

    private fun daoToSubcategoryModificationRequest(dao: SubcategoryModificationRequestDao): SubcategoryModificationRequestQueryDto {
        var requestedVideoOnDemandNameKeys: List<String>? = null
        var currentVideoOnDemandNameKeys: List<String>? = null
        if (dao.currentCategoryVideoOnDemandAssociated) {
            // TODO : MyBatisのCollection機能を利用するように改修する。
            requestedVideoOnDemandNameKeys = updateRequestDetailQueryMapper.selectAllVideoOnDemandNameKeysBySubcategoryId(dao.updateRequestId)
            currentVideoOnDemandNameKeys = updateRequestDetailQueryMapper.selectAllVideoOnDemandNameKeysByUpdateRequestId(dao.updateRequestId)
        }

        return SubcategoryModificationRequestQueryDto(
            dao.updateRequestId,
            dao.finished,
            dao.decisionExpiresAt.toLocalDateTime(),
            dao.finalDecisionResult,
            dao.requestedSubcategoryId,
            dao.requestedSubcategoryName,
            dao.requestedSubcategoryIntroduction,
            s3ImageUrlFromPath(dao.requestedSubcategoryImagePath),
            requestedVideoOnDemandNameKeys,
            dao.currentCategoryId,
            dao.currentCategoryName,
            dao.currentCategoryVideoOnDemandAssociated,
            dao.currentSubcategoryName,
            dao.currentSubcategoryIntroduction,
            s3ImageUrlFromPath(dao.currentSubcategoryImagePath),
            currentVideoOnDemandNameKeys,
            dao.approvedCount.toInt(),
            dao.rejectedCount.toInt()
        )
    }

    fun s3ImageUrlFromPath(path: String?): String? {
        if (path == null)
            return null
        return "${s3RootUrl}/${bucketName}/${path}"
    }
}
