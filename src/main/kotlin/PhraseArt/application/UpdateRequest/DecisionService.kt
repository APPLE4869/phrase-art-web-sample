package PhraseArt.application.UpdateRequest

import PhraseArt.domain.updateRequest.*
import PhraseArt.domain.user.User
import PhraseArt.domain.user.UserId
import PhraseArt.domain.user.UserRepository
import PhraseArt.support.BadRequestException
import PhraseArt.support.ForbiddenException
import PhraseArt.support.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DecisionService(
        @Autowired val updateRequestRepository : UpdateRequestRepository,
        @Autowired val userRepository : UserRepository,
        @Autowired val decisionRepository : DecisionRepository
) {
    @Transactional
    fun approve(updateRequestId: String, principalName: String) {
        val (updateRequest, user) = targetRequestAndUser(UpdateRequestId(updateRequestId), UserId(principalName))

        if (!updateRequest.isDecidable())
            throw BadRequestException("判定期限は終了しています。")

        val currentDecision = updateRequest.currentDecision(user.id)

        if (currentDecision != null) {
            if (currentDecision.isApproved())
                return

            if (currentDecision.isRejected())
                updateRequest.cancelDecision(currentDecision)
                decisionRepository.remove(currentDecision)
        }

        val decision = updateRequest.approve(decisionRepository.nextIdentity(), user.id)
        decisionRepository.store(decision)
    }

    // TODO
    // 現状だとUpdateRequestとDecisionを別の集約として扱っていて、無駄に複雑になってしまっているので、
    // Decisionは素直にUpdateRequestの内部に追加して、UpdateRequestをStoreするだけの処理にする。
    @Transactional
    fun reject(updateRequestId: String, principalName: String) {
        val (updateRequest, user) = targetRequestAndUser(UpdateRequestId(updateRequestId), UserId(principalName))

        val currentDecision = updateRequest.currentDecision(user.id)

        if (!updateRequest.isDecidable())
            throw BadRequestException("判定期限は終了しています。")

        // すでに決定済みの場合
        if (currentDecision != null) {
            if (currentDecision.isRejected())
                return

            if (currentDecision.isApproved())
                updateRequest.cancelDecision(currentDecision)
                decisionRepository.remove(currentDecision)
        }

        val decision = updateRequest.reject(decisionRepository.nextIdentity(), user.id)
        decisionRepository.store(decision)
    }

    // TODO : メソッド名が残念なので、修正する。
    private fun targetRequestAndUser(updateRequestId: UpdateRequestId, userId: UserId): Pair<UpdateRequest, User> {
        val updateRequest = updateRequestRepository.requestOfId(updateRequestId) ?:
            throw NotFoundException("該当する申請は存在しません。")

        val user = userRepository.userOfId(userId) ?:
            throw ForbiddenException("申請に同意する権限がありません。")

        return Pair(updateRequest, user)
    }
}
