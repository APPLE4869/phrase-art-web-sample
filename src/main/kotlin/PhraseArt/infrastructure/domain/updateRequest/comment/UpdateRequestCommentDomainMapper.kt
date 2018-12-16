package PhraseArt.infrastructure.domain.updateRequest.comment

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface UpdateRequestCommentDomainMapper {
    fun insertComment(
        @Param("id") id: String,
        @Param("userId") userId: String,
        @Param("updateRequestId") updateRequestId: String,
        @Param("content") content: String
    )

    fun updateComment(@Param("id") id: String, @Param("content") content: String)

    fun selectOneById(@Param("id") id: String): UpdateRequestCommentDao?
}
