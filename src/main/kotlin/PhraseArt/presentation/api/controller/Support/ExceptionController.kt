package PhraseArt.presentation.api.controller.Support

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

// TODO
// 時間がなかったので、ひとまず簡易的にControllerでHandlingするようにしている。
// 落ち着いたらちゃんとErrorクラスを設計してResolverに処理させるように改修する。
open class ExceptionController {
    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(exception: IllegalArgumentException): MutableMap<String, Any?> {
        return badRequestErrorResponse(exception.message)
    }

    @ExceptionHandler(IllegalStateException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(exception: IllegalStateException): MutableMap<String, Any?> {
        return badRequestErrorResponse(exception.message)
    }

    private fun badRequestErrorResponse(exceptionMessage: String?): MutableMap<String, Any?> {
        return mutableMapOf("status" to 400, "error" to "Bas Request", "message" to exceptionMessage)
    }
}
