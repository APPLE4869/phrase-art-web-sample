package PhraseArt.infrastructure.query.Category.subcategoryQuery

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface SubcategoryQueryMapper {
    fun selectAllByCategoryId(
        @Param("categoryId") categoryId: String,
        @Param("limit") limit: Int,
        @Param("offset") offset: Int
    ): List<SubcategoryQueryDao>

    fun selectOneById(@Param("id") id: String): SubcategoryQueryDao?

    fun selectAllByCategoryIdAndWord(
        @Param("categoryId") categoryId: String?,
        @Param("word") word: String,
        @Param("limit") limit: Int
    ): List<SubcategoryQueryDao>

    fun selectAllVideoOnDemandNameKeysBySubcategoryId(@Param("subcategoryId") subcategoryId: String): List<String>
}
