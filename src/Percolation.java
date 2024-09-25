import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation{
    //We want to make instance variables private to keep access restricted to the class, which reinforces the idea of data encapsulation
    private boolean[][] sites; //false is closed and true is open
    private WeightedQuickUnionUF uf;
    private int gridDim;
    private int gridSquare;
    private int numOfOpenSites;
    private int flattenedIndex;
    private int indexTopVNode;
    private int indexBottomVNode;





    //creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        sites = new int[n][n]; // all the sites are initialized to false by default
        gridDim = n;
        gridSquare = n * n;
        // this will initalize a wquuf grid and will make nodes[i] = i
        // it will also auto update the size and stuff so i don't have to implement this

        uf = new WeightedQuickUnionUF(gridSquare +2);
        indexBottomVNode = gridSquare; // since the indices of uf go from 0 to n^2 -1 and now they go from 0 to n^2 +1 because of the addition of these two virtual nodes
        indexTopVNode = gridSquare + 1;






//        int currentNum = 0;

//        for(int i = 1; i <= n; i++){
//            for(int j = 1; j<=n; j++){
//                arr[i][j] = currentNum;
//                currentNum++;
//                System.out.println(arr[i][j]);
            }
        }
    }

    //Opens the site (row,col) if it is not open already
    // remember that the sites start at (1,1) and not (0,0)
    public void open(int row, int col){
        //for this, i need to connect a node with the node above, below, right, and left
        //so I need to be able to obtain the flat index of each item in the grid
        sites[row-1][col-1] = true;
        numOfOpenSites++;


    //  NEED TO INCORPORATE ERROR CHECKING HERE
        //
        //
        //


        connectAdjacentSites(row, col);



    }

    // for this one the row and
    private void connectAdjacentSites(int row, int col){
        int flatInd = flatIndex(row, col);

        //connect it to the top virtual node if its on first row
        if(row ==1 ) uf.union(indexTopVNode, flatInd); // java doesn't care about whitespace

        //connect it to the bottom virtual node if its on last row

        if(row == gridSize) uf.union(indexBottomVNode, flatInd);





        //check if node above is open and connect above
        if(row - 1 > 0)

        //check if node below is open and connect below

        //check if node right is open and connect right

        //check if node left is open and connect left
        }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){

    }

    //is the site (row, col) full?
    public boolean isFull(int row, int col){

    }

    //returns num of open sites
    public int numberOfOpenSites(){

    }

    //does the system percolate?
    public boolean percolates() {

    }

    //expects rows and columns to start at (1,1), hence the -1 in the math
    private int flatIndex(row, col){
        return ((row - 1)* gridSize) + col;

        }

    //test client (optional)
    public static void main(String[] args) {
    Percolation(10);

    }
}
