package PhraseArt.application.Category

import PhraseArt.domain.category.CategoryId
import PhraseArt.query.Category.CategoryQuery
import PhraseArt.query.Dto.Category.CategoryQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryService(
        @Autowired val categoryQuery : CategoryQuery
) {
    fun findAllCategories(): List<CategoryQueryDto> {
        return categoryQuery.findAllCategories()
    }

    fun findCategory(id: String): CategoryQueryDto? {
        return categoryQuery.findCategory(CategoryId(id))
    }
}
