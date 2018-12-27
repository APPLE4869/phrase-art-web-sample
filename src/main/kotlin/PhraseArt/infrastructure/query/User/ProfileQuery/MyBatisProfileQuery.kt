package PhraseArt.infrastructure.query.User.ProfileQuery

import PhraseArt.domain.user.User
import PhraseArt.domain.user.UserId
import PhraseArt.query.Dto.User.ProfileQueryDto
import PhraseArt.query.User.ProfileQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class MyBatisProfileQuery(
    @Autowired val profileQueryMapper : ProfileQueryMapper
) : ProfileQuery {
    @Value("\${aws.bucket_name}")
    val bucketName: String = ""

    @Value("\${aws.s3_root_url}")
    val s3RootUrl: String = ""

    override fun profileOfUserId(userId: UserId): ProfileQueryDto? {
        return profileQueryMapper.selectOneByUserId(userId.value)?.let { daoToProfile(it) }
    }

    private fun daoToProfile(dao: ProfileQueryDao): ProfileQueryDto {
        return ProfileQueryDto(
            "${s3RootUrl}/${bucketName}/${dao.imagePath}"
        )
    }
}
