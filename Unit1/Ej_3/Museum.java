import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Museum {
    private String name;
    private String address;
    private String city;
    private String country;
    private final List<Room> rooms = new ArrayList<>();

    public Museum() {
    }

    public Museum(String name, String address, String city, String country) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }

    public void printContents() {
        System.out.printf("Museum: %s%nAddress: %s%nCity: %s%nCountry: %s%n",
                name, address, city, country);

        for (Room room : rooms) {
            System.out.printf("%nRoom: %s%n", room.getName());
            if (room.getArtworks().isEmpty()) {
                System.out.println("  No artworks");
                continue;
            }

            for (Artwork artwork : room.getArtworks()) {
                Author author = artwork.getAuthor();
                System.out.printf("  Artwork: %s%n  Author: %s%n",
                        artwork.getTitle(), author == null ? "Unknown" : author.getName());

                if (artwork instanceof Painting) {
                    Painting painting = (Painting) artwork;
                    System.out.printf("  Painting type: %s%n  Format: %s%n",
                            painting.getType(), painting.getFormat());
                } else if (artwork instanceof Sculpture) {
                    Sculpture sculpture = (Sculpture) artwork;
                    System.out.printf("  Material: %s%n  Style: %s%n",
                            sculpture.getMaterial(), sculpture.getStyle());
                }
            }
        }
    }

    void addRoom(Room room) {
        if (!rooms.contains(room)) {
            rooms.add(room);
        }
    }

    void removeRoom(Room room) {
        rooms.remove(room);
    }
}
