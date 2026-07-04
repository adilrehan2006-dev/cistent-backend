package com.assistant.cistent.controller;

import com.assistant.cistent.model.Faculty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/faculty")
@CrossOrigin(origins = "*")
public class FacultyController {

    @GetMapping("/seed")
    public String seedFacultyData() throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ObjectMapper mapper = new ObjectMapper();
        
        ClassPathResource resource = new ClassPathResource("faculty.json");
        List<Faculty> facultyList = mapper.readValue(resource.getInputStream(), new TypeReference<List<Faculty>>() {});
        
        for (Faculty faculty : facultyList) {
            // Updated to match your database field name "emp_ID"
            db.collection("faculty_data").document(faculty.getEMP_ID()).set(faculty);
        }
        return "Database successfully seeded with " + facultyList.size() + " records!";
    }

    @GetMapping("/all")
    public List<Faculty> getAllFaculty() throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("faculty_data").get();
        return future.get().getDocuments().stream()
                     .map(doc -> doc.toObject(Faculty.class))
                     .collect(Collectors.toList());
    }

    @GetMapping("/debug-emails")
    public List<String> debugEmails() throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("faculty_data").get();
        return future.get().getDocuments().stream()
                     .map(doc -> doc.getString("email")) // Match database key "email"
                     .collect(Collectors.toList());
    }

    @GetMapping("/profile")
    public Faculty getProfileByEmail(@RequestParam String email) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        String searchEmail = email.trim().toLowerCase();
        
        ApiFuture<QuerySnapshot> future = db.collection("faculty_data").get();
        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
        
        for (QueryDocumentSnapshot doc : docs) {
            String dbEmail = doc.getString("email"); // Match database key "email"
            if (dbEmail != null && dbEmail.trim().toLowerCase().equals(searchEmail)) {
                return doc.toObject(Faculty.class);
            }
        }
        return null;
    }

    @PostMapping("/update-timings")
    public void updateTimings(@RequestBody Map<String, Object> payload) throws Exception {
        String empId = (String) payload.get("EMP_ID");
        List<String> timings = (List<String>) payload.get("FREE_TIMINGS");
        
        Firestore db = FirestoreClient.getFirestore();
        // Match database key "emp_ID"
        ApiFuture<QuerySnapshot> future = db.collection("faculty_data").whereEqualTo("emp_ID", empId).get();
        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
        
        if (!docs.isEmpty()) {
            // Match database key "free_TIMINGS"
            docs.get(0).getReference().update("free_TIMINGS", timings);
        }
    }
}