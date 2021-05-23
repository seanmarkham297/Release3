package controllers;

import java.util.List;

import models.Station;
import models.Reading;
import models.Member;
import play.Logger;
import play.mvc.Controller;
import utils.StationAnalytics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StationCtrl extends Controller
{
    public static void index(Long id) {
        Station station = Station.findById(id);
        Logger.info("Station id = " + id);
        if (station.readings.size() != 0) {
            Reading minTemp = StationAnalytics.getMinTemp(station.readings);
            Reading maxTemp = StationAnalytics.getMaxTemp(station.readings);
            Reading minWindSpeed = StationAnalytics.getMinWindSpeed(station.readings);
            Reading maxWindSpeed = StationAnalytics.getMaxWindSpeed(station.readings);
            Reading minPressure = StationAnalytics.getMaxPressure(station.readings);
            Reading maxPressure = StationAnalytics.getMinPressure(station.readings);

            if (station.readings.size() != 0) {
                Reading reading = station.readings.get(station.readings.size() - 1);
                station.latestWeather = station.convertToLatestWeather(reading.code);
                station.latestWeatherIcon = station.showLatestWeatherIcon(reading.code);
                station.farenheit = station.convertToFarenheit(reading.temperature);
                station.celsius = station.temperatureCelsius(reading.temperature);
                station.latestWindSpeed = station.convertToLatestWindSpeed(reading.windSpeed);
                station.showPressure = station.showPressureHPA(reading.pressure);
                station.windCompass = station.showWindCompass(reading.windDirection);
                station.windChill = station.showWindChill(reading.temperature, reading.windSpeed);
                render("station.html", station, minTemp, maxTemp, minWindSpeed, maxWindSpeed, minPressure, maxPressure);
            }
        }
    }
    public static void addReading(Long id, int code, double temperature, double windSpeed, int pressure, int windDirection)
    {
        DateFormat dform = new SimpleDateFormat("yyyy/MM/dd  @  HH:mm:ss");
        Date obj = new Date();
        String date = dform.format(obj);

        Reading reading = new Reading(date, code, temperature, windSpeed, pressure, windDirection);
        Station station = Station.findById(id);
        station.readings.add(reading);
        station.save();
        redirect("/stations/" + id);
    }
    public static void deleteReading (Long id, Long readingid)
    {
        Station station = Station.findById(id);
        Reading reading = Reading.findById(readingid);
        Logger.info ("Removing " + reading.code);
        station.readings.remove(reading);
        station.save();
        reading.delete();

        render("station.html", station);
    }
}