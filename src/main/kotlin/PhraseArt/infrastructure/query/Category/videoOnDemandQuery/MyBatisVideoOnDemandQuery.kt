package PhraseArt.infrastructure.query.Category.videoOnDemandQuery

import PhraseArt.query.Category.VideoOnDemandQuery
import PhraseArt.query.Dto.Category.VideoOnDemandQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MyBatisVideoOnDemandQuery(
    @Autowired private val videoOnDemandQueryMapper: VideoOnDemandQueryMapper
) : VideoOnDemandQuery {
    override fun findAllVideoOnDemands(): List<VideoOnDemandQueryDto> {
        return videoOnDemandQueryMapper.selectAll().map {
            daoToVideoOnDemand(it)
        }
    }

    private fun daoToVideoOnDemand(dao: VideoOnDemandQueryDao): VideoOnDemandQueryDto {
        return VideoOnDemandQueryDto(
            dao.nameKey,
            dao.name,
            dao.imagePath,
            dao.url
        )
    }
}
