package online.masterji.honchiSolution.domain;

import java.io.Serializable;
import java.util.List;

public class Slider implements Serializable {

    private List<String> sliders;

    public List<String> getSliders() {
        return sliders;
    }

    public void setSliders(List<String> sliders) {
        this.sliders = sliders;
    }
}
