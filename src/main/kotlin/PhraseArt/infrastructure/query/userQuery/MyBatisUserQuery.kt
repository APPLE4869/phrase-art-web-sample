package PhraseArt.infrastructure.query.userQuery

import PhraseArt.domain.user.UserId
import PhraseArt.query.Dto.UserQueryDto
import PhraseArt.query.UserQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MyBatisUserQuery(
    @Autowired val userQueryMapper : UserQueryMapper
) : UserQuery {
    override fun userOfId(id: UserId): UserQueryDto? {
        return userQueryMapper.selectOneById(id.value)?.let { daoToUser(it) }
    }

    private fun daoToUser(dao: UserQueryDao): UserQueryDto {
        return UserQueryDto(
            dao.id,
            dao.username
        )
    }
}
