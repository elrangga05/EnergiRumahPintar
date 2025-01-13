import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Inisialisasi manajemen perangkat dan jadwal
        ManajemenPerangkat manajemenPerangkat = new ManajemenPerangkat();
        ManajemenJadwal manajemenJadwal = new ManajemenJadwal();
        PenggunaanEnergi penggunaanEnergi = new PenggunaanEnergi();
        RekomendasiPenghematan rekomendasiPenghematan = new RekomendasiPenghematan();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Sistem Manajemen Rumah Pintar ===");
            System.out.println("1. Kelola Perangkat");
            System.out.println("2. Atur Jadwal");
            System.out.println("3. Lihat Penggunaan Energi");
            System.out.println("4. Rekomendasi Penghematan Energi");
            System.out.println("5. Buka GUI");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = scanner.nextInt();

            switch (pilihan) {
                case 1:
                    kelolaPerangkat(manajemenPerangkat, scanner);
                    break;
                case 2:
                    aturJadwal(manajemenPerangkat, manajemenJadwal, scanner);
                    break;
                case 3:
                    lihatPenggunaanEnergi(manajemenPerangkat, penggunaanEnergi, scanner);
                    break;
                case 4:
                    rekomendasiPenghematan.tampilkanRekomendasi(manajemenPerangkat.getDaftarPerangkat());
                    break;
                case 5:
                    bukaGUI(manajemenPerangkat);
                    break;
                case 0:
                    System.out.println("Keluar dari sistem. Terima kasih!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void kelolaPerangkat(ManajemenPerangkat manajemenPerangkat, Scanner scanner) {
        System.out.println("\n=== Kelola Perangkat ===");
        System.out.println("1. Tambah Perangkat");
        System.out.println("2. Hapus Perangkat");
        System.out.println("3. Lihat Semua Perangkat");
        System.out.print("Pilih menu: ");
        int pilihan = scanner.nextInt();

        switch (pilihan) {
            case 1:
                System.out.print("Masukkan ID Perangkat: ");
                String id = scanner.next();
                System.out.print("Masukkan Nama Perangkat: ");
                String nama = scanner.next();
                System.out.print("Masukkan Kategori (Lampu/AC/Pemanas): ");
                String kategori = scanner.next();
                System.out.print("Masukkan Konsumsi Daya (Watt): ");
                double daya = scanner.nextDouble();

                Perangkat perangkat;
                switch (kategori.toLowerCase()) {
                    case "lampu":
                        perangkat = new Lampu(id, nama, daya);
                        break;
                    case "ac":
                        perangkat = new Ac(id, nama, daya);
                        break;
                    case "pemanas":
                        perangkat = new Pemanas(id, nama, daya);
                        break;
                    default:
                        System.out.println("Kategori tidak valid.");
                        return;
                }
                manajemenPerangkat.tambahPerangkat(perangkat);
                break;
            case 2:
                System.out.print("Masukkan ID Perangkat yang ingin dihapus: ");
                String idHapus = scanner.next();
                manajemenPerangkat.hapusPerangkat(idHapus);
                break;
            case 3:
                manajemenPerangkat.tampilkanSemuaPerangkat();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    private static void aturJadwal(ManajemenPerangkat manajemenPerangkat, ManajemenJadwal manajemenJadwal, Scanner scanner) {
        System.out.println("\n=== Atur Jadwal ===");
        System.out.print("Masukkan ID Perangkat: ");
        String id = scanner.next();
        Perangkat perangkat = null;
        for (Perangkat p : manajemenPerangkat.getDaftarPerangkat()) {
            if (p.getId().equals(id)) {
                perangkat = p;
                break;
            }
        }

        if (perangkat == null) {
            System.out.println("Perangkat tidak ditemukan.");
            return;
        }

        System.out.print("Masukkan Hari (Senin/Minggu): ");
        String hari = scanner.next();
        System.out.print("Masukkan Waktu (HH:mm): ");
        String waktu = scanner.next();

        Jadwal jadwal = new Jadwal(hari, waktu, perangkat);
        manajemenJadwal.tambahJadwal(jadwal);
    }

    private static void lihatPenggunaanEnergi(ManajemenPerangkat manajemenPerangkat, PenggunaanEnergi penggunaanEnergi, Scanner scanner) {
        System.out.print("Masukkan durasi (dalam jam): ");
        int jam = scanner.nextInt();
        double totalEnergi = penggunaanEnergi.hitungTotalEnergi(manajemenPerangkat.getDaftarPerangkat(), jam);
        System.out.println("Total energi yang digunakan: " + totalEnergi + " watt-jam");
    }

    private static void bukaGUI(ManajemenPerangkat manajemenPerangkat) {
        SwingUtilities.invokeLater(() -> {
            SmartHomeGUI gui = new SmartHomeGUI();
            gui.tampilkanGUI();
        });
    }
}
