package PhraseArt.infrastructure.domain.phrase.phrase

import org.apache.ibatis.annotations.*

@Mapper
interface PhraseDomainMapper {
    fun selectOneById(@Param("id") id: String): PhraseDao?

    fun selectOneBy(
        @Param("categoryId") categoryId: String,
        @Param("subcategoryId") subcategoryId: String?,
        @Param("content") content: String,
        @Param("authorName") authorName: String
    ): PhraseDao?

    fun selectAllLikesByPhraseId(
        @Param("phraseId") phraseId: String
    ): List<PhraseLikeDao>

    fun selectAllFavoritesByPhraseId(
        @Param("phraseId") phraseId: String
    ): List<PhraseFavoriteDao>

    fun insert(
        @Param("id") id: String,
        @Param("categoryId") categoryId: String,
        @Param("subcategoryId") subcategoryId: String?,
        @Param("authorName") authorName: String,
        @Param("content") content: String
    )

    fun insertLikeWhereNotExists(
        @Param("id") id: String,
        @Param("phraseId") phraseId: String,
        @Param("userId") userId: String
    )

    fun insertFavoriteWhereNotExists(
        @Param("id") id: String,
        @Param("phraseId") phraseId: String,
        @Param("userId") userId: String
    )

    fun update(
        @Param("id") id: String,
        @Param("categoryId") categoryId: String,
        @Param("subcategoryId") subcategoryId: String?,
        @Param("authorName") authorName: String,
        @Param("content") content: String
    )

    fun deleteById(@Param("id") id: String)

    fun deleteCommentsByPhraseId(@Param("phraseId") phraseId: String)

    fun deleteLikesByPhraseId(@Param("phraseId") phraseId: String)

    fun deleteFavoritesByPhraseId(@Param("phraseId") phraseId: String)

    fun deleteLikesWhereNotIn(
        @Param("phraseId") phraseId: String,
        @Param("userIds") userIds: List<String>
    )

    fun deleteFavoritesWhereNotIn(
        @Param("phraseId") phraseId: String,
        @Param("userIds") userIds: List<String>
    )
}
