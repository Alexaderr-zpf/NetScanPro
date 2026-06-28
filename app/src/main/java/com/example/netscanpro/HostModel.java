package com.example.netscanpro;

/**
 * HostModel — representa un host en la red.
 * Usado por HostAdapter para mostrar resultados
 * del Ping Sweep en el RecyclerView.
 */
public class HostModel {

    private String  ip;
    private boolean isAlive;
    private long    rttMs;
    private String  hostname;

    // Constructor completo
    public HostModel(String ip, boolean isAlive, long rttMs, String hostname) {
        this.ip       = ip;
        this.isAlive  = isAlive;
        this.rttMs    = rttMs;
        this.hostname = hostname;
    }

    // Constructor sin hostname (más común)
    public HostModel(String ip, boolean isAlive, long rttMs) {
        this(ip, isAlive, rttMs, null);
    }

    // --- Getters ---
    public String  getIp()        { return ip; }
    public boolean isAlive()      { return isAlive; }
    public long    getRttMs()     { return rttMs; }
    public String  getHostname()  { return hostname; }

    // --- Setters ---
    public void setIp(String ip)            { this.ip = ip; }
    public void setAlive(boolean alive)     { this.isAlive = alive; }
    public void setRttMs(long rttMs)        { this.rttMs = rttMs; }
    public void setHostname(String hostname){ this.hostname = hostname; }
}