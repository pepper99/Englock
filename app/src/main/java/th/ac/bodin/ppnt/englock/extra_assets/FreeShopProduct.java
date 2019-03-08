package th.ac.bodin.ppnt.englock.extra_assets;

public class FreeShopProduct {
    private int imageId;
    private String title;

    public FreeShopProduct(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;
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
}
