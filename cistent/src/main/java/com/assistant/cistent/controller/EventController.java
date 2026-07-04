package com.assistant.cistent.controller;

import com.assistant.cistent.model.Event;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    @GetMapping("/all")
    public List<Event> getAllEvents() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        List<Event> list = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = db.collection("events").get();
        
        for (QueryDocumentSnapshot document : future.get().getDocuments()) {
            Event event = document.toObject(Event.class);
            event.setId(document.getId()); // Store the generated ID so frontend can use it to edit/delete
            list.add(event);
        }
        return list;
    }

    @PostMapping("/save")
    public String saveEvent(@RequestBody Event event) {
        Firestore db = FirestoreClient.getFirestore();
        if (event.getId() == null || event.getId().isEmpty()) {
            // New event - let Firebase generate an ID
            db.collection("events").add(event);
        } else {
            // Existing event - update the specific document
            db.collection("events").document(event.getId()).set(event);
        }
        return "Event saved successfully";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEvent(@PathVariable String id) {
        Firestore db = FirestoreClient.getFirestore();
        db.collection("events").document(id).delete();
        return "Event deleted";
    }
}