import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    //We want to make instance variables private to keep access restricted to the class,
    // which reinforces the idea of encapsulation
    private boolean[][] sites; //false is closed and true is open
    private WeightedQuickUnionUF uf;
    private int gridDim;
    private int gridSquare;
    private int numOfOpenSites;
    private int flatInd;
    private int indexTopVNode;
    private int indexBottomVNode;


    //creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be >= 0");
        sites = new boolean[n][n]; // all the sites are initialized to false by default
        gridDim = n;
        gridSquare = n * n;
        //page 228 of book for WQUUF implementation
        // this will initalize a wquuf grid and will make nodes[i] = i
        // it will also auto update the size and stuff so i don't have to implement this
        // since the indices of uf go from 0 to n^2 -1
        // and now they go from 0 to n^2 +1 (but the # nodes is n^2 + 2 obviously)
        // because of the addition of these two virtual nodes
        // or 1 to n^2 + 2 so length is n^2 +2
        uf = new WeightedQuickUnionUF(gridSquare + 2);
        indexBottomVNode = gridSquare;
        indexTopVNode = gridSquare + 1;

    }


    //Opens the site (row,col) if it is not open already
    // remember that the sites start at (1,1) and not (0,0)
    public void open(int row, int col) {
        //check not out of bounds
        validateSite(row, col);
        //for this, i need to connect a node with the node above, below, right, and left
        //so I need to be able to obtain the flat index of each item in the grid



        //now its open
        //remember we were using (1,1) as the start so shift row and col
        sites[row - 1][col - 1] = true;
        numOfOpenSites++;


        //  NEED TO INCORPORATE ERROR CHECKING HERE
        //
        //
        //


        connectAdjacentSites(row, col);


    }
    // is the site (row, col) open?
    //since its public we will go with (1,1) and then have methods inside change it to shiftedRow
    //and shiftedCol
    public boolean isOpen(int row, int col) {
        validateSite(row, col);
        return sites[row-1][col-1];
    }

    //is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateSite(row, col);
        return uf.connected(indexTopVNode, flatIndex(row, col));
    }

    //returns num of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    //does the system percolate?
    public boolean percolates() {
        return uf.connected(indexTopVNode, indexBottomVNode);
    }

    // for this one the row and
    private void connectAdjacentSites(int row, int col) {
        //remember starting at (1,1) but this flatIndex method shifts for us since its private
        //and i don't have to worry about who will use it
        flatInd = flatIndex(row, col);

        //connect it to the top virtual node if its on first row
        if (row == 1)
            uf.union(indexTopVNode, flatInd);

        //connect it to the bottom virtual node if its on last row

        if (row == gridDim)
            uf.union(indexBottomVNode, flatInd);


        //check if node above is open and connect above. Also make sure to check that the node above is on grid
        if (isOnGrid(row - 1, col) && isOpen(row - 1, col)) {
            uf.union(flatInd, flatIndex(row - 1, col));
                    //subtract 1 from the row to get the node above
        }

        //check if node below is open and connect below
        if (isOnGrid(row + 1, col) && isOpen(row + 1, col)) {
            uf.union(flatInd, flatIndex(row + 1, col));
            //add 1 from the row for row below
        }
        //check if node right is open and connect right
        if (isOnGrid(row, col + 1) && isOpen(row, col + 1)) {
            uf.union(flatInd, flatIndex(row, col + 1));
            //add 1 to col for col right
        }
        //check if node left is open and connect left

        if (isOnGrid(row, col - 1) && isOpen(row, col - 1)) {
            uf.union(flatInd, flatIndex(row, col - 1));
            //subtract 1 to col for col left
        }
    }




    private int flatIndex(int row, int col) {
        int shiftedRow = row - 1;
        int shiftedCol = col - 1;
        int index = shiftedRow  * gridDim + shiftedCol;
        StdOut.printf("Flattened index for (%d, %d) -> %d%n", row, col, index);
        return index;
    }

    //convert from (1,1) to (0,0)
    private boolean isOnGrid(int row, int col) {
        int shiftedRow = row - 1;
        int shiftedCol = col - 1;
        return (shiftedRow >= 0 && shiftedCol >= 0 && shiftedRow < gridDim && shiftedCol < gridDim);
    }

    private void validateSite(int row, int col) {
        if(!isOnGrid(row, col)) {
            throw new IndexOutOfBoundsException("This index is out of bounds");
        }
    }

    //test client (optional)
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);

        Percolation percolation = new Percolation(size);
        StdOut.printf("A %d by %d grid has been created%n", size, size);

        // Start from 1 (after grid size), increment by 2, and stop when i reaches args.length
        for (int i = 1; i < args.length - 1; i += 2) {
            int row = Integer.parseInt(args[i]);
            int col = Integer.parseInt(args[i + 1]);
            StdOut.printf("Adding row: %d  col: %d %n", row, col);
            percolation.open(row, col);

            if (percolation.percolates()) {
                StdOut.printf("%nThe System percolates %n");
                break; // Optional: stop if system percolates
            }
        }
        if (!percolation.percolates()) {
            StdOut.print("Does not percolate \n");
        }

    }
}

