package entity;

public abstract class ServicePackage {

    protected double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public abstract void showInfo();
}