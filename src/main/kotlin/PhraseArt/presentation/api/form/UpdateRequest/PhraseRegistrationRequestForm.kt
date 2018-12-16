package PhraseArt.presentation.api.form.UpdateRequest

data class PhraseRegistrationRequestForm(
    val categoryId: String = "",
    val subcategoryName: String = "",
    val phraseContent: String = "",
    val authorName: String = ""
)
