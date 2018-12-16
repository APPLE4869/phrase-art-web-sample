package PhraseArt.query.Category

import PhraseArt.domain.category.CategoryId
import PhraseArt.query.Dto.Category.CategoryQueryDto

interface CategoryQuery {
    fun findAllCategories(): List<CategoryQueryDto>

    fun findCategory(id: CategoryId): CategoryQueryDto?
}
