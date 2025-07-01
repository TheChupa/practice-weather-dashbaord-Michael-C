package church.weatherapp;


import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Scanner;


@SpringBootApplication

public class WeatherApp {


    public static void main(String[] args) {
        String apiKey = System.getenv("API_KEY"); //Api Key

        Scanner scanner = new Scanner(System.in); // scanner object

        WebClient webClient = WebClient.create(); //webclient this creates the webclient object

        while (true) {

            System.out.println("Press 1 to find out weather for Kansas City. \nPress 2 to find out weather for St Louis. \nPress 3 to enter a different city.\nType `exit` to close.");
            String answerChoice = scanner.nextLine();

            //Validates if input is 1/2/3/exit
            if (!answerChoice.equals("1") && !answerChoice.equals("2") && !answerChoice.equals("3") && !answerChoice.equalsIgnoreCase("exit")) {

                System.out.println("Please enter valid input");

            } else if (Objects.equals(answerChoice, "1")) {
                String city1 = "Kansas City";
                WeatherResponse response = webClient.get()
                        .uri("https://api.openweathermap.org/data/2.5/weather?q=" + city1 + "&appid=" + apiKey + "&units=imperial")
                        .retrieve()
                        .bodyToMono(WeatherResponse.class)
                        .block();
                Main weatherMain = response.getMain(); // creates a Main object and runs the .getMain() on it, which sets weatherMain to the response
                Weather description = response.getWeather().get(0); //creates a weather object and runs get.weather which runs the tostring method on the response. Have to use it like this because it's a list

                System.out.println("----------------------------------");
                System.out.println(weatherMain+ " in " + city1);
                System.out.println(description.toString());
                System.out.println("----------------------------------");

            } else if (Objects.equals(answerChoice, "2")) {
                String city2 = "St Louis";
                WeatherResponse response = webClient.get()
                        .uri("https://api.openweathermap.org/data/2.5/weather?q=" + city2 + "&appid=" + apiKey + "&units=imperial")
                        .retrieve()
                        .bodyToMono(WeatherResponse.class)
                        .block();
                Main weatherMain = response.getMain();
                Weather description = response.getWeather().get(0);

                System.out.println("----------------------------------");
                System.out.println(weatherMain + " in "+city2);
                System.out.println(description.toString());
                System.out.println("----------------------------------");

            } else if (Objects.equals(answerChoice, "3")) {


                System.out.println("Enter a city name to find weather conditions or type `exit` to quit");
                String cityCustom = scanner.nextLine();


                cityCustom = cityCustom.trim();

                try {
                    WeatherResponse response = webClient.get()
                            .uri("https://api.openweathermap.org/data/2.5/weather?q=" + cityCustom + "&appid=" + apiKey + "&units=imperial")
                            .retrieve()
                            .onStatus(status -> status.value() == 404, // checks the code that comes back
                                    clientResponse -> Mono.error(new RuntimeException("This city doesn't exist yet.")) // creates a new runtime Exception for a try/catch block
                            )
                            .bodyToMono(WeatherResponse.class)
                            .block();
                    Main weatherMain = response.getMain();

                    Weather description = response.getWeather().get(0);

                    System.out.println("----------------------------------");
                    System.out.println(weatherMain + " in " + cityCustom);
                    System.out.println(description.toString());
                    System.out.println("----------------------------------");


                } catch
                (RuntimeException e) {
                    System.out.println(e.getMessage());
                }

            } else if (answerChoice.equals("exit")) {
                System.out.println("Bye, Felicia");
                scanner.close();
                break;
            }

        }

    }
}

