import java.util.LinkedList;
import java.util.Queue;

/**
 * Leetcode 733. Flood Fill
 * Link: https://leetcode.com/problems/flood-fill/description/
 */
//------------------------------------ Solution 1 -----------------------------------
public class FloodFill {
    /**
     * BFS solution - start at the given cell if it is already same as asked color return image grid
     * otherwise use queue to add cells which are connected to curr cell getting process which also share
     * the same original color when we started and update the curr cell with asked color
     *
     * TC: O(mn) SC:(mn)
     */
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        if (image[sr][sc] == color) {
            return image;
        }

        int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};
        Queue<Integer> processQueue = new LinkedList<>();
        processQueue.add(sr);
        processQueue.add(sc);
        int original = image[sr][sc];
        image[sr][sc] = color; //marking updated at the time of adding to queue to reduce duplicacy incase multiple cells share same cell with original color

        while(!processQueue.isEmpty()) {
            int r = processQueue.poll();
            int c = processQueue.poll();

            for (int[] dir: dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                //bound check and logic
                if (nr >= 0 && nc >= 0 && nr < image.length && nc < image[nr].length && image[nr][nc] == original) {
                    processQueue.add(nr);
                    processQueue.add(nc);
                    image[nr][nc] = color; //mark updated at the time of adding to queue
                }
            }
        }
        return image;
    }
}

//------------------------------------ Solution 2 -----------------------------------

/**
 * DFS Solution - usual way of DFS visit any adjacent cell recursively and only visit
 * them if they have color as first cell where processing started, this condition takes
 * care of already visited as we are updating color of the cell while visiting in the preorder level
 *
 * TC: O(mn) Auxiliary SC: O(1)
 * Recursive stack SC:O(mn)
 *
 * NOTE: updating the cell color at post order level will not work as we will receive stackoverflow
 * problem this logic will not identify it the cell is already visited or not.
 */
class FloodFill2 {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        if (image[sr][sc] == color) {
            return image;
        }

        int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};
        int original = image[sr][sc];
        dfs(image, dirs, sr, sc, original, color);
        return image;
    }

    private void dfs(int[][] image, int[][] dirs, int r, int c, int original, int color) {
        //base
        if (r < 0 || c < 0 || r == image.length || c == image[r].length) {
            return;
        }
        if (image[r][c] != original) {
            return;
        }

        //logic
        image[r][c] = color;

        for (int[] dir: dirs) {
            int nr = r + dir[0];
            int nc = c + dir[1];
            dfs(image, dirs, nr, nc, original, color);
        }
    }
}

//------------------------------------ Solution 3 -----------------------------------

/**
 * DFS Solution - same as above except we removed the need to pass original color in the
 * recursive function and made update on when recursion will be initiated only if it matches the
 * original color of the current cell being processed
 *
 * TC: O(mn) Auxiliary SC: O(1)
 * recursive stack SC:O(mn)
 *
 * NOTE: previous note applies here as well
 */
class FloodFill3 {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        if (image[sr][sc] == color) {
            return image;
        }

        int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};
        dfs(image, dirs, sr, sc, color);
        return image;
    }

    private void dfs(int[][] image, int[][] dirs, int r, int c, int color) {
        //base

        //logic
        int original = image[r][c];
        image[r][c] = color;

        for (int[] dir: dirs) {
            int nr = r + dir[0];
            int nc = c + dir[1];

            if (nr >= 0 && nc >= 0 && nr < image.length && nc < image[nr].length && image[nr][nc] == original) {
                dfs(image, dirs, nr, nc, color);
            }
        }
    }
}