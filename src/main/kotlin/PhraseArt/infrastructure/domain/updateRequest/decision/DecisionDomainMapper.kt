package PhraseArt.infrastructure.domain.updateRequest.decision

import org.apache.ibatis.annotations.*

@Mapper
interface DecisionDomainMapper {
    @Select("""
        SELECT
            id AS decision_id,
            user_id,
            update_request_id,
            result
        FROM update_request_decisions
        WHERE
            update_request_id = #{updateRequestId}
    """)
    fun allDecisionsOfUpdateRequestId(updateRequestId: String): List<DecisionDao>

    @Select("""
        SELECT
            id AS decision_id,
            user_id,
            update_request_id,
            result
        FROM update_request_decisions
        WHERE
            id = #{id}
    """)
    fun decisionOfId(id: String): DecisionDao

    @Insert("""
        INSERT INTO
            update_request_decisions(id, user_id, update_request_id, result)
        values
            (#{id}, #{userId}, #{updateRequestId}, #{result})
    """)
    fun createDecision(id: String, userId: String, updateRequestId: String, result: String)

    @Update("""
        UPDATE update_request_decisions
        SET user_id = #{userId}
            AND update_request_id = #{updateRequestId}
            AND result = #{result}
        WHERE
            id = #{id}
    """)
    fun updateDecision(id: String, userId: String, updateRequestId: String, result: String)

    @Delete("""
        DELETE FROM update_request_decisions
        WHERE
            id = #{id}
    """)
    fun deleteDecision(id: String)
}
