package PhraseArt.infrastructure.domain.user

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface UserDomainMapper {
    fun insertUser(
        @Param("id") id: String,
        @Param("username") username: String,
        @Param("hashedPassword") hashedPassword: String
    )

    fun insertOrUpdateProfile(
        @Param("id") id: String,
        @Param("userId") userId: String,
        @Param("imagePath") imagePath: String
    )

    fun updateUser(
        @Param("id") id: String,
        @Param("username") username: String,
        @Param("hashedPassword") hashedPassword: String
    )

    fun selectOneById(@Param("id") id: String): UserDao?

    fun selectOneByUsername(@Param("username") username: String): UserDao?
}
