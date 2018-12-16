package PhraseArt.infrastructure.query.Category.categoryQuery

import PhraseArt.domain.category.CategoryId
import PhraseArt.query.Category.CategoryQuery
import PhraseArt.query.Dto.Category.CategoryQueryDto
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired

@Component
class MyBatisCategoryQuery(
        @Autowired private val categoryMapper: CategoryQueryMapper
) : CategoryQuery {
    // カテゴリー数が現段階ではかなり少ないので、一括で全部取得するようにする。
    override fun findAllCategories(): List<CategoryQueryDto> {
        return categoryMapper.selectAll().map {
            daoToCategory(it)
        }
    }

    override fun findCategory(id: CategoryId): CategoryQueryDto? {
        return categoryMapper.selectOneById(id.value)?.let { daoToCategory(it) }
    }

    private fun daoToCategory(dao: CategoryDao): CategoryQueryDto {
        return CategoryQueryDto(
                dao.id,
                dao.name
        )
    }
}
