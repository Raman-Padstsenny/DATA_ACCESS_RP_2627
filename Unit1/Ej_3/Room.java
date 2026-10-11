import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room {
    private String name;
    private final List<Artwork> artworks = new ArrayList<>();
    private Museum museum;

    public Room() {
    }

    public Room(String name) {
        this.name = name;
    }

    public Room(String name, List<Artwork> artworks, Museum museum) {
        this.name = name;
        if (artworks != null) {
            for (Artwork artwork : artworks) {
                addArtwork(artwork);
            }
        }
        setMuseum(museum);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artwork> getArtworks() {
        return Collections.unmodifiableList(artworks);
    }

    void addArtwork(Artwork artwork) {
        if (!artworks.contains(artwork)) {
            artworks.add(artwork);
        }
    }

    void removeArtwork(Artwork artwork) {
        artworks.remove(artwork);
    }

    public void addArtworkToRoom(Artwork artwork) {
        if (artwork != null) {
            artwork.setRoom(this);
        }
    }

    public Museum getMuseum() {
        return museum;
    }

    public void setMuseum(Museum museum) {
        if (this.museum == museum) {
            return;
        }
        if (this.museum != null) {
            this.museum.removeRoom(this);
        }
        this.museum = museum;
        if (museum != null) {
            museum.addRoom(this);
        }
    }
}
