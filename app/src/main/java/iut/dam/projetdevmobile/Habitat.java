package iut.dam.projetdevmobile;

import java.util.List;

public class Habitat {
    private String user;
    private int floor;
    private List<String> equipment;

    public Habitat(String user, int floor, List<String> equipment) {
        this.user = user;
        this.floor = floor;
        this.equipment = equipment;
    }

    public String getUser() { return user; }
    public int getFloor() { return floor; }
    public List<String> getEquipment() { return equipment; }
}