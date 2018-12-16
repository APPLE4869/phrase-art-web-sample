package PhraseArt.presentation.api.form.UpdateRequest

data class PhraseModificationRequestForm(
    val phraseId: String = "",
    val categoryId: String = "",
    val subcategoryName: String = "",
    val phraseContent: String = "",
    val authorName: String = ""
)
