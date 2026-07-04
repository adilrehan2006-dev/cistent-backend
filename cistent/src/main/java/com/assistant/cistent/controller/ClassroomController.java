package com.assistant.cistent.controller; // Update to match your package

import com.assistant.cistent.model.Classroom;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/classrooms")
@CrossOrigin(origins = "*")
public class ClassroomController {

    // Changed to @GetMapping so you can visit this directly in your browser!
    @GetMapping("/seed")
    public String seedClassrooms() {
        Firestore db = FirestoreClient.getFirestore();
        int count = 0;
        String[] blocks = {"AB1", "AB2", "CB"};

        // Generate all 225 rooms automatically
        for (String block : blocks) {
            for (int floor = 1; floor <= 5; floor++) {
                for (int roomNum = 1; roomNum <= 15; roomNum++) {
                    // Formats to 101, 102... 515
                    String roomStr = String.format("%d%02d", floor, roomNum); 
                    
                    Classroom room = new Classroom();
                    room.setBlock(block);
                    room.setRoomNo(roomStr);

                    // Add some dummy free timings randomly for testing
                    List<String> timings = new ArrayList<>();
                    if (roomNum % 3 == 0) timings.add("MON: 9:00 AM - 9:50 AM");
                    if (roomNum % 4 == 0) timings.add("TUE: 10:00 AM - 10:50 AM");
                    if (roomNum % 5 == 0) timings.add("WED: 2:00 PM - 2:50 PM");
                    room.setFreeTimings(timings);

                    // Document ID Example: "AB1_101"
                    String docId = block + "_" + roomStr;
                    db.collection("classrooms").document(docId).set(room);
                    count++;
                }
            }
        }
        return "<h1>Success!</h1><p>Successfully seeded " + count + " classrooms into Firestore! You can close this tab now and check your app.</p>";
    }

    // Endpoint for your frontend to fetch the rooms
    @GetMapping("/all")
    public List<Classroom> getAllClassrooms() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        List<Classroom> list = new ArrayList<>();

        ApiFuture<QuerySnapshot> future = db.collection("classrooms").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            list.add(document.toObject(Classroom.class));
        }
        return list;
    }
}