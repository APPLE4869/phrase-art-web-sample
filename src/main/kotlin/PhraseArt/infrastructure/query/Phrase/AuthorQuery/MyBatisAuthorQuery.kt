package PhraseArt.infrastructure.query.Author.AuthorQuery

import PhraseArt.query.Dto.Phrase.AuthorQueryDto
import PhraseArt.query.Phrase.AuthorQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MyBatisAuthorQuery(
    @Autowired private val authorQueryMapper: AuthorQueryMapper
) : AuthorQuery {
    override fun findAllCandidatesAuthors(categoryId: String?, subcategoryName: String?, word: String): List<AuthorQueryDto> {
        return authorQueryMapper.selectAllBy(categoryId, subcategoryName, word, 10).map {
            daoToAuthor(it)
        }
    }

    private fun daoToAuthor(dao: AuthorDao): AuthorQueryDto {
        return AuthorQueryDto(
            dao.name
        )
    }
}
