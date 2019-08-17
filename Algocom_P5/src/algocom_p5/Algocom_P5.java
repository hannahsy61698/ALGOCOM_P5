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
            
	ArrayList<Integer> matrixList = new ArrayList<Integer>();
        int num = Integer.parseInt(input[0]);
        String[] matrixName = new String[num];
        int[][] newTable;
        int i = 0;
        
        while(i<num)
        {
            String[] split = input[i+1].split(" ");
        	matrixName[i] = split[0];
        	
        	if(i == 0) 
                    matrixList.add(Integer.parseInt(split[1]));
        		
        	matrixList.add(Integer.parseInt(split[2]));
                i++;
        }
        
        newTable = mMult(matrixList, matrixList.size());

        printOrder(1, matrixList.size() - 1, newTable, matrixName);
        System.out.println(" ");
	
        }
	
	private static void printOrder(int x, int y, int[][] newTable, String[] matrixName) {
		
		if(x == y) {
			System.out.print(matrixName[x-1]);
		}
		
		else {
			System.out.print("("); 
			printOrder(x, newTable[x][y], newTable, matrixName);
			printOrder(newTable[x][y] + 1, y, newTable, matrixName);
			System.out.print(")");
                     }
            	}


	private static int[][] mMult(ArrayList<Integer> matrixList, int n) {
		
		int[][] M = new int[n][n]; 
		int[][] S = new int[n][n]; 
                int k =0;
		
		for(int i = 1; i < n; i++) {
			M[i][i] = 0;
		}
		
                int subLen = 2;
                        
		while(subLen< n){ 
			int x=1;
			while(x<n-subLen+1)
                        {
				int y = x + subLen - 1;
				
				if(y != n) {
					
					M[x][y] = Integer.MAX_VALUE;
					
                                        k = x;
                                        while(k<y)
                                        {
                                            int cost = M[x][k] + M[k + 1][y] + matrixList.get(x - 1) * matrixList.get(k) * matrixList.get(y);
						
                                            if(cost < M[x][y]) {
						M[x][y] = cost;
						S[x][y] = k; 
					         }
                                        k++;
                                       }
                                      
                                    }
                                x++;
				}
                        subLen++;
			}
		
		
		return S;
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
	
	private static int backTracking(int row, int col, int[][] pa, int fun, ArrayList<party> pl) {
		int spend = 0;
		
		while(row > 0 && fun > 0) {
			if(fun > 0) {
				if (pa[row - 1][col] != fun) {
					fun -= pl.get(row - 1).getFun();
					spend += pl.get(row - 1).getFee();
					col -= pl.get(row - 1).getFee();
				}
			}
			row--;
		}
		return spend;
	}
	
	public static void partyBudget(String[] inputs) {
		ArrayList<party> partyList = new ArrayList<party>();
		
		String[] temp = inputs[0].split(" ");
		
		int budget = Integer.parseInt(temp[0]);
		int n = Integer.parseInt(temp[1]);
		
		int row = n + 1;
		int col = budget + 1;
		int w;
		int spend;
		
		int[][] fun = new int[row][col];
				
		int x=0;
                int y=1;
                int z;
                
		while(x < n) {
			temp = inputs[x + 1].split(" ");
			partyList.add(new party(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])));
			
			x++;
		}
		
                while(y<row){
			for(z = 1; z < col; z++) {
				w = z - partyList.get(y - 1).getFee();
				
				if(w > 0) {
					fun[y][z] = Math.max(fun[y - 1][z], fun[y - 1][w] + partyList.get(y - 1).getFun());
				}
                                else{
					fun[y][z] = fun[y - 1][z];
				}
			}
                        y++;
		}

		spend = backTracking(row - 1, col - 1, fun, fun[row - 1][col - 1], partyList);
		System.out.println(spend + " " + fun[row - 1][col - 1]);
	}
	
//3
	public static void cut(int l, int cuts, int[] places) {
		int cut = cuts + 2;
		int sum;
		int index;
		int right = 2;
		int left;
		
		int[] place = new int[cut]; 
		int[][] cost = new int[cut][cut]; 
		
		int x = 0;
		
		while(x < place.length) {
			if (x == place.length - 1) {
				place[x] = l;
			}else if(x == 0) {
				place[x] = 0;
			}else {
				place[x] = places[x - 1];
			}
			x++;
		}
		
                while(right<cut){
                	for (left = right - 2; 0 <= left; left--) {
                		cost[left][right] = Integer.MAX_VALUE;
				
				index = left + 1;
				
				while(index < right) {
					sum = cost[index][right] + cost[left][index];
					
					if (sum < cost[left][right])
						cost[left][right] = sum;
					
					index++;
				}
				cost[left][right] = place[right] - place[left] + cost[left][right];
                                
                    }
                        right++;
		}
		System.out.println("The minimum cutting is " + cost[0][cut - 1]);
	}

    public static void main(String[] args) {
        // TODO code application logic here
        
//        	String[] Ainput1 = {"3", "A 10 30", "B 30 5", "C 5 60"};
//		assignTable(Ainput1);
//		
//		String[] Ainput2 = {"5", "A 4 10", "B 10 3", "C 3 12", "D 12 20", "E 20 7"};
//		assignTable(Ainput2);
//				
//		String[] Binput1 = {"50 10", "12 3", "15 8", "16 9", "16 6", "10 2", "21 9", "18 4", "12 4", "17 8", "18 9"}; 
//		partyBudget(Binput1);
//		
//		String[] Binput2 = {"50 10", "13 8", "19 10", "16 8", "12 9", "10 2", "12 8", "13 5", "15 5", "11 7", "16 2"}; 
//		partyBudget(Binput2);
//		
//		int[] Cinput1 = {25, 50, 75};
//		cut(100, Cinput1.length, Cinput1);
//				
//		int[] Cinput2 = {4, 5, 7, 8};
//		cut(10, Cinput2.length, Cinput2);
    }
    
}
