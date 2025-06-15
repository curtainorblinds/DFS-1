import java.util.LinkedList;
import java.util.Queue;

/**
 * Leetcode 542. 01 Matrix
 * Link: https://leetcode.com/problems/01-matrix/description/
 */
//------------------------------------ Solution 1 -----------------------------------
public class ZeroOneMatrix {
    /**
     * BFS Solution - brute force solution to find nearest 0 from each 1 is by running bfs at each 1 individually
     * In the bfs function since we do not want to mutate the state of original mat matrix we are maintaining another
     * visited matrix to avoid duplicacy.
     *
     * TC: O(mn)^2 SC: O(mn)^2
     */
    public int[][] updateMatrix(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) { //TC (mn)
                if (mat[i][j] == 1) {
                    mat[i][j] = bfs(mat, i, j);
                }
            }
        }
        return mat;
    }

    private int bfs(int[][] mat, int r, int c) {
        int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};
        Queue<Integer> bfsQueue = new LinkedList<>(); //fresh bfs
        boolean[][] visited = new boolean[mat.length][mat[0].length]; //fresh visited SC: O(mn)
        bfsQueue.add(r);
        bfsQueue.add(c);
        visited[r][c] = true;
        int distance = 0;

        while(!bfsQueue.isEmpty()) { //TC O(mn)
            int size = bfsQueue.size() / 2;
            distance++;
            for (int i = 0; i < size; i++) {
                int row = bfsQueue.poll();
                int col = bfsQueue.poll();

                for(int[] dir: dirs) {
                    int nr = row + dir[0];
                    int nc = col + dir[1];

                    if (nr >= 0 && nc >= 0 && nr < mat.length && nc < mat[nr].length && !visited[nr][nc]) {
                        if (mat[nr][nc] == 0) {
                            return distance;
                        }
                        bfsQueue.add(nr);
                        bfsQueue.add(nc);
                        visited[nr][nc] = true;
                    }
                }
            }
        }
        return 121321; // at least 1 zero present in the matrix so this will never get returned
    }
}

//------------------------------------ Solution 2 -----------------------------------
class ZeroOneMatrix2 {
    /**
     * BFS solution optimized - idea is to start with all zeros in the bfsQueue and the very next
     * neighbors of them will be at distance of 1, then the next set of neighbors at 2, so and so forth.
     * At each level distance increases by 1 from their parent set and we will process entire mat this way
     * getting minimum distance of each 1 from any 0
     *
     * TC: O(mn) SC: O(mn)
     */
    public int[][] updateMatrix(int[][] mat) {
        Queue<Integer> bfsQueue = new LinkedList<>();

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j] == 0) {
                    bfsQueue.add(i);
                    bfsQueue.add(j);
                } else {
                    mat[i][j] = -1;
                }
            }
        }

        int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};
        while (!bfsQueue.isEmpty()) {
            int r = bfsQueue.poll();
            int c = bfsQueue.poll();

            for(int[] dir: dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                if (nr >= 0 && nc >= 0 && nr < mat.length && nc < mat[nr].length && mat[nr][nc] == -1) {
                    bfsQueue.add(nr);
                    bfsQueue.add(nc);
                    mat[nr][nc] = mat[r][c] + 1;
                }
            }
        }
        return mat;
    }
}