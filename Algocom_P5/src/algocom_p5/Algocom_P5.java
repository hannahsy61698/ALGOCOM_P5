/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algocom_p5;

import java.util.ArrayList;

/**
 *
 * @author Hannah Sy
 */
public class Algocom_P5 {

    /**
     * @param args the command line arguments
     */
    
    //1
	public static void assignTable(String[] input) {
            
	ArrayList<Integer> matrixDimList = new ArrayList<Integer>();
        int numMatrix = Integer.parseInt(input[0]);
        String[] matrixName = new String[numMatrix];
        int[][] tableForPrinting;
        
        for(int i = 0; i < numMatrix; i++) {
        	String[] inputSplitted = input[i+1].split(" ");
        	matrixName[i] = inputSplitted[0];
        	
        	if(i == 0) 
        		matrixDimList.add(Integer.parseInt(inputSplitted[1]));
        		
        	matrixDimList.add(Integer.parseInt(inputSplitted[2]));
        }
        
        //Compute
        tableForPrinting = matrixChainMultiplication(matrixDimList, matrixDimList.size());

        //Print
        printMatrixChainMultOrder(1, matrixDimList.size() - 1, tableForPrinting, matrixName);
	}
	
	private static void printMatrixChainMultOrder(int x, int y, int[][] tableForPrinting, String[] matrixName) {
		
		if(x == y) {
			System.out.print(matrixName[x-1]);
		}
		
		else {
			System.out.print("("); 
			
			printMatrixChainMultOrder(x, tableForPrinting[x][y], tableForPrinting, matrixName);
			printMatrixChainMultOrder(tableForPrinting[x][y] + 1, y, tableForPrinting, matrixName);
			
			System.out.print(")");
		}
	}


	private static int[][] matrixChainMultiplication(ArrayList<Integer> matrixDimList, int n) {
		
		int[][] tableM = new int[n][n]; //Table that will keep track of the minimum for DP.
		int[][] tableS = new int[n][n]; //Table that will tell us what is the order.
		
		//If one matrix, it means there are no multiplication cost.
		for(int i = 1; i < n; i++) {
			tableM[i][i] = 0;
		}
		
		for(int subLen = 2; subLen < n; subLen++) { 
			
			for(int x = 1; x < n - subLen + 1; x++) {
				int y = x + subLen - 1;
				
				if(y != n) {
					
					//Take the min of all possible combination of number of cost
					tableM[x][y] = Integer.MAX_VALUE;
					for( int k = x ; k < y; k++) {
						
						//The cost when multiplying two matrixes.
						int cost = tableM[x][k] + 
								   tableM[k + 1][y] + 
								   matrixDimList.get(x - 1) * 
								   matrixDimList.get(k) * 
								   matrixDimList.get(y);
						
						if(cost < tableM[x][y]) {
							tableM[x][y] = cost;
							tableS[x][y] = k; //The partition on where the matrices where multiplied
						}
					}
				}
			}
		}
		
		return tableS;
	}

// 2
	private static class party {
		private int fee; 
		private int fun; 
		
		public party(int e, int f) {
		    fee = e;
		    fun = f;
		}
		
		public int getFee() {
			return fee;
		}
		
		public int getFun() {
			return fun;
		}
	}
	
	private static int back(int r, int col, int[][] pa, int fun, ArrayList<party> pl) {
		int spend = 0;
		
		while(r > 0 && fun > 0) {
			if(fun > 0) {
				if (pa[r - 1][col] != fun) {
					fun = fun - pl.get(r - 1).getFun();
					spend = spend + pl.get(r - 1).getFee();
					col = col - pl.get(r - 1).getFee();
				}
			}
			r--;
		}
		return spend;
	}
	
	public static void partyBudget(String[] inputs) {
		ArrayList<party> partyList = new ArrayList<party>();
		
		String[] t = inputs[0].split(" ");
		
		int bud = Integer.parseInt(t[0]);
		int p = Integer.parseInt(t[1]);
		
		int r = p + 1;
		int c = bud + 1;
		int w;
		int spend;
		
		int[][] Fun = new int[r][c];
				
		int a = 0;
		int b, d;
		
		while(a < p) {
			t = inputs[a + 1].split(" ");
			partyList.add(new party(Integer.parseInt(t[0]), Integer.parseInt(t[1])));
			
			a++;
		}
		
		for(b = 1; b < r; b++) {
			for(d = 1; d < c; d++) {
				w = d - partyList.get(b - 1).getFee();
				
				if(w > 0) {
					Fun[b][d] = Math.max(Fun[b - 1][d], Fun[b - 1][w] + partyList.get(b - 1).getFun());
				}else{
					Fun[b][d] = Fun[b - 1][d];
				}
			}
		}

		spend = back(r - 1, c - 1, Fun, Fun[r - 1][c - 1], partyList);
		System.out.print(spend + " " + Fun[r - 1][c - 1]);
	}
	
//3
	public static void cut(int l, int cuts, int[] places) {
		int m = cuts + 2;
		int sum;
		int ci;
		int rp;
		int lp;
		
		int[] p = new int[m]; 
		int[][] cost = new int[m][m]; 
		
		int a = 0;
		
		while(a < p.length) {
			if (a == p.length - 1) {
				p[a] = l;
			}else if(a == 0) {
				p[a] = 0;
			}else {
				p[a] = places[a - 1];
			}
			a++;
		}
		
		for (rp = 2; rp < m; rp++) {
			for (lp = rp - 2; 0 <= lp; lp--) {
				cost[lp][rp] = Integer.MAX_VALUE;
				
				ci = lp + 1;
				
				while(ci < rp) {
					sum = cost[ci][rp] + cost[lp][ci];
					
					if (sum < cost[lp][rp])
						cost[lp][rp] = sum;
					
					ci++;
				}
				cost[lp][rp] = p[rp] - p[lp] + cost[lp][rp];
			}
		}
		System.out.print("The minimum cutting is " + cost[0][m - 1]);
	}

    public static void main(String[] args) {
        // TODO code application logic here
        
        	String[] s = {"5", "A 4 10", "B 10 3", "C 3 12", "D 12 20", "E 20 7"};
		assignTable(s);
		
		System.out.println();
				
//		String[] s2 = {"5", "A 4 10", "B 10 3", "C 3 12", "D 12 20", "E 20 7"};
//		assignTable(s2);
//		
//		System.out.println();
//		System.out.println();
//				
//		String[] pb = {"50 10", "12 3", "15 8", "16 9", "16 6", "10 2", "21 9", "18 4", "12 4", "17 8", "18 9"}; 
//		partyBudget(pb);
//		
//		System.out.println();
//			    
//		String[] pb2 = {"50 10", "13 8", "19 10", "16 8", "12 9", "10 2", "12 8", "13 5", "15 5", "11 7", "16 2"}; 
//		partyBudget(pb2);
//		
//		System.out.println();
//		System.out.println();
//				
//		int[] c = {25, 50, 75};
//		cut(100, c.length, c);
//		
//		System.out.println();
//		
//		int[] c2 = {4, 5, 7, 8};
//		cut(10, c2.length, c2);
//	
    }
    
}
