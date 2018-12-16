package PhraseArt.infrastructure.query.userQuery

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface UserQueryMapper {
    fun selectOneById(@Param("id") id: String): UserQueryDao?
}
