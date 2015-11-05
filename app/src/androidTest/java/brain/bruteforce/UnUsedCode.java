package brain.bruteforce;

/**
 * Created by zhanghao on 5/11/15.
 */
public class UnUsedCode {
    /*//1 for walk, 2 for public, 3 for taxi
    public double[][][] bruteForceRoute(int currentAttraction, int nextAttraction, double currentBudget,
                                   int attractionCount,
                                   int[] remainingAttractions, double[][] route, double[][][] solutionSet){
        double[][] newRoute=new double[route.length][route[0].length];
        for (int i = 0; i < route.length; i++) {
            for (int j = 0; j < route[0].length ; j++) {
                newRoute[i][j]=route[i][j];
            }
        }
        for(int i=1; i<=remainingAttractions.length;i++){

            //initiation
            int newNextAttraction,newAttractionCount;
            newAttractionCount=attractionCount+1;
            int[] newRemainingAttractions=new int[remainingAttractions.length];
            double newCurrentBudget;
            newNextAttraction=remainingAttractions[i];

            //terminate condition
            if (currentBudget<0)
                newRoute[2][0]=-1;//unsatisfied
            else if (remainingAttractions.length==0){
                newRoute[2][0]=1;//finished
                // TODO: 5/11/15 add to solution set
                for (int i = 1; i <= solutionSet.length ; i++) {
                    if (solutionSet[i][0][2]!=0)
                        for (int p=1;p<=solutionSet[0].length)
                            for(int q=1;p<=solutionSet[0][0].length)
                                solutionSet[i][p][q]=route[p][q];
                }
            }else {
                //walk
                newCurrentBudget=currentBudget;
                newRoute[0][0]=newCurrentBudget;
                newRoute[0][1]+=getWalkTime(currentAttraction,nextAttraction);
                bruteForceRoute(nextAttraction,newNextAttraction,newCurrentBudget,newAttractionCount,newRemainingAttractions,newRoute,solutionSet);

                //public transport
                newCurrentBudget=currentBudget-getPublicCost(currentAttraction,nextAttraction);
                newRoute[0][0]=newCurrentBudget;
                newRoute[0][1]+=getPublicTime(currentAttraction,nextAttraction);
                bruteForceRoute(nextAttraction,newNextAttraction,newCurrentBudget,newAttractionCount,newRemainingAttractions,newRoute,solutionSet);

                //taxi
                newCurrentBudget=currentBudget-getTaxiCost(currentAttraction,nextAttraction);
                newRoute[0][0]=newCurrentBudget;
                newRoute[0][1]+=getTaxiTime(currentAttraction, nextAttraction);
                bruteForceRoute(nextAttraction,newNextAttraction,newCurrentBudget,newAttractionCount,newRemainingAttractions,newRoute,solutionSet);
            }
        }
        return solutionSet;
    }


    public int[][][] runBruteForceRoute(int attractions)


*/
}
