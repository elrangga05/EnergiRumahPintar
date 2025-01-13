import java.util.ArrayList;

public class ManajemenPerangkat {
    private ArrayList<Perangkat> daftarPerangkat = new ArrayList<>();

    public void tambahPerangkat(Perangkat perangkat) {
        for (Perangkat p : daftarPerangkat) {
            if (p.getId().equals(perangkat.getId())) {
                System.out.println("Perangkat dengan ID " + perangkat.getId() + " sudah ada.");
                return;
            }
        }
        daftarPerangkat.add(perangkat);
        System.out.println("Perangkat berhasil ditambahkan: " + perangkat.getNama());
    }

    public void hapusPerangkat(String id) {
        daftarPerangkat.removeIf(perangkat -> perangkat.getId().equals(id));
        System.out.println("Perangkat dengan ID " + id + " telah dihapus.");
    }

    public void tampilkanSemuaPerangkat() {
        for (Perangkat perangkat : daftarPerangkat) {
            perangkat.tampilkanInfo();
        }
    }

    public ArrayList<Perangkat> getDaftarPerangkat() {
        return daftarPerangkat;
    }
}