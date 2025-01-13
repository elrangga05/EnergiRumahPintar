public class Ac extends Perangkat {
    public Ac(String id, String nama, double konsumsiDaya) {
        super(id, nama, "AC", konsumsiDaya);
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("AC - ID: " + getId() + ", Nama: " + getNama() + ", Daya: " + getKonsumsiDaya() + "W");
    }
}