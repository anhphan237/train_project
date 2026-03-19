
public class Test {
    public static void main(String[] args) {

    }

    // 13. Matrix Traversal
    // 13.1. Flood Fill
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int original = image[sr][sc];

        // tránh loop vô hạn
        if (original == color) return image;

        floodFillDfs(image, sr, sc, original, color);
        return image;
    }

    void floodFillDfs(int[][] image, int x, int y, int original, int color) {
        int m = image.length;
        int n = image[0].length;

        // base case
        if (x < 0 || y < 0 || x >= m || y >= n) return;
        if (image[x][y] != original) return;

        // đổi màu
        image[x][y] = color;

        // 4 hướng
        floodFillDfs(image, x + 1, y, original, color);
        floodFillDfs(image, x - 1, y, original, color);
        floodFillDfs(image, x, y + 1, original, color);
        floodFillDfs(image, x, y - 1, original, color);
    }

    // 13.2. Number of Islands
    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (grid[i][j] == '1') {
                    count++;               // tìm thấy island mới
                    numIslandsDfs(grid, i, j);       // xóa island đó
                }
            }
        }

        return count;
    }

    void numIslandsDfs(char[][] grid, int x, int y) {
        int m = grid.length;
        int n = grid[0].length;

        // base case
        if (x < 0 || y < 0 || x >= m || y >= n) return;
        if (grid[x][y] == '0') return;

        // mark visited (xóa luôn)
        grid[x][y] = '0';

        // 4 hướng
        numIslandsDfs(grid, x + 1, y);
        numIslandsDfs(grid, x - 1, y);
        numIslandsDfs(grid, x, y + 1);
        numIslandsDfs(grid, x, y - 1);
    }

    // 13.2. Surrounded Regions
    public void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;

        // 1. duyệt biên
        for (int i = 0; i < m; i++) {
            dfs(board, i, 0);
            dfs(board, i, n - 1);
        }

        for (int j = 0; j < n; j++) {
            dfs(board, 0, j);
            dfs(board, m - 1, j);
        }

        // 2. xử lý kết quả
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';   // bị bao
                } else if (board[i][j] == '#') {
                    board[i][j] = 'O';   // safe
                }
            }
        }
    }

    void dfs(char[][] board, int x, int y) {
        int m = board.length;
        int n = board[0].length;

        // base case
        if (x < 0 || y < 0 || x >= m || y >= n) return;
        if (board[x][y] != 'O') return;

        // mark safe
        board[x][y] = '#';

        // 4 hướng
        dfs(board, x + 1, y);
        dfs(board, x - 1, y);
        dfs(board, x, y + 1);
        dfs(board, x, y - 1);
    }
}



