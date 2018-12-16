package PhraseArt.batch


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import PhraseArt.application.UpdateRequest.UpdateRequestFinishDecisionService

@Component
class UpdateRequestBatch(
        @Autowired private val updateRequestFinishDecisionService : UpdateRequestFinishDecisionService
) {
    @Scheduled(cron = "0 0 */6 * * *", zone = "Asia/Tokyo")
    fun finishRequestsForExpired() {
        // 期限切れの申請を完了させる処理
        updateRequestFinishDecisionService.finishExpiredRequests()
    }
}
