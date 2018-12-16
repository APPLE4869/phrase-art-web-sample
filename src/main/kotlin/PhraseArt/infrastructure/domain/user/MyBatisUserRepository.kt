package PhraseArt.infrastructure.domain.user

import PhraseArt.domain.user.Profile
import PhraseArt.domain.user.UserRepository
import PhraseArt.domain.user.User
import PhraseArt.domain.user.UserId
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID

@Component
class MyBatisUserRepository(
    @Autowired private val userMapper: UserDomainMapper
) : UserRepository {
    override fun nextIdentity(): UserId {
        return UserId(uuid())
    }

    override fun store(user: User) {
        if (userMapper.selectOneById(user.id.value) == null) {
            userMapper.insertUser(user.id.value, user.username, user.hashedPassword)
            user.profile?.let { userMapper.insertOrUpdateProfile(uuid(), user.id.value, it.imagePath) }
        } else {
            userMapper.updateUser(user.id.value, user.username, user.hashedPassword)
            user.profile?.let { userMapper.insertOrUpdateProfile(uuid(), user.id.value, it.imagePath) }
        }
    }

    override fun userOfId(id: UserId): User? {
        return userMapper.selectOneById(id.value)?.let { daoToUser(it) }
    }

    override fun userOfUsername(username: String): User? {
        return userMapper.selectOneByUsername(username)?.let { daoToUser(it) }
    }

    private fun daoToUser(dao: UserDao): User {
        return User(
            UserId(dao.id),
            dao.username,
            dao.hashedPassword,
            dao.freezen,
            dao.profileImagePath?.let { Profile(it) }
        )
    }

    private fun uuid(): String {
        return UUID.randomUUID().toString().toUpperCase()
    }
}
