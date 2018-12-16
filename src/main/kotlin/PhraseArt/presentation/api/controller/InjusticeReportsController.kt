package PhraseArt.presentation.api.controller

import PhraseArt.presentation.api.controller.Support.PublicApiController
import PhraseArt.presentation.api.form.InjusticeReportForm
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class InjusticeReportsController : PublicApiController() {
    @PostMapping("/injustice_reports")
    @ResponseStatus(value = HttpStatus.OK)
    fun report(@Valid @RequestBody form: InjusticeReportForm) {
        // TODO : 不正報告内容の通知処理を記述する。
    }
}
