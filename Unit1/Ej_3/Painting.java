public class Painting extends Artwork {
    private PaintingType type;
    private String format;

    public Painting() {
    }

    public Painting(String title, Author author, Room room,
                    PaintingType type, String format) {
        super(title, author, room);
        this.type = type;
        this.format = format;
    }

    public PaintingType getType() {
        return type;
    }

    public void setType(PaintingType type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
