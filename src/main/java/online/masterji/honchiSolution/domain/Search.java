package online.masterji.honchiSolution.domain;

public class Search {
String description;
String id;
String photo1;
String photo2;
String price;
String title;

    public Search() {
    }

    public Search(String description, String id, String photo1, String photo2, String price, String title) {
        this.description = description;
        this.id = id;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.price = price;
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
