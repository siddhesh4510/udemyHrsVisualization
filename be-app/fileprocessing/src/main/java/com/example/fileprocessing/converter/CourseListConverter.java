package com.example.fileprocessing.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CourseListConverter implements DynamoDBTypeConverter<String, List<Map<String, Map<String, String>>>> {

    private final ObjectMapper objectMapper;

    public CourseListConverter() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String convert(List<Map<String, Map<String, String>>> courseList) {
        try {
            return objectMapper.writeValueAsString(courseList);
        } catch (JsonProcessingException e) {
            // Handle the exception accordingly
            throw new IllegalArgumentException("Failed to convert course_list to JSON string", e);
        }
    }

    @Override
    public List<Map<String, Map<String, String>>> unconvert(String json) {
        try {
            return objectMapper.readValue(json, List.class);
        } catch (IOException e) {
            // Handle the exception accordingly
            throw new IllegalArgumentException("Failed to convert JSON string to course_list", e);
        }
    }
}

