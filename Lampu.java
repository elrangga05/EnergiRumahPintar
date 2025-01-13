public class Lampu extends Perangkat {
    public Lampu(String id, String nama, double konsumsiDaya) {
        super(id, nama, "Lampu", konsumsiDaya);
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Lampu - ID: " + getId() + ", Nama: " + getNama() + ", Daya: " + getKonsumsiDaya() + "W");
    }
}