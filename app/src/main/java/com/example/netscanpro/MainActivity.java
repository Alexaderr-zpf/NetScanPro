package com.example.netscanpro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
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

        findViewById(R.id.tvCredits).setOnClickListener(v -> showCredits());

         findViewById(R.id.tvCredits).setOnLongClickListener(v -> {
            if (getSharedPreferences("theme_prefs", MODE_PRIVATE).getBoolean("hacker_mode", false)) {
                android.widget.Toast.makeText(this, "root@android:~$ exit\n[ logging out... ]", android.widget.Toast.LENGTH_SHORT).show();
                getSharedPreferences("theme_prefs", MODE_PRIVATE).edit().putBoolean("hacker_mode", false).apply();
                
                
                recreate();
                return true;
            }
            return false;
        });

        
        if (getSharedPreferences("theme_prefs", MODE_PRIVATE).getBoolean("hacker_mode", false)) {
            applyHackerTheme();
        }

         findViewById(R.id.tvTitle).setOnClickListener(new android.view.View.OnClickListener() {
            int taps = 0;
            @Override
            public void onClick(android.view.View v) {
                taps++;
                if (taps == 7) {
                    getSharedPreferences("theme_prefs", MODE_PRIVATE).edit().putBoolean("hacker_mode", true).apply();
                    android.widget.Toast.makeText(MainActivity.this, 
                        "Modo \"Loco Linux\" activado",
                        android.widget.Toast.LENGTH_LONG).show();
                    applyHackerTheme();
                    taps = 0;
                }
            }
        });
    }

    private void applyHackerTheme() {
        int hackerGreen = android.graphics.Color.parseColor("#39d353");
        tvIpInfo.setText("root@netscan-pro:~# id 0 (root)");
        tvIpInfo.setTextColor(hackerGreen);
        ((TextView)findViewById(R.id.tvTitle)).setTextColor(hackerGreen);
        btnScan.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#1a4d2e")));
        btnScan.setTextColor(hackerGreen);
        btnDiag.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#1a4d2e")));
        btnDiag.setTextColor(hackerGreen);
        btnHistorial.setTextColor(hackerGreen);
        ((TextView)findViewById(R.id.tvCredits)).setTextColor(hackerGreen);
    }

    private void showCredits() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(android.graphics.Color.parseColor("#0d1117"));
        layout.setPadding(60, 60, 60, 60);

         getSharedPreferences("theme_prefs", MODE_PRIVATE).getBoolean("hacker_mode", false);
        int accentColor = isHackerMode ? android.graphics.Color.parseColor("#39d353") : android.graphics.Color.parseColor("#D1D1D1");

        if (isHackerMode) {
            
            TextView tvRoot = new TextView(this);
            tvRoot.setText("root@netscan-pro:~# ls /credits\n" +
                          "AUTHORS  DESCRIPTION  VERSION\n\n" +
                          "root@netscan-pro:~# cat AUTHORS");
            tvRoot.setTextColor(accentColor);
            tvRoot.setTypeface(android.graphics.Typeface.MONOSPACE);
            tvRoot.setTextSize(12);
            layout.addView(tvRoot);

            TextView tvAuthorsNames = new TextView(this);
            tvAuthorsNames.setText("Arcos Alexander\n" +
                    "Valdivia Roger\n" +
                    "Mansillas Jhordan\n" +
                    "Gonzales Bruno\n" +
                    "Portugal Aldeir");
            tvAuthorsNames.setTextColor(android.graphics.Color.parseColor("#8b949e"));
            tvAuthorsNames.setTypeface(android.graphics.Typeface.MONOSPACE);
            tvAuthorsNames.setTextSize(13);
            tvAuthorsNames.setPadding(30, 20, 0, 30);
            layout.addView(tvAuthorsNames);

            TextView tvDescPrompt = new TextView(this);
            tvDescPrompt.setText("root@netscan-pro:~# cat DESCRIPTION");
            tvDescPrompt.setTextColor(accentColor);
            tvDescPrompt.setTypeface(android.graphics.Typeface.MONOSPACE);
            tvDescPrompt.setTextSize(12);
            layout.addView(tvDescPrompt);

            TextView tvDescText = new TextView(this);
            tvDescText.setText("Proyecto Final de Programacion\nMovil para Redes v1.0");
            tvDescText.setTextColor(android.graphics.Color.parseColor("#8b949e"));
            tvDescText.setTypeface(android.graphics.Typeface.MONOSPACE);
            tvDescText.setTextSize(13);
            tvDescText.setPadding(30, 10, 0, 20);
            layout.addView(tvDescText);
            
            TextView tvVersion = new TextView(this);
            tvVersion.setText("root@netscan-pro:~# ./version --short\nNetScan_PRO_v1.0");
            tvVersion.setTextColor(accentColor);
            tvVersion.setTypeface(android.graphics.Typeface.MONOSPACE);
            tvVersion.setTextSize(11);
            layout.addView(tvVersion);

        } else {
            // DISEÑO NORMAL 
            TextView header = new TextView(this);
            header.setText(">>> CREDITS <<<");
            header.setTextColor(accentColor);
            header.setTypeface(android.graphics.Typeface.MONOSPACE, android.graphics.Typeface.BOLD);
            header.setTextSize(16);
            header.setGravity(android.view.Gravity.CENTER);
            header.setPadding(0, 0, 0, 40);
            layout.addView(header);

            // Título de Autores
            TextView tvAuthorsTitle = new TextView(this);
            tvAuthorsTitle.setText("AUTHORS:");
            tvAuthorsTitle.setTextColor(accentColor);
            tvAuthorsTitle.setTypeface(android.graphics.Typeface.MONOSPACE, android.graphics.Typeface.BOLD);
            tvAuthorsTitle.setTextSize(13);
            tvAuthorsTitle.setGravity(android.view.Gravity.CENTER);
            layout.addView(tvAuthorsTitle);

            // Nombres en Gris Oscuro y Centrados
            TextView tvAuthorsNames = new TextView(this);
            tvAuthorsNames.setText("Arcos Alexander\n" +
                    "Valdivia Roger\n" +
                    "Mansillas Jhordan\n" +
                    "Gonzales Bruno\n" +
                    "Portugal Aldeir\n");
            tvAuthorsNames.setTextColor(android.graphics.Color.parseColor("#8b949e"));
            tvAuthorsNames.setTypeface(android.graphics.Typeface.MONOSPACE);
            tvAuthorsNames.setTextSize(13);
            tvAuthorsNames.setGravity(android.view.Gravity.CENTER);
            layout.addView(tvAuthorsNames);

            // Título de Descripción
            TextView tvDescTitle = new TextView(this);
            tvDescTitle.setText("DESCRIPTION:");
            tvDescTitle.setTextColor(accentColor);
            tvDescTitle.setTypeface(android.graphics.Typeface.MONOSPACE, android.graphics.Typeface.BOLD);
            tvDescTitle.setTextSize(13);
            tvDescTitle.setGravity(android.view.Gravity.CENTER);
            layout.addView(tvDescTitle);

            // Texto de Descripción en Gris y Centrado
            TextView tvDescText = new TextView(this);
            tvDescText.setText("Proyecto Final de Programación\nMóvil para Redes\n");
            tvDescText.setTextColor(android.graphics.Color.parseColor("#8b949e"));
            tvDescText.setTypeface(android.graphics.Typeface.MONOSPACE);
            tvDescText.setTextSize(13);
            tvDescText.setGravity(android.view.Gravity.CENTER);
            layout.addView(tvDescText);

            // Footer centrado
            TextView footer = new TextView(this);
            footer.setText("\nNetScan Pro Engine v1.0");
            footer.setTextColor(android.graphics.Color.parseColor("#21262d"));
            footer.setTypeface(android.graphics.Typeface.MONOSPACE);
            footer.setTextSize(10);
            footer.setGravity(android.view.Gravity.CENTER);
            layout.addView(footer);
        }

        builder.setView(layout);
        builder.show();
    }
}
