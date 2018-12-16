package PhraseArt.infrastructure.query.Category.videoOnDemandQuery

import org.apache.ibatis.annotations.Mapper

@Mapper
interface VideoOnDemandQueryMapper {
    fun selectAll(): List<VideoOnDemandQueryDao>
}
