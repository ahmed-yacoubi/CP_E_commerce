package ahmed.yaqoubi.controlPanel.model;

public class Category {

    private String id;
    private String name;
    private String desc;
    private String image;
    private boolean isSmall;

    public Category() {
    }

    public Category(String  id, String name, String desc, String  image, boolean isSmall) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.isSmall = isSmall;
    }

    public String  getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String  getImage() {
        return image;
    }

    public void setImage(String  image) {
        this.image = image;
    }

    public boolean isSmall() {
        return isSmall;
    }

    public void setSmall(boolean small) {
        isSmall = small;
    }
}
