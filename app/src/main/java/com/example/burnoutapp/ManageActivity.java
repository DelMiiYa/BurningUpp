package com.example.burnoutapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView textViewCode;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private final List<UserModel> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        textViewCode = findViewById(R.id.textView_Code);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(userList, this);
        recyclerView.setAdapter(userAdapter);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        String currentUserId = mAuth.getCurrentUser().getUid();

        db.collection("teams")
                .whereEqualTo("managerId", currentUserId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot teamDoc = queryDocumentSnapshots.getDocuments().get(0);
                        String teamCode = teamDoc.getId();
                        textViewCode.setText("Team Code: " + teamCode);

                        List<String> memberIds = (List<String>) teamDoc.get("members");
                        if (memberIds != null && !memberIds.isEmpty()) {
                            fetchAndDisplayMembers(memberIds);
                        }
                    }
                })
                .addOnFailureListener(e -> Log.e("ManageActivity", "Failed to fetch team: ", e));
    }

    private void fetchAndDisplayMembers(List<String> memberIds) {
        for (String memberId : memberIds) {
            db.collection("users")
                    .document(memberId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("name");
                            String birthday = documentSnapshot.getString("birthday");
                            String gender = documentSnapshot.getString("gender");
                            Long burnout = documentSnapshot.getLong("burnoutLevel");

                            Map<String, Object> basicTest = (Map<String, Object>) documentSnapshot.get("basicTest");
                            Map<String, Object> advancedTest = (Map<String, Object>) documentSnapshot.get("advancedTest");

                            userList.add(new UserModel(
                                    name != null ? name : "Unnamed",
                                    birthday,
                                    gender,
                                    burnout != null ? burnout.intValue() : null,
                                    advancedTest
                            ));
                            userAdapter.notifyItemInserted(userList.size() - 1);
                        }
                    })
                    .addOnFailureListener(e -> Log.e("ManageActivity", "Failed to fetch user: " + memberId, e));
        }
    }
}
