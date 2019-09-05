
package online.masterji.honchiSolution.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("cloth")
    @Expose
    private String cloth;
    @SerializedName("photo3")
    @Expose
    private String photo3;
    @SerializedName("photo2")
    @Expose
    private String photo2;
    @SerializedName("photo1")
    @Expose
    private String photo1;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("photo")
    @Expose
    private String photo;

    /**
     * No args constructor for use in serialization
     */
    public Data() {
    }

    /**
     * @param title
     * @param photo1
     * @param photo2
     * @param photo
     * @param photo3
     */
    public Data(String photo3, String photo2, String photo1, String title, String photo) {
        super();
        this.photo3 = photo3;
        this.photo2 = photo2;
        this.photo1 = photo1;
        this.title = title;
        this.photo = photo;
    }

    public String getCloth() {
        return cloth;
    }

    public void setCloth(String cloth) {
        this.cloth = cloth;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }

    public Data withPhoto3(String photo3) {
        this.photo3 = photo3;
        return this;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public Data withPhoto2(String photo2) {
        this.photo2 = photo2;
        return this;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public Data withPhoto1(String photo1) {
        this.photo1 = photo1;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Data withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Data withPhoto(String photo) {
        this.photo = photo;
        return this;
    }


}
