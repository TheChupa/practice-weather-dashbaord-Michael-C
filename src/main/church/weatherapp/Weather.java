package church.weatherapp;

import java.util.List;

public class Weather {
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Sky Conditions are: " + description;
    }
}
