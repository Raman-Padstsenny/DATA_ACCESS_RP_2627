public class Artwork {
    private String title;
    private Author author;
    private Room room;

    public Artwork() {
    }

    public Artwork(String title, Author author, Room room) {
        this.title = title;
        setAuthor(author);
        setRoom(room);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        if (this.author == author) {
            return;
        }
        if (this.author != null) {
            this.author.removeArtwork(this);
        }
        this.author = author;
        if (author != null) {
            author.addArtwork(this);
        }
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        if (this.room == room) {
            return;
        }
        Room previousRoom = this.room;
        this.room = room;
        if (previousRoom != null) {
            previousRoom.removeArtwork(this);
        }
        if (room != null) {
            room.addArtwork(this);
        }
    }
}
