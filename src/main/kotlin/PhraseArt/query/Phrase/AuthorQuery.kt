package PhraseArt.query.Phrase

import PhraseArt.domain.user.UserId
import PhraseArt.query.Dto.Phrase.AuthorQueryDto

interface AuthorQuery {
    fun findAllCandidatesAuthors(categoryId: String?, subcategoryName: String?, word: String): List<AuthorQueryDto>
}
