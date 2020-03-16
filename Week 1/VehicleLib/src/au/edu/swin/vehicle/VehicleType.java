package au.edu.swin.vehicle;

public class VehicleType {

    private String code, description;
    private int seats;

    public VehicleType(String _code, String _description, int _seats) {
        this.code = _code;
        this.seats = _seats;
        this.description = _description;
    }

    public String getCode() {
        return code.toUpperCase();
    }

    public void setCode(String _code) {
        this.code = _code;
    }

    public String GetDescription() {
        return description;
    }

    public void setDescription(String _description) {
        this.description = _description;
    }

    public int GetSeats() {
        return seats;
    }

    public void SetSeats(int _seats) {
        this.seats = _seats;
    }
}
