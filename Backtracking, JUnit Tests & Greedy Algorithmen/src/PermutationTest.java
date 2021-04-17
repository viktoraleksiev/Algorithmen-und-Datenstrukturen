import java.util.Collections;
import java.util.LinkedList;

import static org.junit.Assert.*;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;


class PermutationTest {
	PermutationVariation p1;
	PermutationVariation p2;
	public int n1;
	public int n2;
	int cases=1;
	
	void initialize() {
		n1=4;
		n2=6;
		Cases c= new Cases();
		p1= c.switchforTesting(cases, n1);
		p2= c.switchforTesting(cases, n2);
	}
    // returns the number of fixed point free permutations for Array with n elements
    int AnzahlPermutationen(int n){
        if ( n==1 ) return 0;
        return n*AnzahlPermutationen(n-1) + (int) Math.pow(-1,n) ;
    }

	boolean istPermutation(int[] n, int[] m){
            int n1 = n.length;
            int m1 = m.length;

            if (n1 != m1) {
                return false;
            }

            Arrays.sort(n);
            Arrays.sort(m);

            for (int i = 0; i < n1;  i++) {
                if (!(n[i] == m[i]))
                    return false;
            }

            return true;
   	 }

	

	@Test
	void testPermutation() {
		initialize();
        try {
            assertEquals(this.p1.original.length, this.n1);
         } catch (NullPointerException e ) {
           System.out.println("Original not initialized!");
         }
        try {
            assertEquals(this.p2.original.length, this.n2);
        } catch (NullPointerException e ) {
            System.out.println("Original not initialized!");
        }

        for (int i = 0; i < n1; i++)
        {
            for (int j = i + 1; j < n1; j++)
            {
                if (this.p1.original[i] == this.p1.original[j])
                    fail("The numbers can't appear twice in the permuation!");
            }
        }

        for (int i = 0; i < n2; i++)
        {
            for (int j = i + 1; j < n2; j++)
            {
                if (this.p2.original[i] == this.p2.original[j])
                    fail("The numbers can't appear twice in the permuation!");
            }
        }

        try {
            assertTrue(this.p1.allDerangements.isEmpty());
        } catch (NullPointerException e){
            fail("allDeragements is not initialized!");
        }
        try {
            assertTrue(this.p2.allDerangements.isEmpty());
        } catch (NullPointerException e){
            fail("allDeragements is not initialized!");
        }

	}

	@Test
	void testDerangements() {
		initialize();
		//in case there is something wrong with the constructor
		fixConstructor();
	p1.derangements();
        p2.derangements();

        assertEquals(p1.allDerangements.size(),AnzahlPermutationen(p1.original.length));
        assertEquals(p2.allDerangements.size(),AnzahlPermutationen(p2.original.length));

        try {
            for (int i = 0; i < p1.allDerangements.size();i++){
                for (int k = 0; k < p1.original.length;i++)
                if (p1.allDerangements.get(i)[k] == p1.original[k])
                    fail();
            }
        } catch (Exception e){
            System.out.println("Not fixed point free!");
        }

        try {
            for (int i = 0; i < p2.allDerangements.size();i++){
                for (int k = 0; k < p2.original.length;i++)
                    if (p2.allDerangements.get(i)[k] == p2.original[k])
                        fail();
            }
        } catch (Exception e){
            System.out.println("Not fixed point free!");
        }

	}
	
	@Test
	void testsameElements() {
		initialize();
		//in case there is something wrong with the constructor
		fixConstructor();
        p1.derangements();
        p2.derangements();

        assertFalse(p1.allDerangements.isEmpty());
        assertFalse(p2.allDerangements.isEmpty());


            for (int i = 0; i < p1.allDerangements.size();i++){
                        assertTrue(istPermutation(p1.allDerangements.get(i),p1.original));
            }

            for (int i = 0; i < p2.allDerangements.size();i++){
                        assertTrue(istPermutation(p2.allDerangements.get(i),p2.original));
            }
	}
	
	void setCases(int c) {
		this.cases=c;
	}
	
	public void fixConstructor() {
		//in case there is something wrong with the constructor
		p1.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n1;i++)
			p1.original[i]=2*i+1;
		
		p2.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n2;i++)
			p2.original[i]=i+1;
	}
}


