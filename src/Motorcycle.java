public class Motorcycle extends Vehicle {

    private boolean hasSidecar;

    public Motorcycle(String brand, int year, boolean hasSidecar) {
        super(brand, year);
        this.hasSidecar = hasSidecar;
    }

    @Override
    public void displayInfo() {
        String sidecarText;

        if (hasSidecar) {
            sidecarText = "with sidecar";
        } else {
            sidecarText = "no sidecar";
        }

        System.out.println("Motorcycle: " + brand + " (" + year + ") - "
                + sidecarText);
    }
}