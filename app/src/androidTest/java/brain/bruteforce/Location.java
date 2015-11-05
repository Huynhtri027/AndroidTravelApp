package brain.bruteforce;

/**
 * Created by zhanghao on 5/11/15.
 */
public class Location{
    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }

    public int getTransport() {
        return transport;
    }

    public void setTransport(int transport) {
        this.transport = transport;
    }

    private int attractionId;
    private int transport;
    private boolean planned;

    public boolean isPlanned() {
        return planned;
    }

    public void setPlanned(boolean planned) {
        this.planned = planned;
    }



    public Location(int attractionId, int transport, boolean planned) {
        this.attractionId = attractionId;
        this.transport = transport;
        this.planned=planned;
    }
}