package PhraseArt.infrastructure.query.UpdateRequest.DecisionQuery

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface DecisionQueryMapper {
    @Select("""
        SELECT result FROM update_request_decisions
        WHERE user_id = #{userId}
            AND update_request_id = #{updateRequestId}
        LIMIT 1
    """)
    fun findByUserIdAndPhraseRequestId(userId: String, updateRequestId: String): DecisionDao?
}
