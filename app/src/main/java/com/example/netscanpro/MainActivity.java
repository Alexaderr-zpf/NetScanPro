package com.example.netscanpro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvIpInfo;
    private Button   btnScan, btnDiag, btnHistorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvIpInfo     = findViewById(R.id.tvIpInfo);
        btnScan      = findViewById(R.id.btnScan);
        btnDiag      = findViewById(R.id.btnDiag);
        btnHistorial = findViewById(R.id.btnHistorial);

        String ip     = NetworkUtils.getLocalIp(this);
        String subnet = NetworkUtils.getSubnetBase(this);
        if (ip != null) {
            tvIpInfo.setText("ip: " + ip + "  |  subnet: " + subnet + ".0/24");
        } else {
            tvIpInfo.setText("[ sin conexión WiFi ]");
        }

        btnScan.setOnClickListener(v ->
            startActivity(new Intent(this, ScanActivity.class)));

        btnDiag.setOnClickListener(v ->
            startActivity(new Intent(this, DiagActivity.class)));

        btnHistorial.setOnClickListener(v ->
            startActivity(new Intent(this, HistorialActivity.class)));
    }
}
