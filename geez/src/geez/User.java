package geez;

import java.text.NumberFormat;

public class User {

private String name;
 private String hash;
 private byte[] salt;
 private int clearanceLevel;
 
 //the Account constructor
 public User(String Name, int clearance, String Hashish, byte[] salty){
  
  name = Name;
  clearanceLevel = clearance;
  hash = Hashish;
  salt = salty;
 }
 
 public String toString(){
  String result = "";
  NumberFormat fmt = NumberFormat.getCurrencyInstance();
  result = "\nUser: " + name + "\nClearanceLevel: " + clearanceLevel;
  return result;
  
 }
 
}