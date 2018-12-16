package PhraseArt.infrastructure.domain.category.category

import PhraseArt.infrastructure.domain.category.subcategory.SubcategoryDomainDao
import org.apache.ibatis.annotations.*

@Mapper
interface CategoryDomainMapper {
    fun selectOneById(@Param("id") id: String): CategoryDomainDao?
}
