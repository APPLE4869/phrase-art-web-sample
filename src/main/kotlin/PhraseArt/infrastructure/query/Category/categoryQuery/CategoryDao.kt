package PhraseArt.infrastructure.query.Category.categoryQuery

// TODO : mybatis-configで正しく設定すれば、TimestampをLocalDateTime型に変換できるはずなのだが、うまくいかないので、後日改めて対応する。
// import java.time.LocalDateTime

data class CategoryDao(
    val id: String,
    val name: String
)
