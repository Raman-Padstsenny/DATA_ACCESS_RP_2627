public class Sculpture extends Artwork {
    private Materials material;
    private Styles style;

    public Sculpture() {
    }

    public Sculpture(String title, Author author, Room room,
                     Materials material, Styles style) {
        super(title, author, room);
        this.material = material;
        this.style = style;
    }

    public Materials getMaterial() {
        return material;
    }

    public void setMaterial(Materials material) {
        this.material = material;
    }

    public Styles getStyle() {
        return style;
    }

    public void setStyle(Styles style) {
        this.style = style;
    }
}
