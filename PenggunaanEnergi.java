import java.util.ArrayList;

public class PenggunaanEnergi {

    public static void main(String[] args) {
        ManajemenJadwal manajemenJadwal = new ManajemenJadwal();

        Jadwal jadwal1 = new Jadwal("Bangun Pagi", "06:00");
        Jadwal jadwal2 = new Jadwal("Tidur Malam", "22:00");

        manajemenJadwal.tambahJadwal(jadwal1);
        manajemenJadwal.tambahJadwal(jadwal2);

        manajemenJadwal.tampilkanSemuaJadwal();
    }

    // Tambahkan metode hitungTotalEnergi
    public double hitungTotalEnergi(ArrayList<Perangkat> perangkatList, int jam) {
        double totalEnergi = 0;
        for (Perangkat perangkat : perangkatList) {
            // Asumsi setiap perangkat memiliki metode hitungEnergi()
            totalEnergi += perangkat.hitungEnergi(jam);
        }
        return totalEnergi;
    }
}
