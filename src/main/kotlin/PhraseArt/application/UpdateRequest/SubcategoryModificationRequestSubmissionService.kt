package PhraseArt.application.UpdateRequest

import PhraseArt.domain.category.*
import PhraseArt.domain.updateRequest.UpdateRequestId
import PhraseArt.domain.updateRequest.UpdateRequestRepository
import PhraseArt.domain.updateRequest.phrase.SubcategoryModificationRequest
import PhraseArt.domain.user.UserId
import PhraseArt.domain.user.UserRepository
import PhraseArt.presentation.api.form.UpdateRequest.SubcategoryModificationRequestForm
import PhraseArt.support.ForbiddenException
import PhraseArt.support.NotFoundException
import PhraseArt.support.S3Client
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.lang.IllegalStateException

@Service
class SubcategoryModificationRequestSubmissionService(
    @Autowired val s3Client : S3Client,
    @Autowired val userRepository: UserRepository,
    @Autowired val updateRequestRepository : UpdateRequestRepository,
    @Autowired val subcategoryRepository: SubcategoryRepository
) {
    @Transactional
    fun submit(form: SubcategoryModificationRequestForm, image: MultipartFile?, principalName: String) {
        // ログイン中のユーザーデータを取得
        val user = userRepository.userOfId(UserId(principalName)) ?:
            throw ForbiddenException("申請する権限がありません")

        val subcategory: Subcategory = subcategoryRepository.subcategoryOfId(SubcategoryId(form.id)) ?:
            throw NotFoundException("申請対象のサブカテゴリーが存在しません")

        // TODO : 呼び出し元に必要な知識が多すぎる気がするので、もう少しシンプルにできないか考えて改修する。
        val videoOnDemands = videoOnDemandsOfNameKeys(form.videoOnDemandNameKeys)

        if (!subcategory.isAnyChanged(form.name, image, form.introduction, videoOnDemands))
            throw IllegalArgumentException("いずれかの内容を修正してください")

        if (updateRequestRepository.subcategoryModificationRequestOfSubcategoryId(subcategory.id) != null)
            throw IllegalStateException("現在このサブカテゴリーには別の修正申請が出されています")

        val requestId = updateRequestRepository.nextIdentity()
        val uploadedImagePath =
            if (image != null)
                generateUploadedImagePath(requestId, image)
            else
                subcategory.imagePath

        val request = SubcategoryModificationRequest.create(
            requestId,
            user.id,
            subcategory.id,
            form.name,
            form.introduction,
            uploadedImagePath,
            subcategory,
            videoOnDemands
        )

        updateRequestRepository.store(request)
    }

    private fun generateUploadedImagePath(requestId: UpdateRequestId, image: MultipartFile): String? {
        try {
            return s3Client.upload(image, Subcategory.IMAGE_PATH_SYMBOL, requestId)
        } catch(e: Exception) {
            throw Exception("画像の登録処理に失敗しました")
        }
    }

    private fun videoOnDemandsOfNameKeys(nameKeys: List<String>?): List<VideoOnDemand>? {
        if (nameKeys == null || nameKeys.count() == 0)
            return null

        val nameKeyTypes = nameKeys.map {
            VideOnDemandNameKeyType.fromValue(it) ?: throw NotFoundException("申請された動画配信サービスは存在しません")
        }

        return subcategoryRepository.videoOnDemandsOfNameKeys(nameKeyTypes)
    }
}
