package PhraseArt.domain.support

import PhraseArt.support.AssertionConcern

open class AbstractId : AssertionConcern {
    val value: String

    constructor(anId: String) {
        this.assertArgumentNotEmpty(anId, "識別子を入力してください")
        this.assertArgumentLength(anId, 36, "識別子は36文字である必要があります")

        this.value = anId
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (javaClass != other?.javaClass) return false
        return value == (other as AbstractId).value
    }
}
