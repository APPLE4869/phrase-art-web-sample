package PhraseArt.infrastructure.query.Category.videoOnDemandQuery

import PhraseArt.query.Category.VideoOnDemandQuery
import PhraseArt.query.Dto.Category.VideoOnDemandQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class MyBatisVideoOnDemandQuery(
    @Autowired private val videoOnDemandQueryMapper: VideoOnDemandQueryMapper
) : VideoOnDemandQuery {
    @Value("\${aws.bucket_name}")
    val bucketName: String = ""

    @Value("\${aws.s3_root_url}")
    val s3RootUrl: String = ""

    override fun findAllVideoOnDemands(): List<VideoOnDemandQueryDto> {
        return videoOnDemandQueryMapper.selectAll().map {
            daoToVideoOnDemand(it)
        }
    }

    private fun daoToVideoOnDemand(dao: VideoOnDemandQueryDao): VideoOnDemandQueryDto {
        return VideoOnDemandQueryDto(
            dao.nameKey,
            dao.name,
            "${s3RootUrl}/${bucketName}${dao.imagePath}",
            dao.url,
            dao.appDeepLink
        )
    }
}
