package PhraseArt.application.UpdateRequest

import PhraseArt.domain.category.CategoryId
import PhraseArt.domain.category.SubcategoryRepository
import PhraseArt.domain.user.UserRepository
import PhraseArt.presentation.api.form.UpdateRequest.PhraseRegistrationRequestForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import PhraseArt.domain.updateRequest.phrase.PhraseRegistrationRequest
import PhraseArt.domain.user.UserId
import PhraseArt.support.ForbiddenException
import PhraseArt.domain.category.Subcategory
import PhraseArt.domain.category.SubcategoryId
import PhraseArt.domain.phrase.PhraseId
import PhraseArt.domain.phrase.PhraseRepository
import PhraseArt.domain.updateRequest.UpdateRequestRepository
import PhraseArt.domain.updateRequest.phrase.PhraseDeletionRequest
import PhraseArt.domain.updateRequest.phrase.PhraseModificationRequest
import PhraseArt.presentation.api.form.UpdateRequest.PhraseDeletionRequestForm
import PhraseArt.presentation.api.form.UpdateRequest.PhraseModificationRequestForm
import org.springframework.transaction.annotation.Transactional
import PhraseArt.domain.updateRequest.phrase.PhraseUpdateRequestDomainService

@Service
class PhraseUpdateRequestSubmissionService(
    @Autowired val phraseRepository: PhraseRepository,
    @Autowired val userRepository: UserRepository,
    @Autowired val updateRequestRepository : UpdateRequestRepository,
    @Autowired val subcategoryRepository: SubcategoryRepository,
    @Autowired val phraseUpdateRequestDomainService : PhraseUpdateRequestDomainService
) {
    @Transactional
    fun submitRegistrationRequest(form: PhraseRegistrationRequestForm, principalName: String) {
        // ログイン中のユーザーデータを取得
        val user = userRepository.userOfId(UserId(principalName)) ?:
            throw ForbiddenException("申請する権限がありません")

        //カテゴリーIDとサブカテゴリー名に一致するカテゴリーがあれば取得
        val categoryId = CategoryId(form.categoryId)
        val subcategory: Subcategory? =
                subcategoryRepository.subcategoryOfCategoryIdAndName(categoryId, form.subcategoryName)

        // 申請された名言が登録可能なものかを確認
        phraseUpdateRequestDomainService.assertRegistablePhrase(
            categoryId,
            subcategory?.id,
            form.subcategoryName,
            form.phraseContent,
            form.authorName
        )

        // 登録申請を作成
        val request = PhraseRegistrationRequest.create(
            updateRequestRepository.nextIdentity(),
            user.id,
            categoryId,
            subcategory?.id,
            form.subcategoryName,
            form.phraseContent,
            form.authorName
        )

        updateRequestRepository.store(request)
    }

    @Transactional
    fun submitModificationRequest(form: PhraseModificationRequestForm, principalName: String) {
        // ログイン中のユーザーデータを取得
        val user = userRepository.userOfId(UserId(principalName)) ?: throw ForbiddenException("申請する権限がありません")

        val phrase = phraseRepository.phraseOfId(PhraseId(form.phraseId)) ?:
            throw ForbiddenException("この名言はすでに削除されています")

        //カテゴリーIDとサブカテゴリー名に一致するカテゴリーがあれば取得
        val categoryId = CategoryId(form.categoryId)
        val subcategory: Subcategory? =
                subcategoryRepository.subcategoryOfCategoryIdAndName(categoryId, form.subcategoryName)

        // 申請された名言が登録可能なものかを確認
        phraseUpdateRequestDomainService.assertRegistablePhrase(
            categoryId,
            subcategory?.id,
            form.subcategoryName,
            form.phraseContent,
            form.authorName
        )

        // 申請中の修正申請が出されていないことを確認
        phraseUpdateRequestDomainService.assertNotModificationRequesting(phrase.id)

        // 申請中の削除申請が出されていないことを確認
        phraseUpdateRequestDomainService.assertNotDeletionRequesting(phrase.id)

        // 修正申請を作成
        val request = PhraseModificationRequest.create(
            updateRequestRepository.nextIdentity(),
            user.id,
            phrase.id,
            categoryId,
            subcategory?.id,
            form.subcategoryName,
            form.phraseContent,
            form.authorName,
            phrase,
            subcategory?.name
        )

        updateRequestRepository.store(request)
    }

    @Transactional
    fun submitDeletionRequest(form: PhraseDeletionRequestForm, principalName: String) {
        // ログイン中のユーザーデータを取得
        val user = userRepository.userOfId(UserId(principalName)) ?:
            throw ForbiddenException("申請する権限がありません")

        val phrase = phraseRepository.phraseOfId(PhraseId(form.phraseId)) ?:
            throw ForbiddenException("この名言はすでに削除されています")

        val subcategory = phrase.subcategoryId?.let { subcategoryRepository.subcategoryOfId(it) }

        // 申請中の修正申請が出されていないことを確認
        phraseUpdateRequestDomainService.assertNotModificationRequesting(phrase.id)

        // 申請中の削除申請が出されていないことを確認
        phraseUpdateRequestDomainService.assertNotDeletionRequesting(phrase.id)

        // 削除申請を作成
        val request = PhraseDeletionRequest.create(
            updateRequestRepository.nextIdentity(),
            user.id,
            phrase.id,
            phrase,
            subcategory?.name
        )

        updateRequestRepository.store(request)
    }
}
