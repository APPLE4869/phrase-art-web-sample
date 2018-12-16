package PhraseArt.domain.phrase

import PhraseArt.domain.category.CategoryId
import PhraseArt.domain.category.SubcategoryId

interface PhraseRepository {
    fun nextIdentity(): PhraseId

    fun phraseOfId(id: PhraseId): Phrase?

    fun phraseOf(categoryId: CategoryId, subcategoryId: SubcategoryId?, content: String, authorName: String): Phrase?

    fun store(phrase: Phrase)

    fun remove(phrase: Phrase)
}
