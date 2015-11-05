package brain.bruteforce;

/**
 * Created by zhanghao on 5/11/15.
 */
public class Main {

    public int getMaxSize(int a){
        int ans=1;
        for (int i = 1; i < a; i++) {
            ans=ans*i*a;
        }
        return ans;
    }

    public int findPrevious(Route thisRoute){
        for (int i = 0; i < thisRoute.route.length; i++) {
            if (!thisRoute.route[i].isPlanned()){
                return i;
            }
        }
        return 0;
    }

    public boolean allVisited(Attraction[] attractions){
        boolean mark=true;
        for (Attraction attraction : attractions) {
            if (!attraction.visited)
                mark = false;
        }
        return mark;
    }

    public double getPublicCost(int a, int b){
        // TODO: 4/11/15
        return 0;
    }

    public double getTaxiCost(int a, int b){
        // TODO: 4/11/15
        return 0;
    }

    public double getPublicTime(int a, int b){
        // TODO: 4/11/15
        return 0;
    }

    public double getTaxiTime(int a, int b){
        // TODO: 4/11/15
        return 0;
    }
    public double getWalkTime(int a, int b){
        // TODO: 4/11/15
        return 0;
    }


    public int [][] fastApproximate(){
        // TODO: 4/11/15 implement this
        return new int[1][1];
    }

    public Route[] bruteForce(int[] attractions){
        Attraction[] attractionList=new Attraction[attractions.length];
        for (int i = 0; i <attractions.length; i++)
            attractionList[i].visited=false;
        Route[] ans=new Route[getMaxSize(attractionList.length-1)];
        Route thisRoute=new Route(attractionList.length-1);
        return bruteForceSolve(attractionList,ans,thisRoute);
    }

    public Route[] bruteForceSolve(Attraction[] attractionList, Route[] ans, Route thisRoute){

        //add ans to answer set if finished
        if (allVisited(attractionList)){
            for (Route an : ans) {
                if (!an.done) {
                    an.done = true;
                    an.cost = thisRoute.cost;
                    an.time = thisRoute.time;
                    for (int j = 0; j < thisRoute.route.length; j++) {
                        an.route[j].setTransport(thisRoute.route[j].getTransport());
                        an.route[j].setAttractionId(thisRoute.route[j].getAttractionId());
                        an.route[j].setPlanned(thisRoute.route[j].isPlanned());
                    }
                }
            }
            return ans;
        }

        //Copying objects
        Route newThisRoute=new Route(thisRoute.route.length);
        newThisRoute.attractionNumber=thisRoute.attractionNumber;
        newThisRoute.time=thisRoute.time;
        newThisRoute.cost=thisRoute.cost;
        newThisRoute.done=thisRoute.done;
        for (int i = 0; i < thisRoute.route.length; i++) {
            newThisRoute.route[i].setTransport(thisRoute.route[i].getTransport());
            newThisRoute.route[i].setAttractionId(thisRoute.route[i].getAttractionId());
            newThisRoute.route[i].setPlanned(thisRoute.route[i].isPlanned());
        }

        Attraction[] newAttractionList=new Attraction[attractionList.length];
        for (int i = 0; i < attractionList.length; i++) {
            newAttractionList[i].visited=attractionList[i].visited;
        }

        //find next attraction
        for (int i = 0; i < attractionList.length ; i++) {
            if (!attractionList[i].visited){
                //walk
                attractionList[i].visited=true;
                newThisRoute.cost+=0;
                newThisRoute.time+=getWalkTime(findPrevious(newThisRoute),i);
                newThisRoute.route[i].setAttractionId(i);
                newThisRoute.route[i].setTransport(1);
                newThisRoute.route[i].setPlanned(true);
                bruteForceSolve(newAttractionList, ans,newThisRoute);

                //public
                attractionList[i].visited=true;
                newThisRoute.cost+=getPublicCost(findPrevious(newThisRoute), i);
                newThisRoute.time+=getPublicTime(findPrevious(newThisRoute),i);
                newThisRoute.route[i].setAttractionId(i);
                newThisRoute.route[i].setTransport(2);
                newThisRoute.route[i].setPlanned(true);
                bruteForceSolve(newAttractionList,ans,newThisRoute);

                //taxi
                attractionList[i].visited=true;
                newThisRoute.cost+=getTaxiCost(findPrevious(newThisRoute),i);
                newThisRoute.time+=getTaxiTime(findPrevious(newThisRoute), i);
                newThisRoute.route[i].setAttractionId(i);
                newThisRoute.route[i].setTransport(3);
                newThisRoute.route[i].setPlanned(true);
                bruteForceSolve(newAttractionList,ans,newThisRoute);
            }
        }
        return ans;
    }

}
