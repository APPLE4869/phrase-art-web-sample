package PhraseArt.application.UpdateRequest

import PhraseArt.domain.category.Subcategory
import PhraseArt.domain.category.SubcategoryId
import PhraseArt.domain.category.SubcategoryRepository
import PhraseArt.domain.category.VideOnDemandNameKeyType
import PhraseArt.domain.updateRequest.UpdateRequestRepository
import PhraseArt.domain.updateRequest.phrase.SubcategoryModificationRequest
import PhraseArt.domain.user.UserId
import PhraseArt.domain.user.UserRepository
import PhraseArt.presentation.api.form.UpdateRequest.SubcategoryModificationRequestForm
import PhraseArt.support.ForbiddenException
import PhraseArt.support.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalStateException

@Service
class SubcategoryModificationRequestSubmissionService(
        @Autowired val userRepository: UserRepository,
        @Autowired val updateRequestRepository : UpdateRequestRepository,
        @Autowired val subcategoryRepository: SubcategoryRepository
) {
    @Transactional
    fun submitRegistrationRequest(form: SubcategoryModificationRequestForm, principalName: String) {
        // ログイン中のユーザーデータを取得
        val user = userRepository.userOfId(UserId(principalName)) ?:
            throw ForbiddenException("申請する権限がありません")

        val subcategory: Subcategory = subcategoryRepository.subcategoryOfId(SubcategoryId(form.id)) ?:
            throw NotFoundException("申請対象のサブカテゴリーが存在しません")

        val videoOnDemands = subcategoryRepository.videoOnDemandsOfNameKeys(
            form.videoOnDemandNameKeys.map {
                VideOnDemandNameKeyType.fromValue(it) ?: throw NotFoundException("申請された動画配信サービスは存在しません")
            }
        )

        if (!subcategory.isAnyChanged(form.name, form.introduction, videoOnDemands))
            throw IllegalArgumentException("いずれかの内容を修正してください")

        if (updateRequestRepository.subcategoryModificationRequestOfSubcategoryId(subcategory.id) != null)
            throw IllegalStateException("現在このサブカテゴリーには別の修正申請が出されています")

        val request = SubcategoryModificationRequest.create(
                updateRequestRepository.nextIdentity(),
                user.id,
                subcategory.id,
                form.name,
                form.introduction,
                null,// TODO : 画像アップロード実装時に改修予定
                subcategory
        )

        updateRequestRepository.store(request)
    }
}
