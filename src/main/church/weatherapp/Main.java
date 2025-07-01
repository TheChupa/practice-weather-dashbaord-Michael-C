package church.weatherapp;

public class Main {
    private double temp;
    private double humidity;


    private double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "It is "+ temp+"°F with a humidty reading of "+ humidity + "%";
    }
}

