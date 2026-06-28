package com.example.netscanpro;

import android.os.Bundle;
import android.os.Environment;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.*;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ScanActivity extends AppCompatActivity {

    private RecyclerView    recyclerView;
    private HostAdapter     adapter;
    private List<HostModel> hostList;
    private ProgressBar     progressBar;
    private TextView        tvStatus, tvIpInfo;
    private Button          btnScan, btnExport;
    private ScanDatabase    db;
    private String          subnetBase;
    private AtomicInteger   scanned, alive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar  = findViewById(R.id.progressBar);
        tvStatus     = findViewById(R.id.tvStatus);
        tvIpInfo     = findViewById(R.id.tvIpInfo);
        btnScan      = findViewById(R.id.btnScan);
        btnExport    = findViewById(R.id.btnExport);

        hostList = new ArrayList<>();
        adapter  = new HostAdapter(hostList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        db = new ScanDatabase(this);

        subnetBase = NetworkUtils.getSubnetBase(this);
        String localIp = NetworkUtils.getLocalIp(this);
        if (localIp != null) {
            tvIpInfo.setText("ip: " + localIp + "  |  subnet: " + subnetBase + ".0/24");
        } else {
            tvIpInfo.setText("sin conexión WiFi");
        }

        btnScan.setOnClickListener(v -> startScan());
        btnExport.setOnClickListener(v -> exportResults());

        if (getSharedPreferences("theme_prefs", MODE_PRIVATE).getBoolean("hacker_mode", false)) {
            applyHackerTheme();
        }
    }

    private void applyHackerTheme() {
        int hackerGreen = android.graphics.Color.parseColor("#39d353");
        tvStatus.setTextColor(hackerGreen);
        progressBar.setProgressTintList(android.content.res.ColorStateList.valueOf(hackerGreen));
        btnScan.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#1a4d2e")));
        btnScan.setTextColor(hackerGreen);
        btnExport.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#1a4d2e")));
        btnExport.setTextColor(hackerGreen);
        ((TextView)findViewById(android.R.id.content).getRootView().findViewWithTag("title_tag")).setTextColor(hackerGreen);
    }

    private void startScan() {
        if (subnetBase == null) {
            Toast.makeText(this, "conecta a una red WiFi primero", Toast.LENGTH_SHORT).show();
            return;
        }

        adapter.clearHosts();
        progressBar.setMax(254);
        progressBar.setProgress(0);
        scanned = new AtomicInteger(0);
        alive   = new AtomicInteger(0);
        btnScan.setEnabled(false);
        tvStatus.setText("escaneando…");

        ExecutorService executor = Executors.newFixedThreadPool(50);

        for (int i = 1; i <= 254; i++) {
            final String ip = subnetBase + "." + i;
            executor.submit(() -> {
                try {
                    InetAddress addr = InetAddress.getByName(ip);
                    long start   = System.currentTimeMillis();
                    boolean reachable = addr.isReachable(500);
                    long rtt = System.currentTimeMillis() - start;

                    int done = scanned.incrementAndGet();
                    if (reachable) alive.incrementAndGet();

                    // Crear el host primero
                    HostModel host = new HostModel(ip, reachable, rtt);

                    // Si está activo, intentar resolver hostname con más agresividad
                    if (reachable) {
                        try {
                            // Intentamos obtener el nombre canónico (a veces más efectivo)
                            String name = addr.getCanonicalHostName();
                            
                            // Si el nombre es igual a la IP, probamos con getHostName
                            if (name.equals(ip)) {
                                name = addr.getHostName();
                            }

                            // Solo asignar si resolvió algo distinto a la IP
                            if (name != null && !name.equals(ip)) {
                                host.setHostname(name);
                            }
                        } catch (Exception ignored) {}
                    }

                    runOnUiThread(() -> {
                        if (reachable) adapter.addHost(host);
                        progressBar.setProgress(done);
                        tvStatus.setText("[ " + done + " / 254 ]  hosts: " + alive.get() + " activos");
                        if (done == 254) onScanComplete();
                    });
                } catch (Exception e) {
                    scanned.incrementAndGet();
                }
            });
        }
        executor.shutdown();
    }

    private void onScanComplete() {
        btnScan.setEnabled(true);
        tvStatus.setText("[ COMPLETO ]  " + alive.get() + " / 254 activos");

        String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(new Date());
        db.insertScan(fecha, subnetBase, 254, alive.get());
    }

    private void exportResults() {
        if (hostList.isEmpty()) {
            Toast.makeText(this, "no hay resultados para exportar", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String fileName = "netscan_"
                    + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                    .format(new Date()) + ".txt";
            File file = new File(
                    Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS), fileName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write("NetScan Pro — Resultados\n");
            bw.write("Subnet: " + subnetBase + ".0/24\n\n");
            for (HostModel h : hostList) {
                bw.write(h.getIp() + "  ALIVE  " + h.getRttMs() + "ms\n");
            }
            bw.close();
            Toast.makeText(this, "guardado en Descargas: " + fileName, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "error al exportar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
