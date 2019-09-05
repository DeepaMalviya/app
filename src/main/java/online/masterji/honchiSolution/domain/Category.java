
package online.masterji.honchiSolution.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Category {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("data")
    @Expose
    public Data data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Category() {
    }

    /**
     * 
     * @param id
     * @param data
     */
    public Category(String id, Data data) {
        super();
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category withId(String id) {
        this.id = id;
        return this;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Category withData(Data data) {
        this.data = data;
        return this;
    }



}
