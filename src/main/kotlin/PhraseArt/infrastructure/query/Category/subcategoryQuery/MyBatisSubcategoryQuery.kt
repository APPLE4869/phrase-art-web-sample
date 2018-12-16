package PhraseArt.infrastructure.query.Category.subcategoryQuery

import PhraseArt.infrastructure.query.Category.videoOnDemandQuery.VideoOnDemandQueryDao
import PhraseArt.query.Category.SubcategoryQuery
import PhraseArt.query.Dto.Category.SubcategoryQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MyBatisSubcategoryQuery(
    @Autowired private val subcategoryMapper: SubcategoryQueryMapper
) : SubcategoryQuery {
    override fun findAllSubcategories(categoryId: String, offset: Int): List<SubcategoryQueryDto> {
        return subcategoryMapper.selectAllByCategoryId(categoryId, 20, offset).map {
            daoToSubcategory(it, null)
        }
    }

    override fun findSubcategory(id: String): SubcategoryQueryDto? {
        return subcategoryMapper.selectOneById(id)?.let {
            val videoOnDemandNameKeys = subcategoryMapper.selectAllVideoOnDemandNameKeysBySubcategoryId(it.id)
            daoToSubcategory(it, videoOnDemandNameKeys)
        }
    }

    private fun daoToSubcategory(dao: SubcategoryQueryDao, videoOnDemandNameKeys: List<String>?): SubcategoryQueryDto {
        return SubcategoryQueryDto(
                dao.id,
                dao.categoryId,
                dao.categoryName,
                dao.name,
                dao.imagePath,
                dao.introduction,
                videoOnDemandNameKeys
        )
    }
}
