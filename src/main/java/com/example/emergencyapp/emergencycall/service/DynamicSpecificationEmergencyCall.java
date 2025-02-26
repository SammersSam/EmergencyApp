package com.example.emergencyapp.emergencycall.service;

import com.example.emergencyapp.emergencycall.model.EmergencyCall;
import com.example.emergencyapp.emergencycall.model.ResourcesType;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DynamicSpecificationEmergencyCall {
    /**
     * Builds a Specification<EmergencyCall> based on a map of criteria.
     *
     * @param criteria  a map of key-value pairs for filtering (e.g., "status" -> "BUSY")
     * @return a Specification<EmergencyCall> containing the combined predicates
     */
    public static Specification<EmergencyCall> byFilters(Map<String, String> criteria) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            criteria.keySet()
                    .stream()
                    .filter(key -> key.endsWith("From"))
                    .forEach(key -> {
                        String baseKey = key.substring(0, key.length() - 4);
                        if (criteria.containsKey(baseKey + "To")) {
                            String fromValue = criteria.get(baseKey);
                            String toValue = criteria.get(baseKey + "To");
                            handleRangeCriteria(root, builder, predicates, baseKey, fromValue, toValue);
                        }
                    });

            criteria.forEach((key, value) -> {
                if (!key.endsWith("From") && !key.endsWith("To"))
                    handleStringCriteria(root, builder, predicates, key, value);
            });
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
    /**
     * Builds a range-based predicate (e.g., BETWEEN fromValue and toValue)
     * for the specified field and adds it to the list of predicates.
     *
     * @param root        the root of the query (EmergencyCall entity)
     * @param builder     the CriteriaBuilder for creating predicates
     * @param predicates  the list of predicates to which this criterion is added
     * @param field       the name of the field to apply the range filter on
     * @param fromValue   the lower bound of the range
     * @param toValue     the upper bound of the range
     */
    private static void handleRangeCriteria(Root<EmergencyCall> root, CriteriaBuilder builder, List<Predicate> predicates,
                                           String field, String fromValue, String toValue) {
        Path<String> path = getPath(root, field);
        predicates.add(builder.between(path, fromValue, toValue));
    }
    /**
     * Builds a string-based predicate (e.g., LIKE) for the specified field
     * and adds it to the list of predicates.
     *
     * @param root        the root of the query (EmergencyCall entity)
     * @param builder     the CriteriaBuilder for creating predicates
     * @param predicates  the list of predicates to which this criterion is added
     * @param key         the name of the field to apply the filter on
     * @param value       the string value to match
     */
    private static void handleStringCriteria(Root<EmergencyCall> root, CriteriaBuilder
            builder, List<Predicate> predicates,
                                            String key, String value) {
        Path<String> path = getPath(root, key);
        predicates.add(builder.like(builder.lower(path), "%" + value.toLowerCase() + "%"));
    }
    /**
     * Resolves a nested path (e.g., "address.city") in the root entity
     * to a Path object used for building criteria predicates.
     *
     * @param root the root of the query
     * @param key  the dotted path string representing the nested field
     * @param <T>  the type of the resulting Path
     * @return the Path object pointing to the requested field
     */
    private static <T> Path<T> getPath(From<?, ?> root, String key) {
        String[] parts = key.split("\\.");
        Path<T> path = root.get(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            path = path.get(parts[i]);
        }
        return path;
    }


}
