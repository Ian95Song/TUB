import java.util.LinkedList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PermutationTest {
    PermutationVariation p1;
    PermutationVariation p2;
    public int n1;
    public int n2;
    int cases = 0;

    void initialize() {
        n1 = 4;
        n2 = 6;
        Cases c = new Cases();
        p1 = c.switchforTesting(cases, n1);
        p2 = c.switchforTesting(cases, n2);
    }


    @Test
    void testPermutation() {
        initialize();
        // TODO
        assertEquals(n1,p1.original.length,"n1 length not right");
        assertEquals(n2,p2.original.length,"n2 length not right");
//        HashSet<Integer> hashSet1 = new HashSet<Integer>();
//        for (int i = 0; i < p1.original.length; i++) {
//            hashSet1.add(p1.original[i]);
//        }
//        HashSet<Integer> hashSet2 = new HashSet<Integer>();
//        for (int i = 0; i < p2.original.length; i++) {
//            hashSet2.add(p2.original[i]);
//        }
//        assertEquals(p1.original.length,hashSet1.size(),"p1 has same numbers");
//        assertEquals(p2.original.length,hashSet2.size(),"p2 has same numbers");

        int[] p1ori = p1.original.clone();
        int[] p2ori = p2.original.clone();

        Arrays.sort(p1ori);
        boolean doppelt1 = false;
        for (int i = 1; i<p1ori.length;i++){
            if (p1ori[i]==p1ori[i-1]){
                doppelt1 = true;
            }
        }

        boolean doppelt2 = false;
        Arrays.sort(p2ori);
        for (int i = 1; i<p2ori.length;i++){
            if (p2ori[i]==p2ori[i-1]){
                doppelt2 = true;
            }
        }

        assertFalse(doppelt1,"p1 has same numbers");
        assertFalse(doppelt2,"p2 has same numbers");

        boolean isLeerList1 = p1.allDerangements.isEmpty();
        assertTrue(isLeerList1,"original1 is not empty");
        boolean isLeerList2 = p2.allDerangements.isEmpty();
        assertTrue(isLeerList2,"original2 is not empty");

    }

    @Test
    void testDerangements() {
        initialize();
        //in case there is something wrong with the constructor
        fixConstructor();
        // TODO

        p1.derangements();
        p2.derangements();
//        System.out.println(p1.allDerangements.size());
//        System.out.println(p1.allDerangements);

        int[] anzahl = new int[100];
        anzahl[0] = 0;
        anzahl[1] = 0;
        anzahl[2] = 1;
        for (int i = 3; i<100; i++){
            anzahl[i] = (i-1)*(anzahl[i-1]+anzahl[i-2]);
        }

        assertEquals(anzahl[n1],p1.allDerangements.size(),"Anzahl p1 not right");
        assertEquals(anzahl[n2],p2.allDerangements.size(),"Anzahl p2 not right");


        for (int[] i : p1.allDerangements){
            assertEquals(n1,i.length,"derangements size not right");
            for (int j=0; j <i.length; j++){
                assertNotEquals(p1.original[j],i[j],"Fixpunkt not free");
            }
        }

//        System.out.println(p2):
        for (int[] i : p2.allDerangements){
            assertEquals(n2,i.length,"derangements size not right");
            for (int j=0; j <i.length; j++){
                assertNotEquals(p2.original[j],i[j],"Fixpunkt not free");
            }
        }

    }

    @Test
    void testsameElements() {
        initialize();
        //in case there is something wrong with the constructor
        fixConstructor();
        // TODO

        p1.derangements();
        p2.derangements();

        boolean nichts1 = p1.allDerangements.isEmpty();
        assertFalse(nichts1,"nothing calculated");

        for (int[] i : p1.allDerangements){
            for (int j :i){
                boolean isIn = false;
                for (int k :p1.original){
                    if (k == j){
                        isIn = true;
                    }
                }
                assertTrue(isIn,"elements not part of original");
            }
        }

        boolean nichts2 = p2.allDerangements.isEmpty();
        assertFalse(nichts2,"nothing calculated");

        for (int[] i : p2.allDerangements){
            for (int j :i){
                boolean isIn = false;
                for (int k :p2.original){
                    if (k == j){
                        isIn = true;
                    }
                }
                assertTrue(isIn,"elements not part of original");
            }
        }
    }

    void setCases(int c) {
        this.cases = c;
    }

    public void fixConstructor() {
        //in case there is something wrong with the constructor
        p1.allDerangements = new LinkedList<int[]>();
        for (int i = 0; i < n1; i++)
            p1.original[i] = 2 * i + 1;

        p2.allDerangements = new LinkedList<int[]>();
        for (int i = 0; i < n2; i++)
            p2.original[i] = i + 1;
    }
}
