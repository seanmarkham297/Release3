package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

import java.util.Date;

@Entity
public class Reading extends Model
{
  public String date;
  public int code;
  public double temperature;
  public double windSpeed;
  public int pressure;
  public int windDirection;
  
  public Reading(String date, int code, double temperature, double windSpeed, int pressure, int windDirection)
  {
    this.date = date;
    this.code = code;
    this.temperature = temperature;
    this.windSpeed = windSpeed;
    this.pressure = pressure;
    this.windDirection = windDirection;
  }
}
