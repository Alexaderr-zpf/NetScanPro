package com.example.netscanpro;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class HistorialActivity extends AppCompatActivity {

    private ListView     listView;
    private Button       btnClear;
    private ScanDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        listView = findViewById(R.id.listView);
        btnClear = findViewById(R.id.btnClear);
        db       = new ScanDatabase(this);

        loadHistorial();

        btnClear.setOnClickListener(v -> {
            db.deleteAll();
            loadHistorial();
            Toast.makeText(this, "historial borrado", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadHistorial() {
        List<String> scans = db.getAllScans();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                scans
        );
        listView.setAdapter(adapter);

        if (scans.isEmpty()) {
            Toast.makeText(this, "sin escaneos guardados", Toast.LENGTH_SHORT).show();
        }
    }
}
