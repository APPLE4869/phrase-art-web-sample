package PhraseArt.infrastructure.domain.phrase.comment

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface PhraseCommentDomainMapper {
    fun insertComment(
        @Param("id") id: String,
        @Param("userId") userId: String,
        @Param("phraseId") phraseId: String,
        @Param("content") content: String
    )

    fun updateComment(@Param("id") id: String, @Param("content") content: String)

    fun selectOneById(@Param("id") id: String): PhraseCommentDao?
}
