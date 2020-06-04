import java.util.LinkedList;

/**
 * This class implements a game of Row of Bowls.
 * For the games rules see Blatt05. The goal is to find an optimal strategy.
 */
public class RowOfBowls {

    int n;
    int[] values;
    int[][] firstPickMatrix;
    int[][] secondPickMatrix;
    LinkedList<Integer> sequence;

    public RowOfBowls() {
    }
    
    /**
     * Implements an optimal game using dynamic programming
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both parties play optimally
     */
    public int maxGain(int[] values)
    {
        // TODO
        this.n=values.length;
        this.values=values;
        int[][] firstPickMatrix = new int[n][n];
        int[][] secondPickMatrix = new int[n][n];

        for (int i =0; i<n;i++){
            firstPickMatrix[i][i] = values[i];
            secondPickMatrix[i][i] = 0;
        }

        for (int j=1; j<n; j++){
            for (int i= j-1; i>=0; i--){
                firstPickMatrix[i][j] = Math.max(values[i]+secondPickMatrix[i+1][j],values[j]+secondPickMatrix[i][j-1]);
                secondPickMatrix[i][j] = Math.min(firstPickMatrix[i+1][j],firstPickMatrix[i][j-1]);
            }
        }

        this.firstPickMatrix=firstPickMatrix;
        this.secondPickMatrix=secondPickMatrix;

        return firstPickMatrix[0][n-1]-secondPickMatrix[0][n-1];

    }

    /**
     * Implements an optimal game recursively.
     *
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both parties play optimally
     */

    public int firstPick (int[] values, int first, int last){
        if (first==last){
            return values[first];
        }
        int a = values[first] + secondPick(values,first+1,last);
        int b = values[last] + secondPick(values,first,last-1);
        return (Math.max(a, b));

    }

    public int secondPick (int[] values, int first, int last){
        if (first==last){
            return 0;
        }
        int a = firstPick(values,first+1,last);
        int b = firstPick(values,first,last-1);
        return (Math.min(a, b));

    }

    public int maxGainRecursive(int[] values) {
        // TODO
        if (values == null || values.length==0){
            return 0;
        }
        return firstPick(values,0,values.length-1)-secondPick(values,0,values.length-1);
    }

    
    /**
     * Calculates an optimal sequence of bowls using the partial solutions found in maxGain(int values)
     * @return optimal sequence of chosen bowls (represented by the index in the values array)
     */

    public void seekFirstPick(int first, int last){
        if (first==last){
            this.sequence.addFirst(first);
//            System.out.println(first);
            return;
        }
        if (firstPickMatrix[first][last]==values[first]+secondPickMatrix[first+1][last]){
            seekSecondPick(first+1,last);
//            System.out.println(first);
            this.sequence.addFirst(first);
        }
        else {
            seekSecondPick(first,last-1);
//            System.out.println(last);
            this.sequence.addFirst(last);
        }
    }

    public void seekSecondPick(int first, int last){
        if (first==last){
//            System.out.println(first);
            this.sequence.addFirst(first);
            return;
        }
        if (secondPickMatrix[first][last]==firstPickMatrix[first+1][last]){
            seekFirstPick(first+1,last);
//            System.out.println(first);
            this.sequence.addFirst(first);
        }
        else {
            seekFirstPick(first,last-1);
//            System.out.println(last);
            this.sequence.addFirst(last);
        }
    }



    public Iterable<Integer> optimalSequence()
    {
        // TODO
        this.sequence= new LinkedList<>();
        seekFirstPick(0,n-1);
        return this.sequence;
    }


    public static void main(String[] args)
    {
        // For Testing
        int [] values = {4,7,2,3};
//        int [] values = {3,4,1,2,8,5};
        RowOfBowls game = new RowOfBowls();
//        System.out.println(game.maxGainRecursive(values));
        System.out.println(game.maxGain(values));
        System.out.println(game.optimalSequence());

        }
}

