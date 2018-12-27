package PhraseArt.application.Phrase

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import PhraseArt.query.Dto.Phrase.AuthorQueryDto
import PhraseArt.query.Phrase.AuthorQuery

@Service
class AuthorService(
    @Autowired val authorQuery : AuthorQuery
) {
    fun findAllCandidatesAuthors(categoryId: String?, subcategoryName: String?, word: String): List<AuthorQueryDto> {
        return authorQuery.findAllCandidatesAuthors(categoryId, subcategoryName, word)
    }
}
