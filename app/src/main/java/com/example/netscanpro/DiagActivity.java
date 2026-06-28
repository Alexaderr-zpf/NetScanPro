package com.example.netscanpro;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiagActivity extends AppCompatActivity {

    private EditText  etTarget;
    private Button    btnStart, btnStop;
    private TextView  tvLastRtt, tvAvg, tvStats;
    private LineChart lineChart;

    private Handler         handler;
    private ExecutorService executor;
    private boolean         running = false;

    private ArrayList<Entry> entries = new ArrayList<>();
    private int  pingCount = 0;
    private int  lostCount = 0;
    private long totalRtt  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diag);

        etTarget  = findViewById(R.id.etTarget);
        btnStart  = findViewById(R.id.btnStart);
        btnStop   = findViewById(R.id.btnStop);
        tvLastRtt = findViewById(R.id.tvLastRtt);
        tvAvg     = findViewById(R.id.tvAvg);
        tvStats   = findViewById(R.id.tvStats);
        lineChart = findViewById(R.id.lineChart);

        setupChart();

        handler  = new Handler(Looper.getMainLooper());
        executor = Executors.newSingleThreadExecutor();

        btnStart.setOnClickListener(v -> startPing());
        btnStop.setOnClickListener(v  -> stopPing());
        btnStop.setEnabled(false);

        if (getSharedPreferences("theme_prefs", MODE_PRIVATE).getBoolean("hacker_mode", false)) {
            applyHackerTheme();
        }
    }

    private void applyHackerTheme() {
        int hackerGreen = android.graphics.Color.parseColor("#39d353");
        ((TextView)findViewById(android.R.id.content).getRootView().findViewWithTag("title_tag")).setTextColor(hackerGreen);
        btnStart.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#1a4d2e")));
        btnStart.setTextColor(hackerGreen);
        
        // Mostrar el prompt solo en modo hacker
        findViewById(R.id.tvPrompt).setVisibility(android.view.View.VISIBLE);
    }

    private void setupChart() {
        lineChart.setBackgroundColor(Color.parseColor("#0d1117"));
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setTouchEnabled(false);
        lineChart.setDrawGridBackground(false);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(500f);
        yAxis.setTextColor(Color.parseColor("#8b949e"));
        yAxis.setGridColor(Color.parseColor("#21262d"));
        lineChart.getAxisRight().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#8b949e"));
        xAxis.setGridColor(Color.parseColor("#21262d"));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        LineDataSet dataSet = new LineDataSet(entries, "RTT");
        dataSet.setColor(Color.parseColor("#39d353"));
        dataSet.setLineWidth(2f);
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        lineChart.setData(new LineData(dataSet));
    }

    private void startPing() {
        String target = etTarget.getText().toString().trim();
        if (target.isEmpty()) {
            Toast.makeText(this, "ingresa una IP o dominio", Toast.LENGTH_SHORT).show();
            return;
        }

        entries.clear();
        pingCount = 0;
        lostCount = 0;
        totalRtt  = 0;
        lineChart.clearValues();
        running = true;
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);

        Runnable pingTask = new Runnable() {
            @Override
            public void run() {
                if (!running) return;
                executor.submit(() -> {
                    try {
                        long start = System.currentTimeMillis();
                        boolean ok = InetAddress.getByName(target).isReachable(1000);
                        long rtt   = System.currentTimeMillis() - start;
                        runOnUiThread(() -> updateChart(ok, rtt));
                    } catch (Exception e) {
                        runOnUiThread(() -> updateChart(false, 0));
                    }
                });
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(pingTask);
    }

    private void updateChart(boolean ok, long rtt) {
        pingCount++;
        int statusColor;
        
        if (!ok) {
            lostCount++;
            tvLastRtt.setText("RTT: TIMEOUT");
            statusColor = Color.parseColor("#f85149"); // Rojo error
            rtt = 0;
        } else {
            totalRtt += rtt;
            tvLastRtt.setText("RTT: " + rtt + " ms");
            
            // Lógica de colores dinámica por latencia
            if (rtt < 50) {
                statusColor = Color.parseColor("#39d353"); // Verde (Excelente)
            } else if (rtt < 150) {
                statusColor = Color.parseColor("#e3b341"); // Amarillo/Naranja (Medio)
            } else {
                statusColor = Color.parseColor("#f85149"); // Rojo (Pobre)
            }
        }
        
        tvLastRtt.setTextColor(statusColor);

        long avg = (pingCount - lostCount) > 0 ? totalRtt / (pingCount - lostCount) : 0;
        tvAvg.setText("avg: " + avg + " ms");
        tvStats.setText("enviados: " + pingCount + "  perdidos: " + lostCount);

        if (entries.size() >= 60) entries.remove(0);
        entries.add(new Entry(pingCount, rtt));

        LineDataSet dataSet = new LineDataSet(entries, "RTT");
        dataSet.setColor(statusColor); // La línea cambia al color del último ping
        dataSet.setLineWidth(2f);
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        
        // Efecto degradado opcional para la línea (pro)
        dataSet.setDrawFilled(true);
        dataSet.setFillAlpha(20);
        dataSet.setFillColor(statusColor);

        lineChart.setData(new LineData(dataSet));
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }

    private void stopPing() {
        running = false;
        handler.removeCallbacksAndMessages(null);
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        tvLastRtt.setText("RTT: --");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPing();
        executor.shutdownNow();
    }
}
