package PhraseArt.domain.updateRequest.phrase

import PhraseArt.domain.category.CategoryId
import PhraseArt.domain.category.SubcategoryId
import PhraseArt.domain.phrase.PhraseId
import PhraseArt.domain.phrase.PhraseRepository
import PhraseArt.domain.updateRequest.UpdateRequestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import PhraseArt.domain.support.Service

@Component
class PhraseUpdateRequestDomainService(
    @Autowired val phraseRepository: PhraseRepository,
    @Autowired val updateRequestRepository : UpdateRequestRepository
) : Service() {
    // 名言の内容(引数)が登録可能なものかを確認
    fun assertRegistablePhrase(categoryId: CategoryId, subcategoryId: SubcategoryId?, subcategoryName: String, phraseContent: String, authorName: String) {
        // 同じ内容の名言がすでに登録されていないかチェック
        this.assertArgumentNull(
            phraseRepository.phraseOf(categoryId, subcategoryId, phraseContent, authorName),
            "すでに同じ内容の名言が登録済みです"
        )

        // 同じ内容の登録申請(申請中)が登録されていないかチェック
        this.assertArgumentNull(
            updateRequestRepository.phraseRegistrationRequestOf(categoryId, subcategoryName, phraseContent, authorName),
            "すでに同じ内容の名言が申請中です"
        )

        // 同じ内容の修正申請(申請中)が登録されていないかチェック
        this.assertArgumentNull(
            updateRequestRepository.phraseModificationRequestOf(categoryId, subcategoryName, phraseContent, authorName),
            "すでに同じ内容の名言が申請中です"
        )
    }

    // 対象の名言に修正申請が出されていないかを確認
    fun assertNotModificationRequesting(phraseId: PhraseId) {
        this.assertArgumentNull(
            updateRequestRepository.phraseModificationRequestOfPhraseId(phraseId),
            "この名言には修正申請が出されています"
        )
    }

    // 対象の名言に削除申請が出されていないかを確認
    fun assertNotDeletionRequesting(phraseId: PhraseId) {
        this.assertArgumentNull(
            updateRequestRepository.phraseDeletionRequestOfPhraseId(phraseId),
            "この名言には削除申請が出されています"
        )
    }
}
