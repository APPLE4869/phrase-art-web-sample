package PhraseArt.infrastructure.query.Phrase.PhraseCommentQuery

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface UpdateRequestCommentQueryMapper {
    fun selectAllLatestComments(
        @Param("updateRequestId") updateRequestId: String
    ): List<UpdateRequestCommentQueryDao>

    fun selectAllPreviousComments(
        @Param("updateRequestId") updateRequestId: String,
        @Param("latestCommentId") latestCommentId: String,
        @Param("offset") offset: Int,
        @Param("limit") limit: Int
    ): List<UpdateRequestCommentQueryDao>

    fun selectAllFollowingComments(
        @Param("updateRequestId") updateRequestId: String,
        @Param("latestCommentId") latestCommentId: String
    ): List<UpdateRequestCommentQueryDao>
}
