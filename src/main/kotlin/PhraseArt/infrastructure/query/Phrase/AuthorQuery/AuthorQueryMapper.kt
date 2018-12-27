package PhraseArt.infrastructure.query.Author.AuthorQuery

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface AuthorQueryMapper {
    fun selectAllBy(
        @Param("categoryId") categoryId: String?,
        @Param("subcategoryName") subcategoryName: String?,
        @Param("word") word: String,
        @Param("limit") limit: Int
    ): List<AuthorDao>
}
