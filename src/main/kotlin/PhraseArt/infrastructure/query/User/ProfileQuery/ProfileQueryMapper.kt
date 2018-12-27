package PhraseArt.infrastructure.query.User.ProfileQuery

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface ProfileQueryMapper {
    fun selectOneByUserId(@Param("userId") userId: String): ProfileQueryDao?
}
