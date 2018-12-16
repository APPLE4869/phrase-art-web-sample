package PhraseArt.domain.user

import PhraseArt.domain.support.Value

class Profile: Value {
    var imagePath: String
        set(value) {
            this.assertArgumentNotEmpty(value, "画像パスを入力してください")
            this.assertArgumentLength(value, 100, "画像パスは100文字以内にしてください")
            field = value
        }

    constructor(anImagePath: String) {
        this.imagePath = anImagePath
    }

    fun changeImage(imagePath: String) {
        this.imagePath = imagePath
    }
}
