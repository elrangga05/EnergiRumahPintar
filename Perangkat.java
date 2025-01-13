public abstract class Perangkat {
    private String id;
    private String nama;
    private String kategori;
    private double konsumsiDaya;
    private boolean status;

    public Perangkat(String id, String nama, String kategori, double konsumsiDaya) {
        this.id = id;
        this.nama = nama;
        this.kategori = kategori;
        this.konsumsiDaya = konsumsiDaya;
        this.status = false;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public String getKategori() { return kategori; }
    public double getKonsumsiDaya() { return konsumsiDaya; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public double hitungEnergi(int jam) {
        return konsumsiDaya * jam;
    }

    public abstract void tampilkanInfo();
}
