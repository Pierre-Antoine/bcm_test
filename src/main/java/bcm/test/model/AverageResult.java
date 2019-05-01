package bcm.test.model;

public class AverageResult {

    String region;
    double averageTempeature;
    int nbPoints;

    public AverageResult(String region, double averageTempeature, int nbPoints) {
        this.region = region;
        this.averageTempeature = averageTempeature;
        this.nbPoints = nbPoints;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setAverageTempeature(double averageTempeature) {
        this.averageTempeature = averageTempeature;
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    public String getRegion() {
        return region;
    }

    public double getAverageTempeature() {
        return averageTempeature;
    }

    public int getNbPoints() {
        return nbPoints;
    }
}
