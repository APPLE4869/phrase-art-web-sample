package PhraseArt.infrastructure.query.UpdateRequest.UpdateRequestListQuery

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface UpdateRequestQueryMapper {
    fun selectAllRequesting(
        @Param("limit") limit: Int,
        @Param("offset") offset: Int
    ): List<UpdateRequestListItemDao>

    fun selectAllFinished(
        @Param("limit") limit: Int,
        @Param("offset") offset: Int
    ): List<UpdateRequestListItemDao>
}
