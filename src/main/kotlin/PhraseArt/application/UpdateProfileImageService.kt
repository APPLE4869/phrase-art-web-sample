package PhraseArt.application

import PhraseArt.domain.user.User
import PhraseArt.domain.user.UserId
import PhraseArt.domain.user.UserRepository
import PhraseArt.support.BadRequestException
import PhraseArt.support.ForbiddenException
import PhraseArt.support.S3Client
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class UpdateProfileImageService(
    @Autowired val s3Client : S3Client,
    @Autowired val userRepository: UserRepository
) {
    fun updateImage(image: MultipartFile, principalName: String) {
        if(image.isEmpty()) {
            throw BadRequestException("画像を送信してください")
        }

        val user = userRepository.userOfId(UserId(principalName)) ?:
                throw ForbiddenException("ログイン中のアカウントが不正です")

        try {
            val uploadedImagePath = s3Client.upload(image, User.IMAGE_PATH_SYMBOL, user.id)
            user.changeProfileImage(uploadedImagePath)
        } catch(e: Exception) {
            // TODO : とりあえず例外にメッセージを残しているだけなので、あとで改修する。
            throw Exception("画像の登録処理に失敗しました")
        }

        userRepository.store(user)
    }
}
