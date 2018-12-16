package PhraseArt.domain.category

interface CategoryRepository {
    fun categoryOfId(id: CategoryId): Category?
}
