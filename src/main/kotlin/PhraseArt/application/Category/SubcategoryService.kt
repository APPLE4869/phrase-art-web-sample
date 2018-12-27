package PhraseArt.application.Category

import PhraseArt.query.Category.SubcategoryQuery
import PhraseArt.query.Dto.Category.SubcategoryQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SubcategoryService(
    @Autowired val subcategoryQuery : SubcategoryQuery
) {
    fun findAllSubcategories(categoryId: String, offset: Int=0): List<SubcategoryQueryDto> {
        return subcategoryQuery.findAllSubcategories(categoryId, offset)
    }

    fun findSubcategory(id: String): SubcategoryQueryDto? {
        return subcategoryQuery.findSubcategory(id)
    }

    fun findAllCandidatesSubcategories(categoryId: String?, word: String): List<SubcategoryQueryDto> {
        return subcategoryQuery.findAllCandidatesSubcategories(categoryId, word)
    }
}
