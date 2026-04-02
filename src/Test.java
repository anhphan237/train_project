import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // 13.3. Surrounded Regions
    public void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;

        // 1. duyệt biên
        for (int i = 0; i < m; i++) {
            solveDfs(board, i, 0);
            solveDfs(board, i, n - 1);
        }

        for (int j = 0; j < n; j++) {
            solveDfs(board, 0, j);
            solveDfs(board, m - 1, j);
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

    void solveDfs(char[][] board, int x, int y) {
        int m = board.length;
        int n = board[0].length;

        // base case
        if (x < 0 || y < 0 || x >= m || y >= n) return;
        if (board[x][y] != 'O') return;

        // mark safe
        board[x][y] = '#';

        // 4 hướng
        solveDfs(board, x + 1, y);
        solveDfs(board, x - 1, y);
        solveDfs(board, x, y + 1);
        solveDfs(board, x, y - 1);
    }

    // 14. Backtracking
    // 14.1. Permutations
    // ==========================================================
    // Cách 1 - dùng visited array
    // ==========================================================
    public List<List<Integer>> permuteC1(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        boolean[] used = new boolean[nums.length];

        permuteBacktrack(nums, new ArrayList<>(), used, res);
        return res;
    }

    void permuteBacktrack(int[] nums, List<Integer> path, boolean[] used, List<List<Integer>> res) {
        // base case
        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path)); // clone
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;

            // chọn
            path.add(nums[i]);
            used[i] = true;

            // đi tiếp
            permuteBacktrack(nums, path, used, res);

            // quay lui
            path.remove(path.size() - 1);
            used[i] = false;
        }
    }
    // ==========================================================
    // Cách 2 - swap
    // ==========================================================
    public List<List<Integer>> permuteC2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        permuteBacktrack(nums, 0, res);
        return res;
    }

    void permuteBacktrack(int[] nums, int start, List<List<Integer>> res) {
        if (start == nums.length) {
            List<Integer> temp = new ArrayList<>();
            for (int n : nums) temp.add(n);
            res.add(temp);
            return;
        }

        for (int i = start; i < nums.length; i++) {
            swap(nums, start, i);

            permuteBacktrack(nums, start + 1, res);

            swap(nums, start, i); // backtrack
        }
    }

    void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    // 14.2. Subsets
    // ==========================================================
    // Cách 1 - Backtracking
    // ==========================================================
    public List<List<Integer>> subsetsC1(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        subsetsBacktrack(nums, 0, new ArrayList<>(), res);
        return res;
    }

    void subsetsBacktrack(int[] nums, int start, List<Integer> path, List<List<Integer>> res) {
        // luôn add (khác permutations)
        res.add(new ArrayList<>(path));

        for (int i = start; i < nums.length; i++) {
            // chọn
            path.add(nums[i]);

            // đi tiếp (i + 1 để tránh quay lại phần tử cũ)
            subsetsBacktrack(nums, i + 1, path, res);

            // backtrack
            path.remove(path.size() - 1);
        }
    }
    // ==========================================================
    // Cách 2 - Bitmask
    // ==========================================================
    public List<List<Integer>> subsetsC2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;

        for (int mask = 0; mask < (1 << n); mask++) {
            List<Integer> subset = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(nums[i]);
                }
            }

            res.add(subset);
        }

        return res;
    }

    // 14.3. N-Queens
    List<List<String>> res = new ArrayList<>();

    public List<List<String>> solveNQueens(int n) {
        boolean[] col = new boolean[n];
        boolean[] diag1 = new boolean[2 * n]; // row - col + n
        boolean[] diag2 = new boolean[2 * n]; // row + col

        char[][] board = new char[n][n];
        for (char[] row : board) Arrays.fill(row, '.');

        solveNQueensBacktrack(0, n, board, col, diag1, diag2);
        return res;
    }

    void solveNQueensBacktrack(int row, int n, char[][] board,
                   boolean[] col, boolean[] diag1, boolean[] diag2) {

        // base case
        if (row == n) {
            res.add(build(board));
            return;
        }

        for (int c = 0; c < n; c++) {

            int d1 = row - c + n;
            int d2 = row + c;

            if (col[c] || diag1[d1] || diag2[d2]) continue;

            // đặt queen
            board[row][c] = 'Q';
            col[c] = diag1[d1] = diag2[d2] = true;

            solveNQueensBacktrack(row + 1, n, board, col, diag1, diag2);

            // backtrack
            board[row][c] = '.';
            col[c] = diag1[d1] = diag2[d2] = false;
        }
    }

    List<String> build(char[][] board) {
        List<String> list = new ArrayList<>();
        for (char[] row : board) {
            list.add(new String(row));
        }
        return list;
    }

    // 15. Dynamic Programming
    // 15.1. Climbing Stairs
    public int climbStairs(int n) {
        if (n <= 2) return n;

        int a = 1, b = 2;
        for (int i = 3; i <= n; i++) {
            int c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    // 15.2. House Robber
    public int rob(int[] nums) {
        int prev2 = 0; // dp[i-2]
        int prev1 = 0; // dp[i-1]

        for (int num : nums) {
            int cur = Math.max(prev1, prev2 + num);
            prev2 = prev1;
            prev1 = cur;
        }

        return prev1;
    }

    // 15.3. Coin Change
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin >= 0) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    // 15.4. Longest Common Subsequence
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[m][n];
    }

    // 15.5. Longest Increasing Subsequence
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int res = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            res = Math.max(res, dp[i]);
        }

        return res;
    }

    // 15.6. Partition Equal Subset Sum
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 2 != 0) return false;

        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        for (int num : nums) {
            for (int i = target; i >= num; i--) {
                dp[i] = dp[i] || dp[i - num];
            }
        }

        return dp[target];
    }

    // 1. Prefix Sum
    // ==========================================================
    // 303) Range Sum Query - Immutable
    // ==========================================================
    // Time:
    // - Constructor: O(n)
    // - sumRange: O(1)
    // Space: O(n)
    class NumArray {

        private int[] prefix;

        public NumArray(int[] nums) {
            prefix = new int[nums.length + 1];

            for (int i = 0; i < nums.length; i++) {
                prefix[i + 1] = prefix[i] + nums[i];
            }
        }

        public int sumRange(int left, int right) {
            return prefix[right + 1] - prefix[left];
        }
    }


    // ==========================================================
    // 525) Contiguous Array
    // ==========================================================
    // Time: O(n)
    // Space: O(n)
    public int findMaxLength(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        int sum = 0;
        int maxLen = 0;

        map.put(0, -1); // VERY IMPORTANT

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                sum -= 1;
            } else {
                sum += 1;
            }

            if (map.containsKey(sum)) {
                int prevIndex = map.get(sum);
                maxLen = Math.max(maxLen, i - prevIndex);
            } else {
                map.put(sum, i);
            }
        }

        return maxLen;
    }

    // ==========================================================
    // 560) Subarray Sum Equals K
    // ==========================================================
    // Time: O(n)
    // Space: O(n)
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        map.put(0, 1);

        int sum = 0;
        int count = 0;

        for (int num : nums) {
            sum += num;

            if (map.containsKey(sum - k)) {
                count += map.get(sum - k);
            }

            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return count;
    }

}



