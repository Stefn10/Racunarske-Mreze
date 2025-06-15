package Zd5_;

public class WinnerRecord {

    public int clientNumber;
    public long duration;

    public WinnerRecord(int clientNumber, long duration){
        this.clientNumber = clientNumber;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Klijent# "+ clientNumber + " - vreme:" + duration + "ms";
    }
}
