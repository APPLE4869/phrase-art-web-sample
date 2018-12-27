package PhraseArt.query.UpdateRequest

import PhraseArt.domain.updateRequest.UpdateRequestId
import PhraseArt.query.Dto.UpdateRequest.PhraseDeletionRequestQueryDto
import PhraseArt.query.Dto.UpdateRequest.PhraseModificationRequestQueryDto
import PhraseArt.query.Dto.UpdateRequest.PhraseRegistrationRequestQueryDto
import PhraseArt.query.Dto.UpdateRequest.SubcategoryModificationRequestQueryDto

interface UpdateRequestDetailQuery {
    fun phraseRegistrationRequestOfId(id: UpdateRequestId): PhraseRegistrationRequestQueryDto?

    fun phraseModificationRequestOfId(id: UpdateRequestId): PhraseModificationRequestQueryDto?

    fun phraseDeletionRequestOfId(id: UpdateRequestId): PhraseDeletionRequestQueryDto?

    fun subcategoryModificationRequestOfId(id: UpdateRequestId): SubcategoryModificationRequestQueryDto?
}
