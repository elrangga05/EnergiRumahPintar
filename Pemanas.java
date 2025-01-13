public class Pemanas extends Perangkat {
    public Pemanas(String id, String nama, double konsumsiDaya) {
        super(id, nama, "Pemanas", konsumsiDaya);
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Pemanas - ID: " + getId() + ", Nama: " + getNama() + ", Daya: " + getKonsumsiDaya() + "W");
    }
}