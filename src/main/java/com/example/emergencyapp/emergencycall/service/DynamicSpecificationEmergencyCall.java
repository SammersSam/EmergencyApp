package com.example.emergencyapp.emergencycall.service;

import com.example.emergencyapp.emergencycall.model.EmergencyCall;
import com.example.emergencyapp.emergencycall.model.ResourcesType;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DynamicSpecificationEmergencyCall {

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

    private static void handleRangeCriteria(Root<EmergencyCall> root, CriteriaBuilder
            builder, List<Predicate> predicates,
                                           String field, String fromValue, String toValue) {
        Path<String> path = getPath(root, field);
        predicates.add(builder.between(path, fromValue, toValue));
    }

    private static void handleStringCriteria(Root<EmergencyCall> root, CriteriaBuilder
            builder, List<Predicate> predicates,
                                            String key, String value) {
        Path<String> path = getPath(root, key);
        predicates.add(builder.like(builder.lower(path), "%" + value.toLowerCase() + "%"));
    }

    private static <T> Path<T> getPath(From<?, ?> root, String key) {
        String[] parts = key.split("\\.");
        Path<T> path = root.get(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            path = path.get(parts[i]);
        }
        return path;
    }


}
