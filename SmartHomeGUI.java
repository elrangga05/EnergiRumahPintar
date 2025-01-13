import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SmartHomeGUI {
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private ManajemenPerangkat manajemenPerangkat;
    private ManajemenJadwal manajemenJadwal;
    private DefaultTableModel perangkatModel;
    private DefaultTableModel jadwalModel;

    public SmartHomeGUI() {
        manajemenPerangkat = new ManajemenPerangkat();
        manajemenJadwal = new ManajemenJadwal();
        initGUI();
    }

    private void initGUI() {
        frame = new JFrame("Sistem Manajemen Rumah Pintar");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        // Tab untuk Pengelolaan Perangkat
        JPanel perangkatPanel = new JPanel(new BorderLayout());
        perangkatModel = new DefaultTableModel(new String[]{"ID", "Nama", "Kategori", "Daya (W)", "Status"}, 0);
        JTable perangkatTable = new JTable(perangkatModel);
        perangkatPanel.add(new JScrollPane(perangkatTable), BorderLayout.CENTER);

        JPanel perangkatButtons = new JPanel();
        JButton tambahPerangkatBtn = new JButton("Tambah Perangkat");
        JButton hapusPerangkatBtn = new JButton("Hapus Perangkat");
        JButton toggleStatusBtn = new JButton("Ubah Status (ON/OFF)");
        perangkatButtons.add(tambahPerangkatBtn);
        perangkatButtons.add(hapusPerangkatBtn);
        perangkatButtons.add(toggleStatusBtn);
        perangkatPanel.add(perangkatButtons, BorderLayout.SOUTH);

        tambahPerangkatBtn.addActionListener(e -> tambahPerangkat());
        hapusPerangkatBtn.addActionListener(e -> hapusPerangkat(perangkatTable));
        toggleStatusBtn.addActionListener(e -> toggleStatus(perangkatTable));

        tabbedPane.addTab("Pengelolaan Perangkat", perangkatPanel);

        // Tab untuk Pengaturan Jadwal
        JPanel jadwalPanel = new JPanel(new BorderLayout());
        jadwalModel = new DefaultTableModel(new String[]{"Hari", "Waktu", "Perangkat"}, 0);
        JTable jadwalTable = new JTable(jadwalModel);
        jadwalPanel.add(new JScrollPane(jadwalTable), BorderLayout.CENTER);

        JPanel jadwalButtons = new JPanel();
        JButton tambahJadwalBtn = new JButton("Tambah Jadwal");
        JButton hapusJadwalBtn = new JButton("Hapus Jadwal");
        jadwalButtons.add(tambahJadwalBtn);
        jadwalButtons.add(hapusJadwalBtn);
        jadwalPanel.add(jadwalButtons, BorderLayout.SOUTH);

        tambahJadwalBtn.addActionListener(e -> tambahJadwal());
        hapusJadwalBtn.addActionListener(e -> hapusJadwal(jadwalTable));

        tabbedPane.addTab("Pengaturan Jadwal", jadwalPanel);

        // Tab untuk Pemantauan Energi
        JPanel energiPanel = new JPanel(new BorderLayout());
        JLabel energiLabel = new JLabel("Total Konsumsi Energi:");
        energiPanel.add(energiLabel, BorderLayout.NORTH);

        JButton hitungEnergiBtn = new JButton("Hitung Konsumsi Energi");
        energiPanel.add(hitungEnergiBtn, BorderLayout.SOUTH);

        hitungEnergiBtn.addActionListener(e -> hitungEnergi(energiLabel));
        tabbedPane.addTab("Pemantauan Energi", energiPanel);

        // Tab untuk Rekomendasi Penghematan
        JPanel rekomendasiPanel = new JPanel(new BorderLayout());
        JTextArea rekomendasiText = new JTextArea();
        rekomendasiText.setEditable(false);
        rekomendasiPanel.add(new JScrollPane(rekomendasiText), BorderLayout.CENTER);

        JButton rekomendasiBtn = new JButton("Lihat Rekomendasi");
        rekomendasiPanel.add(rekomendasiBtn, BorderLayout.SOUTH);

        rekomendasiBtn.addActionListener(e -> tampilkanRekomendasi(rekomendasiText));
        tabbedPane.addTab("Rekomendasi Penghematan", rekomendasiPanel);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private void tambahPerangkat() {
        // Form input untuk perangkat
        String id = JOptionPane.showInputDialog("Masukkan ID Perangkat:");
        String nama = JOptionPane.showInputDialog("Masukkan Nama Perangkat:");
        String kategori = JOptionPane.showInputDialog("Masukkan Kategori (Lampu/AC/Pemanas):");
        double daya = Double.parseDouble(JOptionPane.showInputDialog("Masukkan Konsumsi Daya (Watt):"));

        Perangkat perangkat = switch (kategori.toLowerCase()) {
            case "lampu" -> new Lampu(id, nama, daya);
            case "ac" -> new Ac(id, nama, daya);
            case "pemanas" -> new Pemanas(id, nama, daya);
            default -> {
                JOptionPane.showMessageDialog(frame, "Kategori tidak valid!");
                yield null;
            }
        };
        if (perangkat != null) {
            manajemenPerangkat.tambahPerangkat(perangkat);
            perangkatModel.addRow(new Object[]{id, nama, kategori, daya, "OFF"});
        }
    }

    private void hapusPerangkat(JTable table) {
        int row = table.getSelectedRow();
        if (row != -1) {
            String id = (String) table.getValueAt(row, 0);
            manajemenPerangkat.hapusPerangkat(id);
            perangkatModel.removeRow(row);
        } else {
            JOptionPane.showMessageDialog(frame, "Pilih perangkat untuk dihapus.");
        }
    }

    private void toggleStatus(JTable table) {
        int row = table.getSelectedRow();
        if (row != -1) {
            String id = (String) table.getValueAt(row, 0);
            Perangkat perangkat = manajemenPerangkat.getDaftarPerangkat().stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .orElse(null);
            if (perangkat != null) {
                perangkat.setStatus(!perangkat.isStatus());
                table.setValueAt(perangkat.isStatus() ? "ON" : "OFF", row, 4);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Pilih perangkat untuk mengubah status.");
        }
    }

    private void tambahJadwal() {
        // Form input untuk jadwal
        String id = JOptionPane.showInputDialog("Masukkan ID Perangkat:");
        Perangkat perangkat = manajemenPerangkat.getDaftarPerangkat().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (perangkat == null) {
            JOptionPane.showMessageDialog(frame, "Perangkat tidak ditemukan.");
            return;
        }
        String hari = JOptionPane.showInputDialog("Masukkan Hari (Senin/Minggu):");
        String waktu = JOptionPane.showInputDialog("Masukkan Waktu (HH:mm):");
        manajemenJadwal.tambahJadwal(new Jadwal(hari, waktu, perangkat));
        jadwalModel.addRow(new Object[]{hari, waktu, perangkat.getNama()});
    }

    private void hapusJadwal(JTable table) {
        int row = table.getSelectedRow();
        if (row != -1) {
            jadwalModel.removeRow(row);
        } else {
            JOptionPane.showMessageDialog(frame, "Pilih jadwal untuk dihapus.");
        }
    }

    private void hitungEnergi(JLabel label) {
        int jam = Integer.parseInt(JOptionPane.showInputDialog("Masukkan durasi (jam):"));
        double totalEnergi = manajemenPerangkat.getDaftarPerangkat().stream()
                .filter(Perangkat::isStatus)
                .mapToDouble(p -> p.hitungEnergi(jam))
                .sum();
        label.setText("Total Konsumsi Energi: " + totalEnergi + " watt-jam");
    }

    private void tampilkanRekomendasi(JTextArea area) {
        StringBuilder rekomendasi = new StringBuilder();
        for (Perangkat p : manajemenPerangkat.getDaftarPerangkat()) {
            if (!p.isStatus()) {
                rekomendasi.append("Perangkat ").append(p.getNama()).append(" sudah mati, tidak perlu dimatikan.\n");
            } else {
                rekomendasi.append("Matikan perangkat ").append(p.getNama()).append(" untuk menghemat energi.\n");
            }
        }
        area.setText(rekomendasi.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SmartHomeGUI::new);
    }

    public void tampilkanGUI() {
    }
}
