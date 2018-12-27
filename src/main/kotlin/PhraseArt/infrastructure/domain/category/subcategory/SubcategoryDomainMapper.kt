package PhraseArt.infrastructure.domain.category.subcategory

import org.apache.ibatis.annotations.*

@Mapper
interface SubcategoryDomainMapper {
    fun selectOneById(@Param("id") id: String): SubcategoryDomainDao?

    fun selectAllByCategoryId(@Param("categoryId") categoryId: String): List<SubcategoryDomainDao>

    fun selectOneByCategoryIdAndName(
        @Param("categoryId") categoryId: String,
        @Param("name") name: String
    ): SubcategoryDomainDao?

    fun insert(
        @Param("id") id: String,
        @Param("categoryId") categoryId: String,
        @Param("name") name: String,
        @Param("imagePath") imagePath: String?,
        @Param("introduction") introduction: String?,
        @Param("sequence") sequence: Int
    )

    fun update(
        @Param("id") id: String,
        @Param("categoryId") categoryId: String,
        @Param("name") name: String,
        @Param("imagePath") imagePath: String?,
        @Param("introduction") introduction: String?
    )

    //----- 以下、VideoOnDemand関係のメソッド -----//

    fun selectAllVideoOnDemandsByNameKeys(
        @Param("namekeys") nameKeys: List<String>
    ): List<VideoOnDemandDomainDao>

    fun selectAllVideoOnDemandsBySubcategoryId(
        @Param("subcategoryId") subcategoryId: String
    ): List<VideoOnDemandDomainDao>

    fun selectOneVideoOnDemandByNameKey(
        @Param("nameKey") nameKey: String
    ): VideoOnDemandDomainDao?

    fun insertSubcategoryVideoOnDemand(
        @Param("id") id: String,
        @Param("subcategoryId") subcategoryId: String,
        @Param("videoOnDemandId") videoOnDemandId: String
    )

    fun deleteAllSubcategoryVideoOnDemandBySubcategoryId(
        @Param("subcategoryId") subcategoryId: String
    )
}
