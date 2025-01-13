public class Jadwal {
    private String hari;
    private String waktu;
    private Perangkat perangkat;

    public Jadwal(String hari, String waktu, Perangkat perangkat) {
        this.hari = hari;
        this.waktu = waktu;
        this.perangkat = perangkat;
    }

    public Jadwal(String bangunPagi, String time) {
    }

    public String getHari() { return hari; }
    public String getWaktu() { return waktu; }
    public Perangkat getPerangkat() { return perangkat; }

    public void tampilkanJadwal() {
        String infoPerangkat = (perangkat != null) ? perangkat.getNama() : "Tidak ada perangkat";
        System.out.println(String.format("Jadwal: %s %s - Perangkat: %s", hari, waktu, infoPerangkat));
    }
}
