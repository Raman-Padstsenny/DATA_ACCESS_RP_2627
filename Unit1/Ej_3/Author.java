import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Author {
    private String name;
    private String nationality;
    private final List<Artwork> artworks = new ArrayList<>();

    public Author() {
    }

    public Author(String name, String nationality) {
        this.name = name;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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
}
