package PhraseArt.infrastructure.query.Phrase.PhraseQuery

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface PhraseQueryMapper {
    fun selectAll(
        @Param("userId") userId: String?,
        @Param("limit") limit: Int,
        @Param("offset") offset: Int
    ): List<PhraseDao>

    fun selectAllFavoriteByUserId(
        @Param("userId") userId: String,
        @Param("limit") limit: Int,
        @Param("offset") offset: Int
    ): List<PhraseDao>

    fun selectAllBySearchWord(
        @Param("userId") userId: String?,
        @Param("searchWord") searchWord: String,
        @Param("limit") limit: Int,
        @Param("offset") offset: Int
    ): List<PhraseDao>

    fun selectAllByCategoryId(
        @Param("categoryId") categoryId: String,
        @Param("userId") userId: String?,
        @Param("limit") limit: Int,
        @Param("offset") offset: Int
    ): List<PhraseDao>

    fun selectAllBySubcategoryId(
        @Param("subcategoryId") subcategoryId: String,
        @Param("userId") userId: String?,
        @Param("limit") limit: Int,
        @Param("offset") offset: Int
    ): List<PhraseDao>

    fun selectOneById(
        @Param("id") id: String,
        @Param("userId") userId: String?
    ): PhraseDao?
}
