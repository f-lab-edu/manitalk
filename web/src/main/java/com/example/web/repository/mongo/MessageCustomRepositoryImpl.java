package com.example.web.repository.mongo;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import static com.example.web.constant.MessageField.*;

@Repository
@RequiredArgsConstructor
public class MessageCustomRepositoryImpl implements MessageCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Integer aggregateMissionCount(Integer roomId, Integer userId, String mission) {
        Aggregation aggregation = createAggregation(roomId, userId, mission);
        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "messages", Document.class);

        return extractMissionCount(results);
    }

    private Aggregation createAggregation(Integer roomId, Integer userId, String mission) {
        return Aggregation.newAggregation(
                Aggregation.match(Criteria.where(ROOM_ID).is(roomId).and(USER_ID).is(userId)),
                Aggregation.group(USER_ID)
                        .sum(ConditionalOperators.Cond.when(regexMatchExpression(mission)).then(1).otherwise(0))
                        .as(RESULT),
                Aggregation.project(RESULT)
        );
    }

    private AggregationExpression regexMatchExpression(String pattern) {
        return context -> new Document("$regexMatch", new Document("input", "$" + SEARCH).append("regex", pattern));
    }

    private Integer extractMissionCount(AggregationResults<Document> results) {
        if (results.getMappedResults().isEmpty()) {
            return 0;
        }
        Document result = results.getMappedResults().getFirst();
        return result.getInteger(RESULT, 0);
    }
}
