package PhraseArt.infrastructure.query.Phrase.PhraseCommentQuery

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface PhraseCommentQueryMapper {
    fun selectAllLatestComments(
        @Param("phraseId") phraseId: String
    ): List<PhraseCommentQueryDao>

    fun selectAllPreviousComments(
        @Param("phraseId") phraseId: String,
        @Param("latestCommentId") latestCommentId: String,
        @Param("offset") offset: Int,
        @Param("limit") limit: Int
    ): List<PhraseCommentQueryDao>

    fun selectAllFollowingComments(
        @Param("phraseId") phraseId: String,
        @Param("latestCommentId") latestCommentId: String
    ): List<PhraseCommentQueryDao>
}
