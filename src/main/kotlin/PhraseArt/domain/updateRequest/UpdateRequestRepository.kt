package PhraseArt.domain.updateRequest

import PhraseArt.domain.category.CategoryId
import PhraseArt.domain.category.SubcategoryId
import PhraseArt.domain.phrase.PhraseId

interface UpdateRequestRepository {
    fun nextIdentity(): UpdateRequestId

    fun requestOfId(id: UpdateRequestId): UpdateRequest?

    fun allUnfinishedExpiredRequest(): List<UpdateRequest>

    fun store(registrationRequest: UpdateRequest)

    fun phraseRegistrationRequestOf(
        categoryId: CategoryId,
        subcategoryName: String,
        phraseContent: String,
        authorName: String
    ): UpdateRequest?

    fun phraseModificationRequestOf(
        categoryId: CategoryId,
        subcategoryName: String,
        phraseContent: String,
        authorName: String
    ): UpdateRequest?

    fun phraseModificationRequestOfPhraseId(phraseId: PhraseId): UpdateRequest?

    fun phraseDeletionRequestOfPhraseId(phraseId: PhraseId): UpdateRequest?

    fun subcategoryModificationRequestOfSubcategoryId(subcategoryId: SubcategoryId): UpdateRequest?
}
