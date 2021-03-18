public class Business {
    int id;
    String name;
    String address;
    String kved;
    String stan;

    public Business(int id, String name, String address, String kved, String stan) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.kved = kved;
        this.stan = stan;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Назва: %s | Адреса: %s | КВЕД: %s | Стан: %s",
                this.id, this.name, this.address, this.kved, this.stan);
    }
}
