package PhraseArt.presentation.api.controller

import PhraseArt.application.UserService
import PhraseArt.presentation.api.controller.Support.ExceptionController
import PhraseArt.presentation.api.form.SignupForm
import PhraseArt.support.security.SecurityConstants.SIGNUP_URL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class SignupController(
        @Autowired val userService: UserService
) : ExceptionController() {
    @PostMapping(SIGNUP_URL)
    @ResponseStatus(value = HttpStatus.OK)
    fun signup(@Valid @RequestBody user: SignupForm) {
        userService.signup(user)
    }
}
