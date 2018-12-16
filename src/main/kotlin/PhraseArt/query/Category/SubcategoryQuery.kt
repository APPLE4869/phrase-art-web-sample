package PhraseArt.query.Category

import PhraseArt.query.Dto.Category.SubcategoryQueryDto

interface SubcategoryQuery {
    fun findAllSubcategories(categoryId: String, offset: Int=0): List<SubcategoryQueryDto>

    fun findSubcategory(id: String): SubcategoryQueryDto?
}
