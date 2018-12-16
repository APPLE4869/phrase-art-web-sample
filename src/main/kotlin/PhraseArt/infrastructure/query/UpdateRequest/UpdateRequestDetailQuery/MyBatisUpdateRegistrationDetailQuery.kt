package PhraseArt.infrastructure.query.UpdateRequest.UpdateRequestDetailQuery

import PhraseArt.domain.updateRequest.UpdateRequestId
import PhraseArt.query.Dto.UpdateRequest.PhraseRegistrationRequestQueryDto
import PhraseArt.query.Dto.UpdateRequest.PhraseModificationRequestQueryDto
import PhraseArt.query.Dto.UpdateRequest.PhraseDeletionRequestQueryDto
import PhraseArt.query.UpdateRequest.UpdateRequestDetailQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MyBatisUpdateRegistrationDetailQuery(
    @Autowired private val phraseRegistrationRequestQueryMapper : UpdateRequestDetailQueryMapper
) : UpdateRequestDetailQuery {
    override fun phraseRegistrationRequestOfId(id: UpdateRequestId): PhraseRegistrationRequestQueryDto? {
        return phraseRegistrationRequestQueryMapper.selectOnePhraseRegistrationReqeustById(id.value)?.let { daoToPhraseRegistrationRequest(it) }
    }

    override fun phraseModificationRequestOfId(id: UpdateRequestId): PhraseModificationRequestQueryDto? {
        return phraseRegistrationRequestQueryMapper.selectOnePhraseModificationReqeustById(id.value)?.let { daoToPhraseModificationRequest(it) }
    }

    override fun phraseDeletionRequestOfId(id: UpdateRequestId): PhraseDeletionRequestQueryDto? {
        return phraseRegistrationRequestQueryMapper.selectOnePhraseDeletionReqeustById(id.value)?.let { daoToPhraseDeletionRequest(it) }
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
}
