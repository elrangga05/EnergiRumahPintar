import java.util.ArrayList;

public class ManajemenJadwal {
    private ArrayList<Jadwal> daftarJadwal = new ArrayList<>();

    public void tambahJadwal(Jadwal jadwal) {
        daftarJadwal.add(jadwal);
        System.out.println("Jadwal berhasil ditambahkan.");
    }

    public void tampilkanSemuaJadwal() {
        for (Jadwal jadwal : daftarJadwal) {
            jadwal.tampilkanJadwal();
        }
    }
}