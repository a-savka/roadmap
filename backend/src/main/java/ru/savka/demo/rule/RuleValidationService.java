package ru.savka.demo.rule;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RuleValidationService {

    /**
     * Evaluates a set of field-level conditions against a context.
     * <p>
     * All conditions must match (AND logic). A condition with null or "*"
     * expected value always matches regardless of the context value.
     * <p>
     * This service is completely domain-agnostic. It does not know about
     * steps, forms, or any business entities — it only checks whether
     * the given facts (context) satisfy the given constraints (conditions).
     *
     * @param conditions list of field constraints, all must match
     * @param context    map of field name → actual value
     * @return true if all conditions match the context, false otherwise
     */
    public boolean evaluate(List<FieldCondition> conditions, Map<String, String> context) {
        if (conditions == null || conditions.isEmpty()) {
            return true;
        }
        return conditions.stream().allMatch(c -> matches(c, context));
    }

    private boolean matches(FieldCondition condition, Map<String, String> context) {
        String expected = condition.getExpected();
        if (expected == null || "*".equals(expected)) {
            return true;
        }
        String actual = context.get(condition.getField());
        return expected.equals(actual);
    }
}
