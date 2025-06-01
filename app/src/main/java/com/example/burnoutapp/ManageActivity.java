package com.example.burnoutapp;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.common.collect.Table;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ManageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView textViewCode;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        textViewCode = findViewById(R.id.textView_Code);
        tableLayout = findViewById(R.id.data_table);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        String currentUserId = mAuth.getCurrentUser().getUid();
//        Log.d(TAG, "Current user ID: " + currentUserId);

        db.collection("teams")
                .whereEqualTo("managerId", currentUserId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot teamDoc = queryDocumentSnapshots.getDocuments().get(0);
                        String teamCode = teamDoc.getId();
                        textViewCode.setText("Team Code: " + teamCode);
//                        Log.d(TAG, "Team found: " + teamCode);

                        List<String> memberIds = (List<String>) teamDoc.get("members");

                        if (memberIds != null && !memberIds.isEmpty()) {
//                            Log.d(TAG, "Found " + memberIds.size() + " members.");
                            fetchAndDisplayMembers(memberIds);
                        } else {
//                            Log.d(TAG, "No members found in the team.");
                        }
                    } else {
//                        Log.d(TAG, "No team found for this manager.");
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Failed to fetch team: ", e));
    }

    private void fetchAndDisplayMembers(List<String> memberIds) {
        for (String memberId : memberIds) {
            db.collection("users")
                    .document(memberId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("name");
                            Long burnoutLevel = documentSnapshot.getLong("burnoutLevel");

//                            Log.d(TAG, "Fetched user " + memberId + ": name=" + name + ", burnout=" + burnoutLevel);
                            addTableRow(name != null ? name : "Unnamed", burnoutLevel != null ? String.valueOf(burnoutLevel) : "N/A");
                        } else {
//                            Log.d(TAG, "User not found: " + memberId);
                        }
                    })
                    .addOnFailureListener(e -> Log.e(TAG, "Failed to fetch user " + memberId, e));
        }
    }

    private void addTableRow(String name, String burnoutLevel) {
        TableRow row = new TableRow(this);

        TextView nameView = new TextView(this);
        nameView.setText(name);
        nameView.setPadding(8, 8, 8, 8);

        TextView burnoutView = new TextView(this);
        burnoutView.setText(burnoutLevel);
        burnoutView.setPadding(8, 8, 8, 8);

        row.addView(nameView);
        row.addView(burnoutView);

        tableLayout.addView(row);
    }
}