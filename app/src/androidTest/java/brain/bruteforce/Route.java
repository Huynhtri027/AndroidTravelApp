package brain.bruteforce;

/**
 * Created by zhanghao on 5/11/15.
 */
public class Route{
    int attractionNumber;
    double cost;
    double time;
    boolean done;
    Location[] route;

    public Route(int attractionNumber) {
        this.attractionNumber = attractionNumber;
        route=new Location[attractionNumber];
    }
}