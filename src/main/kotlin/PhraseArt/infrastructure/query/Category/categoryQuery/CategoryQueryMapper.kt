package PhraseArt.infrastructure.query.Category.categoryQuery

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface CategoryQueryMapper {
    fun selectAll(): List<CategoryDao>

    fun selectOneById(@Param("id") id: String): CategoryDao?
}
