package online.masterji.honchiSolution.domain;

import java.util.List;

public class Item {
    String title;
    String title1;
    String id;
    String name;
    String neck;
    String chest;
    String waistTop;
    String waistBottom;
    String wrist;
    String gender;
    String thigh;


    String lengthBottom;
    String biceps;
    String mohri;
    String lengthTop;

    String knee;
    String round;
    String extra;


    String shoulder;
    //String ArmLength;
    String armLength;
    String hips;
    String inseam;
    String sleeveLngth;

    public String getThigh() {
        return thigh;
    }

    public void setThigh(String thigh) {
        this.thigh = thigh;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeck() {
        return neck;
    }

    public void setNeck(String neck) {
        this.neck = neck;
    }

    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public String getWaistTop() {
        return waistTop;
    }

    public void setWaistTop(String waistTop) {
        this.waistTop = waistTop;
    }

    public String getWaistBottom() {
        return waistBottom;
    }

    public void setWaistBottom(String waistBottom) {
        this.waistBottom = waistBottom;
    }

    public String getWrist() {
        return wrist;
    }

    public void setWrist(String wrist) {
        this.wrist = wrist;
    }

    public String getLengthBottom() {
        return lengthBottom;
    }

    public void setLengthBottom(String lengthBottom) {
        this.lengthBottom = lengthBottom;
    }

    public String getBiceps() {
        return biceps;
    }

    public void setBiceps(String biceps) {
        this.biceps = biceps;
    }

    public String getMohri() {
        return mohri;
    }

    public void setMohri(String mohri) {
        this.mohri = mohri;
    }

    public String getLengthTop() {
        return lengthTop;
    }

    public void setLengthTop(String lengthTop) {
        this.lengthTop = lengthTop;
    }

    public String getKnee() {
        return knee;
    }

    public void setKnee(String knee) {
        this.knee = knee;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getShoulder() {
        return shoulder;
    }

    public void setShoulder(String shoulder) {
        this.shoulder = shoulder;
    }

    public String getArmLength() {
        return armLength;
    }

    public void setArmLength(String armLength) {
        this.armLength = armLength;
    }

    public String getHips() {
        return hips;
    }

    public void setHips(String hips) {
        this.hips = hips;
    }

    public String getInseam() {
        return inseam;
    }

    public void setInseam(String inseam) {
        this.inseam = inseam;
    }

    public String getSleeveLngth() {
        return sleeveLngth;
    }

    public void setSleeveLngth(String sleeveLngth) {
        this.sleeveLngth = sleeveLngth;
    }

    public List<Measurment> getMeasurments() {
        return measurments;
    }

    public void setMeasurments(List<Measurment> measurments) {
        this.measurments = measurments;
    }

    private List<Measurment> measurments;
    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
