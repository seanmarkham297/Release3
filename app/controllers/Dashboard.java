package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Member;
import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;

public class Dashboard extends Controller {
  public static void index() {
    Logger.info("Rendering Dashboard");
    Member member = Accounts.getLoggedInMember();
    List<Station> stations = Station.findAll();
    render("dashboard.html", member, stations);
  }
  public static void addStation(String name, double latitude, double longitude){
    Member member = Accounts.getLoggedInMember();
    Station station = new Station(name, latitude, longitude);
    member.stations.add(station);
    member.save();
    //station.save();
    Logger.info("Adding a new Station" + name);
    redirect("/dashboard");
  }
  public static void deleteStation (Long id, Long stationid)
  {
    Member member = Member.findById(id);
    Station station = Station.findById(stationid);
    member.stations.remove(station);
    member.save();
    station.delete();
    Logger.info ("Removing" + station.name);
    redirect ("/dashboard");
  }
}