package th.ac.bodin.ppnt.englock.extra_assets;

public class Product {
    private int imageId;
    private String title;
    private String description;
    private long price;
    private boolean isBought;
    private boolean isSelected;

    public Product(int imageId, String title, String description, long price, boolean isBought, boolean isSelected) {
        this.imageId = imageId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.isBought = isBought;
        this.isSelected = isSelected;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBoughtStat(boolean isBought) {
        this.isBought = isBought;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
