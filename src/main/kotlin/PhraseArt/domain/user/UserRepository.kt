package PhraseArt.domain.user

interface UserRepository {
    fun nextIdentity(): UserId

    fun store(user: User)

    fun userOfId(id: UserId): User?

    fun userOfUsername(username: String): User?
}
