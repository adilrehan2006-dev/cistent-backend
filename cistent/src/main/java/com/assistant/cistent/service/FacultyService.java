package com.assistant.cistent.service;

import com.assistant.cistent.model.Faculty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class FacultyService {

    private static final String COLLECTION_NAME = "faculty_data";

    public String seedFacultyData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource("faculty.json").getInputStream();
            List<Faculty> faculties = mapper.readValue(inputStream, new TypeReference<List<Faculty>>(){});
            
            Firestore db = FirestoreClient.getFirestore();
            
            int count = 0;
            for (Faculty faculty : faculties) {
                db.collection(COLLECTION_NAME).add(faculty);
                count++;
            }
            
            return "Successfully uploaded " + count + " faculty records to Firestore!";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to upload data: " + e.getMessage();
        }
    }
}