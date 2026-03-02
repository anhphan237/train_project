import java.util.*;

public class Main {
    public static void main(String[] args) {

    }

    // ==========================================================
    // 1) Contains Duplicate
    // ==========================================================
    // Time: O(n^2) | Space: O(1)
    public boolean containsDuplicateBrute(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) return true;
            }
        }
        return false;
    }

    // Time: O(n) average | Space: O(n)
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int x : nums) {
            if (!seen.add(x)) return true;
        }
        return false;
    }

    // ==========================================================
    // 2) Valid Anagram
    // ==========================================================
    // Time: O(n log n) | Space: O(n)
    public boolean isAnagramBrute(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) return false;
        char[] a = s.toCharArray();
        char[] b = t.toCharArray();
        Arrays.sort(a);
        Arrays.sort(b);
        return Arrays.equals(a, b);
    }

    // Time: O(n) | Space: O(1) (vì mảng 26 cố định)
    public boolean isAnagram(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) return false;

        int[] cnt = new int[26];
        for (int i = 0; i < s.length(); i++) {
            cnt[s.charAt(i) - 'a']++;
            cnt[t.charAt(i) - 'a']--;
        }
        for (int c : cnt) if (c != 0) return false;
        return true;
    }

    // ==========================================================
    // 3) Two Sum
    // ==========================================================
    // Time: O(n^2) | Space: O(1)
    // ==========================================================
    // Example Input:  nums = [3,4,5,6], target = 7
    //
    // Output: [0,1]
    // ==========================================================
    public int[] twoSumBrute(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) return new int[]{i, j};
            }
        }
        return new int[0];
    }

    // Time: O(n) average | Space: O(n)
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> indexByValue = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int need = target - nums[i];
            if (indexByValue.containsKey(need)) {
                return new int[]{indexByValue.get(need), i};
            }
            indexByValue.put(nums[i], i);
        }
        return new int[0];
    }

    // ==========================================================
    // 4) Group Anagrams
    // ==========================================================
    // Time: O(m * k log k) | Space: O(m * k)
    // m = số string, k = độ dài trung bình
    public List<List<String>> groupAnagramsBrute(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] a = s.toCharArray();
            Arrays.sort(a);
            String key = new String(a);
            map.computeIfAbsent(key, x -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }

    // Time: O(m * k) | Space: O(m * k)
    // Key = đếm 26 ký tự -> tránh sort
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            int[] cnt = new int[26];
            for (int i = 0; i < s.length(); i++)
                cnt[s.charAt(i) - 'a']++;

            // build key "a1#b0#c2#..."
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                sb.append(cnt[i]).append('#');
            }
            String key = sb.toString();

            map.computeIfAbsent(key, x -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }

    // ==========================================================
    // 5) Top K Frequent Elements
    // ==========================================================
    // Time: O(n log n) | Space: O(n)
    public int[] topKFrequentBrute(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : nums) freq.put(x, freq.getOrDefault(x, 0) + 1);

        List<int[]> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            list.add(new int[]{e.getKey(), e.getValue()});
        }

        list.sort((a, b) -> Integer.compare(b[1], a[1]));

        int[] ans = new int[k];
        for (int i = 0; i < k; i++) ans[i] = list.get(i)[0];
        return ans;
    }

    // Time: O(n) average | Space: O(n)
    // Bucket sort theo tần suất
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : nums) freq.put(x, freq.getOrDefault(x, 0) + 1);

        // buckets[f] = list numbers có tần suất f
        List<Integer>[] buckets = new List[nums.length + 1];
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            int f = e.getValue();
            if (buckets[f] == null) buckets[f] = new ArrayList<>();
            buckets[f].add(e.getKey());
        }

        int[] ans = new int[k];
        int idx = 0;
        for (int f = buckets.length - 1; f >= 0 && idx < k; f--) {
            if (buckets[f] == null) continue;
            for (int val : buckets[f]) {
                ans[idx++] = val;
                if (idx == k) break;
            }
        }
        return ans;
    }

    // ==========================================================
    // 6) Encode and Decode Strings
    // ==========================================================
    // Brute: delimiter đơn giản (có thể fail nếu string chứa delimiter)
    // Time: O(totalLen) | Space: O(totalLen)
    public String encodeBrute(List<String> strs) {
        // WARNING: Nếu string có chứa "||" sẽ lỗi decode
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strs.size(); i++) {
            if (i > 0) sb.append("||");
            sb.append(strs.get(i));
        }
        return sb.toString();
    }

    // Time: O(totalLen) | Space: O(totalLen)
    public List<String> decodeBrute(String s) {
        if (s.isEmpty()) return new ArrayList<>();
        return Arrays.asList(s.split("\\|\\|", -1));
    }

    // Optimized (chuẩn phỏng vấn): length#string
    // Time: O(totalLen) | Space: O(totalLen)
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str.length()).append('#').append(str);
        }
        return sb.toString();
    }

    // Time: O(totalLen) | Space: O(totalLen)
    public List<String> decode(String s) {
        List<String> res = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            int j = i;
            while (s.charAt(j) != '#') j++;
            int len = Integer.parseInt(s.substring(i, j));
            j++; // skip '#'
            res.add(s.substring(j, j + len));
            i = j + len;
        }
        return res;
    }

    // ==========================================================
    // 7) Product of Array Except Self
    // ==========================================================
    // Example:
    //
    // Input: nums = [1,2,4,6]
    // Output: [48,24,12,8]
    // ==========================================================
    // Time: O(n^2) | Space: O(1) (không tính output)
    public int[] productExceptSelfBrute(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int prod = 1;
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                prod *= nums[j];
            }
            ans[i] = prod;
        }
        return ans;
    }

    // ==========================================================
    // Example:
    //
    // Input: nums = [1,2,4,6]
    // Output: [48,24,12,8]
    // ==========================================================
    // Time: O(n) | Space: O(1) (không tính output)
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];

        int prefix = 1;
        for (int i = 0; i < n; i++) {
            ans[i] = prefix;
            prefix *= nums[i];
        }

        int suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            ans[i] *= suffix;
            suffix *= nums[i];
        }

        return ans;
    }

    // ==========================================================
    // 8) Longest Consecutive Sequence
    // ==========================================================
    // Example 1:
    //
    // Input: nums = [2,20,4,10,3,4,5]
    //
    // -> Explanation: The longest consecutive sequence is [2, 3, 4, 5].
    //
    // Output: 4
    // ==========================================================
    // Example 2:
    //
    // Input: nums = [0,3,2,5,4,6,1,1]
    //
    // Output: 7
    // ==========================================================
    // Brute force [2,20,4,10,3,4,5] -> sort [2,3,4,4,5,10,20]
    // 1: trùng; 2: consecutive; 3: non consecutive
    // ==========================================================
    // Time: O(n log n) | Space: O(1) (nếu sort in-place, Java sort int[] là O(log n) stack)
    public int longestConsecutiveBrute(int[] nums) {
        if (nums.length == 0) return 0;
        Arrays.sort(nums);

        int best = 1;
        int cur = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) continue;
            if (nums[i] == nums[i - 1] + 1) {
                cur++;
            } else {
                best = Math.max(best, cur);
                cur = 1;
            }
        }
        return Math.max(best, cur);
    }

    // ==========================================================
    // Optimal [2,20,4,10,3,4,5] -> set {2,20,4,10,3,5}
    // ==========================================================
    // Time: O(n) average | Space: O(n)
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int x : nums) set.add(x);

        int best = 0;
        for (int x : set) {
            if (set.contains(x - 1)) continue; // không phải start
            int cur = 1;
            int val = x;
            while (set.contains(val + 1)) {
                val++;
                cur++;
            }
            best = Math.max(best, cur);
        }
        return best;
    }

    // ==========================================================
    // 9) Valid Palindrome
    // ==========================================================
    // Time: O(n) | Space: O(n)
    public boolean isPalindromeBrute(String s) {
        StringBuilder cleaned = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)) cleaned.append(Character.toLowerCase(c));
        }
        String t = cleaned.toString();
        String rev = cleaned.reverse().toString();
        return t.equals(rev);
    }

    // Time: O(n) | Space: O(1)
    public boolean isPalindrome(String s) {
        int l = 0, r = s.length() - 1;
        while (l < r) {
            while (l < r && !Character.isLetterOrDigit(s.charAt(l))) l++;
            while (l < r && !Character.isLetterOrDigit(s.charAt(r))) r--;
            if (Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r))) return false;
            l++;
            r--;
        }
        return true;
    }

    // ==========================================================
    // 10) 3Sum
    // ==========================================================
    // Time: O(n^3) | Space: O(1) (không tính output)
    public List<List<Integer>> threeSumBrute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        int[] trip = new int[]{nums[i], nums[j], nums[k]};
                        Arrays.sort(trip);
                        String key = trip[0] + "," + trip[1] + "," + trip[2];
                        if (seen.add(key)) {
                            res.add(Arrays.asList(trip[0], trip[1], trip[2]));
                        }
                    }
                }
            }
        }
        return res;
    }

    // Time: O(n^2) | Space: O(1) extra (không tính sort + output)
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int l = i + 1, r = nums.length - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum < 0) l++;
                else if (sum > 0) r--;
                else {
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    l++;
                    r--;
                    while (l < r && nums[l] == nums[l - 1]) l++;
                    while (l < r && nums[r] == nums[r + 1]) r--;
                }
            }
        }
        return res;
    }

    // ==========================================================
    // 11) Container With Most Water
    // ==========================================================
    // Time: O(n^2) | Space: O(1)
    public int maxAreaBrute(int[] height) {
        int best = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int area = Math.min(height[i], height[j]) * (j - i);
                best = Math.max(best, area);
            }
        }
        return best;
    }

    // Time: O(n) | Space: O(1)
    public int maxArea(int[] height) {
        int l = 0, r = height.length - 1;
        int best = 0;
        while (l < r) {
            int area = Math.min(height[l], height[r]) * (r - l);
            best = Math.max(best, area);
            if (height[l] < height[r]) l++;
            else r--;
        }
        return best;
    }

    // ==========================================================
    // 12) Best Time to Buy and Sell Stock
    // ==========================================================
    // Time: O(n^2) | Space: O(1)
    // ==========================================================
    // Input: prices = [10,1,5,6,7,1]
    //
    // Output: 6
    // ==========================================================
    public int maxProfitBrute(int[] prices) {
        int best = 0;
        for (int i = 0; i < prices.length; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                best = Math.max(best, prices[j] - prices[i]);
            }
        }
        return best;
    }

    // Time: O(n) | Space: O(1)
    public int maxProfit(int[] prices) {
        int min = Integer.MAX_VALUE;
        int best = 0;
        for (int p : prices) {
            min = Math.min(min, p);
            best = Math.max(best, p - min);
        }
        return best;
    }

    // ==========================================================
    // 13) Longest Substring Without Repeating Characters
    // ==========================================================
    // Time: O(n^2) | Space: O(min(n, charset))
    public int lengthOfLongestSubstringBrute(String s) {
        int best = 0;
        for (int i = 0; i < s.length(); i++) {
            Set<Character> set = new HashSet<>();
            for (int j = i; j < s.length(); j++) {
                if (set.contains(s.charAt(j))) break;
                set.add(s.charAt(j));
                best = Math.max(best, j - i + 1);
            }
        }
        return best;
    }

    // Time: O(n) | Space: O(min(n, charset))
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> lastIndex = new HashMap<>();
        int best = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (lastIndex.containsKey(c)) {
                left = Math.max(left, lastIndex.get(c) + 1);
            }
            lastIndex.put(c, right);
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    // ==========================================================
    // 14) Longest Repeating Character Replacement
    // ==========================================================
    // Time: O(n^2) | Space: O(1) (26)
    public int characterReplacementBrute(String s, int k) {
        int best = 0;
        for (int i = 0; i < s.length(); i++) {
            int[] cnt = new int[26];
            int maxFreq = 0;
            for (int j = i; j < s.length(); j++) {
                int idx = s.charAt(j) - 'A';
                cnt[idx]++;
                maxFreq = Math.max(maxFreq, cnt[idx]);

                int len = j - i + 1;
                int need = len - maxFreq;
                if (need <= k) best = Math.max(best, len);
            }
        }
        return best;
    }

    // Time: O(n) | Space: O(1) (26)
    public int characterReplacement(String s, int k) {
        int[] cnt = new int[26];
        int left = 0;
        int maxFreq = 0;
        int best = 0;

        for (int right = 0; right < s.length(); right++) {
            int idx = s.charAt(right) - 'A';
            cnt[idx]++;
            maxFreq = Math.max(maxFreq, cnt[idx]);

            int len = right - left + 1;
            while (len - maxFreq > k) {
                cnt[s.charAt(left) - 'A']--;
                left++;
                len = right - left + 1;
            }
            best = Math.max(best, len);
        }
        return best;
    }

    // ==========================================================
    // 15) Minimum Window Substring
    // ==========================================================
    // Time: O(n^2) | Space: O(1) (ASCII 128)
    public String minWindowBrute(String s, String t) {
        if (t.isEmpty()) return "";
        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;

        int bestLen = Integer.MAX_VALUE;
        int bestL = 0;

        for (int l = 0; l < s.length(); l++) {
            int[] have = new int[128];
            int formed = 0;
            int required = 0;
            for (int c = 0; c < 128; c++) if (need[c] > 0) required++;

            int[] met = new int[128]; // 0/1
            for (int r = l; r < s.length(); r++) {
                char ch = s.charAt(r);
                have[ch]++;
                if (need[ch] > 0 && have[ch] == need[ch] && met[ch] == 0) {
                    met[ch] = 1;
                    formed++;
                }
                if (formed == required) {
                    int len = r - l + 1;
                    if (len < bestLen) {
                        bestLen = len;
                        bestL = l;
                    }
                    break;
                }
            }
        }

        return bestLen == Integer.MAX_VALUE ? "" : s.substring(bestL, bestL + bestLen);
    }

    // Time: O(n) | Space: O(1) (ASCII 128)
    public String minWindow(String s, String t) {
        if (t.isEmpty()) return "";

        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;

        int required = 0;
        for (int c = 0; c < 128; c++) if (need[c] > 0) required++;

        int[] have = new int[128];
        int formed = 0;

        int bestLen = Integer.MAX_VALUE;
        int bestL = 0;

        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            have[ch]++;

            if (need[ch] > 0 && have[ch] == need[ch]) formed++;

            while (formed == required) {
                int len = right - left + 1;
                if (len < bestLen) {
                    bestLen = len;
                    bestL = left;
                }

                char drop = s.charAt(left);
                have[drop]--;
                if (need[drop] > 0 && have[drop] < need[drop]) formed--;
                left++;
            }
        }

        return bestLen == Integer.MAX_VALUE ? "" : s.substring(bestL, bestL + bestLen);
    }

    // ==========================================================
    // 16) Valid Parentheses
    // ==========================================================
    // Time: O(n^2) worst | Space: O(n)
    // Brute idea: lặp đi lặp lại replace "()", "[]", "{}"
    public boolean isValidBrute(String s) {
        String cur = s;
        boolean changed = true;
        while (changed) {
            String next = cur.replace("()", "").replace("[]", "").replace("{}", "");
            changed = next.length() != cur.length();
            cur = next;
        }
        return cur.isEmpty();
    }

    // Time: O(n) | Space: O(n)
    public boolean isValid(String s) {
        Deque<Character> st = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') st.push(c);
            else {
                if (st.isEmpty()) return false;
                char top = st.pop();
                if (c == ')' && top != '(') return false;
                if (c == ']' && top != '[') return false;
                if (c == '}' && top != '{') return false;
            }
        }
        return st.isEmpty();
    }

    // ==========================================================
    // 17) Find Minimum in Rotated Sorted Array
    // ==========================================================
    // Time: O(n) | Space: O(1)
    public int findMinBrute(int[] nums) {
        int min = nums[0];
        for (int x : nums) min = Math.min(min, x);
        return min;
    }

    // Time: O(log n) | Space: O(1)
    public int findMin(int[] nums) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] > nums[r]) l = mid + 1;
            else r = mid;
        }
        return nums[l];
    }

    // ==========================================================
    // 18) Search in Rotated Sorted Array
    // ==========================================================
    // Time: O(n) | Space: O(1)
    public int searchBrute(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) return i;
        }
        return -1;
    }

    // Time: O(log n) | Space: O(1)
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] == target) return mid;

            // left sorted
            if (nums[l] <= nums[mid]) {
                if (nums[l] <= target && target < nums[mid]) r = mid - 1;
                else l = mid + 1;
            } else { // right sorted
                if (nums[mid] < target && target <= nums[r]) l = mid + 1;
                else r = mid - 1;
            }
        }
        return -1;
    }

    // ==========================================================
    // 19) Reverse Linked List
    // ==========================================================
    // Time: O(n) | Space: O(n) (recursion stack)
    public ListNode reverseListBrute(ListNode head) {
        return reverseRec(head, null);
    }

    private ListNode reverseRec(ListNode cur, ListNode prev) {
        if (cur == null) return prev;
        ListNode next = cur.next;
        cur.next = prev;
        return reverseRec(next, cur);
    }

    // Time: O(n) | Space: O(1)
    public ListNode reverseList(ListNode head) {
        ListNode prev = null, cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

    // ==========================================================
    // Common Data Structures
    // ==========================================================
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int v) { val = v; }
        ListNode(int v, ListNode n) { val = v; next = n; }
    }

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode() {}
        TreeNode(int v) { val = v; }
        TreeNode(int v, TreeNode l, TreeNode r) { val = v; left = l; right = r; }
    }

    // ==========================================================
    // 20) Merge Two Sorted Lists
    // ==========================================================
    // Time: O(n + m) | Space: O(n + m)  (creates new list)
    public ListNode mergeTwoListsBrute(ListNode list1, ListNode list2) {
        List<Integer> vals = new ArrayList<>();
        while (list1 != null) { vals.add(list1.val); list1 = list1.next; }
        while (list2 != null) { vals.add(list2.val); list2 = list2.next; }
        Collections.sort(vals);
        ListNode dummy = new ListNode(0), cur = dummy;
        for (int v : vals) {
            cur.next = new ListNode(v);
            cur = cur.next;
        }
        return dummy.next;
    }

    // Time: O(n + m) | Space: O(1)  (reuses nodes)
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0), tail = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }
        tail.next = (list1 != null) ? list1 : list2;
        return dummy.next;
    }

    // ==========================================================
    // 21) Linked List Cycle
    // ==========================================================
    // Time: O(n) | Space: O(n)
    public boolean hasCycleBrute(ListNode head) {
        Set<ListNode> seen = new HashSet<>();
        while (head != null) {
            if (!seen.add(head)) return true;
            head = head.next;
        }
        return false;
    }

    // Time: O(n) | Space: O(1)
    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    // ==========================================================
    // 22) Reorder List
    // ==========================================================
    // Time: O(n) | Space: O(n)
    public void reorderListBrute(ListNode head) {
        if (head == null) return;
        List<ListNode> arr = new ArrayList<>();
        ListNode cur = head;
        while (cur != null) { arr.add(cur); cur = cur.next; }

        int i = 0, j = arr.size() - 1;
        while (i < j) {
            arr.get(i).next = arr.get(j);
            i++;
            if (i == j) break;
            arr.get(j).next = arr.get(i);
            j--;
        }
        arr.get(i).next = null;
    }

    // Time: O(n) | Space: O(1)
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        // 1) find middle
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2) reverse second half
        ListNode second = reverseList(slow);
        ListNode first = head;

        // 3) merge alternating
        while (second != null && first != null) {
            ListNode t1 = first.next;
            ListNode t2 = second.next;

            first.next = second;
            if (t1 == null) break;
            second.next = t1;

            first = t1;
            second = t2;
        }
    }

    // ==========================================================
    // 23) Remove Nth Node From End of List
    // ==========================================================
    // Time: O(n) | Space: O(n)
    public ListNode removeNthFromEndBrute(ListNode head, int n) {
        List<ListNode> nodes = new ArrayList<>();
        ListNode cur = head;
        while (cur != null) { nodes.add(cur); cur = cur.next; }
        int idx = nodes.size() - n; // remove this index

        if (idx == 0) return head.next; // remove head
        nodes.get(idx - 1).next = (idx + 1 < nodes.size()) ? nodes.get(idx + 1) : null;
        return head;
    }

    // Time: O(n) | Space: O(1)
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode fast = dummy, slow = dummy;

        for (int i = 0; i < n; i++) fast = fast.next;
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }

    // ==========================================================
    // 24) Merge K Sorted Lists
    // ==========================================================
    // Time: O(N log N) | Space: O(N)   (N = total nodes)
    public ListNode mergeKListsBrute(ListNode[] lists) {
        List<Integer> vals = new ArrayList<>();
        for (ListNode head : lists) {
            while (head != null) { vals.add(head.val); head = head.next; }
        }
        Collections.sort(vals);
        ListNode dummy = new ListNode(0), cur = dummy;
        for (int v : vals) {
            cur.next = new ListNode(v);
            cur = cur.next;
        }
        return dummy.next;
    }

    // Time: O(N log k) | Space: O(k)
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
        for (ListNode node : lists) if (node != null) pq.offer(node);

        ListNode dummy = new ListNode(0), tail = dummy;
        while (!pq.isEmpty()) {
            ListNode min = pq.poll();
            tail.next = min;
            tail = tail.next;
            if (min.next != null) pq.offer(min.next);
        }
        tail.next = null;
        return dummy.next;
    }

    // ==========================================================
    // 25) Invert Binary Tree
    // ==========================================================
    // Time: O(n) | Space: O(n)  (BFS queue)
    public TreeNode invertTreeBrute(TreeNode root) {
        if (root == null) return null;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            TreeNode tmp = node.left;
            node.left = node.right;
            node.right = tmp;
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
        return root;
    }

    // Time: O(n) | Space: O(h)
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    // ==========================================================
    // 26) Maximum Depth of Binary Tree
    // ==========================================================
    // Time: O(n) | Space: O(n)  (level order)
    public int maxDepthBrute(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        int depth = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            depth++;
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
        }
        return depth;
    }

    // Time: O(n) | Space: O(h)
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    // ==========================================================
    // 27) Same Tree
    // ==========================================================
    // Time: O(n) | Space: O(n)  (serialize then compare)
    public boolean isSameTreeBrute(TreeNode p, TreeNode q) {
        return serializePreorderWithNull(p).equals(serializePreorderWithNull(q));
    }

    private String serializePreorderWithNull(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        Deque<TreeNode> st = new ArrayDeque<>();
        st.push(root);
        while (!st.isEmpty()) {
            TreeNode node = st.pop();
            if (node == null) {
                sb.append("#,");
                continue;
            }
            sb.append(node.val).append(",");
            // preorder: node, left, right -> stack push right then left
            st.push(node.right);
            st.push(node.left);
        }
        return sb.toString();
    }

    // Time: O(n) | Space: O(h)
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        if (p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    // ==========================================================
    // 28) Subtree of Another Tree
    // ==========================================================
    // Time: O(n + m) average-ish | Space: O(n + m)
    // Approach: serialize both trees and use substring check
    public boolean isSubtreeBrute(TreeNode root, TreeNode subRoot) {
        String a = serializePreorderWithNull(root);
        String b = serializePreorderWithNull(subRoot);
        return a.contains(b);
    }

    // Time: O(n * m) worst-case | Space: O(h)
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (subRoot == null) return true;
        if (root == null) return false;
        if (isSameTree(root, subRoot)) return true;
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    // ==========================================================
    // 29) Lowest Common Ancestor of a Binary Search Tree
    // ==========================================================
    // Time: O(h) | Space: O(h) (recursive)
    public TreeNode lowestCommonAncestorBrute(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (p.val < root.val && q.val < root.val) return lowestCommonAncestorBrute(root.left, p, q);
        if (p.val > root.val && q.val > root.val) return lowestCommonAncestorBrute(root.right, p, q);
        return root;
    }

    // Time: O(h) | Space: O(1) (iterative)
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode cur = root;
        while (cur != null) {
            if (p.val < cur.val && q.val < cur.val) cur = cur.left;
            else if (p.val > cur.val && q.val > cur.val) cur = cur.right;
            else return cur;
        }
        return null;
    }

    // ==========================================================
    // 30) Binary Tree Level Order Traversal
    // ==========================================================
    // Time: O(n) | Space: O(n) (DFS with map)
    public List<List<Integer>> levelOrderBrute(TreeNode root) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        dfsLevel(root, 0, map);
        List<List<Integer>> res = new ArrayList<>();
        for (int d = 0; d < map.size(); d++) res.add(map.get(d));
        return res;
    }

    private void dfsLevel(TreeNode node, int depth, Map<Integer, List<Integer>> map) {
        if (node == null) return;
        map.computeIfAbsent(depth, k -> new ArrayList<>()).add(node.val);
        dfsLevel(node.left, depth + 1, map);
        dfsLevel(node.right, depth + 1, map);
    }

    // Time: O(n) | Space: O(n) (BFS)
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> level = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                level.add(node.val);
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            res.add(level);
        }
        return res;
    }

    // ==========================================================
    // 31) Validate Binary Search Tree
    // ==========================================================
    // Time: O(n) | Space: O(n) (inorder list)
    public boolean isValidBSTBrute(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();
        inorderCollect(root, inorder);
        for (int i = 1; i < inorder.size(); i++) {
            if (inorder.get(i) <= inorder.get(i - 1)) return false;
        }
        return true;
    }

    private void inorderCollect(TreeNode node, List<Integer> out) {
        if (node == null) return;
        inorderCollect(node.left, out);
        out.add(node.val);
        inorderCollect(node.right, out);
    }

    // Time: O(n) | Space: O(h)
    public boolean isValidBST(TreeNode root) {
        return validBST(root, null, null);
    }

    private boolean validBST(TreeNode node, Integer low, Integer high) {
        if (node == null) return true;
        if (low != null && node.val <= low) return false;
        if (high != null && node.val >= high) return false;
        return validBST(node.left, low, node.val) && validBST(node.right, node.val, high);
    }

    // ==========================================================
    // 32) Kth Smallest Element in a BST
    // ==========================================================
    // Time: O(n) | Space: O(n)
    public int kthSmallestBrute(TreeNode root, int k) {
        List<Integer> inorder = new ArrayList<>();
        inorderCollect(root, inorder);
        return inorder.get(k - 1);
    }

    // Time: O(h + k) | Space: O(h)
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> st = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null || !st.isEmpty()) {
            while (cur != null) {
                st.push(cur);
                cur = cur.left;
            }
            cur = st.pop();
            if (--k == 0) return cur.val;
            cur = cur.right;
        }
        return -1;
    }

    // ==========================================================
    // 33) Construct Binary Tree From Preorder And Inorder Traversal
    // ==========================================================
    // Time: O(n^2) | Space: O(n)
    public TreeNode buildTreeBrute(int[] preorder, int[] inorder) {
        return buildTreeBruteRec(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode buildTreeBruteRec(int[] pre, int ps, int pe, int[] in, int is, int ie) {
        if (ps > pe || is > ie) return null;
        int rootVal = pre[ps];
        int idx = is;
        while (idx <= ie && in[idx] != rootVal) idx++;
        int leftSize = idx - is;
        TreeNode root = new TreeNode(rootVal);
        root.left = buildTreeBruteRec(pre, ps + 1, ps + leftSize, in, is, idx - 1);
        root.right = buildTreeBruteRec(pre, ps + leftSize + 1, pe, in, idx + 1, ie);
        return root;
    }

    // Time: O(n) | Space: O(n)
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inIndex = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) inIndex.put(inorder[i], i);
        return buildTreeOpt(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, inIndex);
    }

    private TreeNode buildTreeOpt(int[] pre, int ps, int pe, int[] in, int is, int ie, Map<Integer, Integer> inIndex) {
        if (ps > pe || is > ie) return null;
        int rootVal = pre[ps];
        int idx = inIndex.get(rootVal);
        int leftSize = idx - is;
        TreeNode root = new TreeNode(rootVal);
        root.left = buildTreeOpt(pre, ps + 1, ps + leftSize, in, is, idx - 1, inIndex);
        root.right = buildTreeOpt(pre, ps + leftSize + 1, pe, in, idx + 1, ie, inIndex);
        return root;
    }

    // ==========================================================
    // 34) Binary Tree Maximum Path Sum
    // ==========================================================
    // Brute idea: for each node, compute best path through that node by exploring both sides with full recursion.
    // Time: O(n^2) worst-case | Space: O(h)
    public int maxPathSumBrute(TreeNode root) {
        if (root == null) return 0;
        int[] ans = new int[] { Integer.MIN_VALUE };
        collectNodes(root, new ArrayList<>(), ans);
        return ans[0];
    }

    private void collectNodes(TreeNode node, List<TreeNode> nodes, int[] ans) {
        if (node == null) return;
        nodes.add(node);
        collectNodes(node.left, nodes, ans);
        collectNodes(node.right, nodes, ans);

        // once at root call end, evaluate all nodes
        if (node == nodes.get(0)) {
            for (TreeNode x : nodes) {
                int left = maxDownPath(x.left);
                int right = maxDownPath(x.right);
                ans[0] = Math.max(ans[0], x.val + Math.max(0, left) + Math.max(0, right));
            }
        }
    }

    // max sum from node down to any leaf (single path)
    private int maxDownPath(TreeNode node) {
        if (node == null) return 0;
        int left = maxDownPath(node.left);
        int right = maxDownPath(node.right);
        return node.val + Math.max(0, Math.max(left, right));
    }

    // Time: O(n) | Space: O(h)
    public int maxPathSum(TreeNode root) {
        int[] best = new int[] { Integer.MIN_VALUE };
        maxGain(root, best);
        return best[0];
    }

    private int maxGain(TreeNode node, int[] best) {
        if (node == null) return 0;
        int left = Math.max(0, maxGain(node.left, best));
        int right = Math.max(0, maxGain(node.right, best));
        best[0] = Math.max(best[0], node.val + left + right);
        return node.val + Math.max(left, right);
    }

    // ==========================================================
    // 35) Serialize And Deserialize Binary Tree
    // ==========================================================
    // Brute: level-order (BFS) serialization
    // Time: O(n) | Space: O(n)
    static class CodecBrute {
        public String serialize(TreeNode root) {
            if (root == null) return "";
            StringBuilder sb = new StringBuilder();
            Queue<TreeNode> q = new ArrayDeque<>();
            q.offer(root);
            while (!q.isEmpty()) {
                TreeNode node = q.poll();
                if (node == null) {
                    sb.append("#,");
                    continue;
                }
                sb.append(node.val).append(",");
                q.offer(node.left);
                q.offer(node.right);
            }
            return sb.toString();
        }

        public TreeNode deserialize(String data) {
            if (data == null || data.isEmpty()) return null;
            String[] parts = data.split(",");
            int i = 0;
            if (parts[i].equals("#")) return null;

            TreeNode root = new TreeNode(Integer.parseInt(parts[i++]));
            Queue<TreeNode> q = new ArrayDeque<>();
            q.offer(root);

            while (!q.isEmpty() && i < parts.length) {
                TreeNode cur = q.poll();

                // left
                if (i < parts.length && !parts[i].equals("#") && !parts[i].isEmpty()) {
                    cur.left = new TreeNode(Integer.parseInt(parts[i]));
                    q.offer(cur.left);
                }
                i++;

                // right
                if (i < parts.length && !parts[i].equals("#") && !parts[i].isEmpty()) {
                    cur.right = new TreeNode(Integer.parseInt(parts[i]));
                    q.offer(cur.right);
                }
                i++;
            }
            return root;
        }
    }

    // Optimized: preorder DFS with null markers (simple + compact)
    // Time: O(n) | Space: O(n)
    static class Codec {
        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            ser(root, sb);
            return sb.toString();
        }

        private void ser(TreeNode node, StringBuilder sb) {
            if (node == null) {
                sb.append("#,");
                return;
            }
            sb.append(node.val).append(",");
            ser(node.left, sb);
            ser(node.right, sb);
        }

        public TreeNode deserialize(String data) {
            if (data == null || data.isEmpty()) return null;
            Deque<String> q = new ArrayDeque<>(Arrays.asList(data.split(",")));
            return deser(q);
        }

        private TreeNode deser(Deque<String> q) {
            if (q.isEmpty()) return null;
            String s = q.pollFirst();
            if (s.equals("#") || s.isEmpty()) return null;
            TreeNode node = new TreeNode(Integer.parseInt(s));
            node.left = deser(q);
            node.right = deser(q);
            return node;
        }
    }

    // ==========================================================
    // 36) Find Median From Data Stream
    // ==========================================================
    // Brute: keep sorted list
    static class MedianFinderBrute {
        private final List<Integer> arr = new ArrayList<>();

        // addNum: Time: O(n) (insert) | Space: O(n)
        public void addNum(int num) {
            int idx = Collections.binarySearch(arr, num);
            if (idx < 0) idx = -idx - 1;
            arr.add(idx, num);
        }

        // findMedian: Time: O(1) | Space: O(1)
        public double findMedian() {
            int n = arr.size();
            if (n % 2 == 1) return arr.get(n / 2);
            return (arr.get(n / 2 - 1) + arr.get(n / 2)) / 2.0;
        }
    }

    // Optimized: two heaps
    static class MedianFinder {
        // max-heap for lower half
        private final PriorityQueue<Integer> left = new PriorityQueue<>(Collections.reverseOrder());
        // min-heap for upper half
        private final PriorityQueue<Integer> right = new PriorityQueue<>();

        // Time: O(log n) | Space: O(n)
        public void addNum(int num) {
            if (left.isEmpty() || num <= left.peek()) left.offer(num);
            else right.offer(num);

            // balance sizes
            if (left.size() > right.size() + 1) right.offer(left.poll());
            else if (right.size() > left.size()) left.offer(right.poll());
        }

        // Time: O(1) | Space: O(1)
        public double findMedian() {
            if (left.size() > right.size()) return left.peek();
            return (left.peek() + right.peek()) / 2.0;
        }
    }

    // ==========================================================
    // 37) Combination Sum
    // ==========================================================
    // Brute: backtracking without pruning/sorting
    // Time: exponential | Space: O(target) recursion depth
    public List<List<Integer>> combinationSumBrute(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        combBrute(candidates, 0, target, new ArrayList<>(), res);
        return res;
    }

    private void combBrute(int[] c, int idx, int remain, List<Integer> path, List<List<Integer>> res) {
        if (remain == 0) {
            res.add(new ArrayList<>(path));
            return;
        }
        if (remain < 0 || idx == c.length) return;

        // choose current (can reuse)
        path.add(c[idx]);
        combBrute(c, idx, remain - c[idx], path, res);
        path.remove(path.size() - 1);

        // skip current
        combBrute(c, idx + 1, remain, path, res);
    }

    // Optimized: sort + prune when candidate > remain
    // Time: exponential (pruned) | Space: O(target)
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> res = new ArrayList<>();
        combOpt(candidates, 0, target, new ArrayList<>(), res);
        return res;
    }

    private void combOpt(int[] c, int start, int remain, List<Integer> path, List<List<Integer>> res) {
        if (remain == 0) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < c.length; i++) {
            if (c[i] > remain) break;
            path.add(c[i]);
            combOpt(c, i, remain - c[i], path, res); // reuse allowed
            path.remove(path.size() - 1);
        }
    }

    // ==========================================================
    // 38) Word Search
    // ==========================================================
    // Brute: DFS with separate visited array per start (more memory churn)
    // Time: O(R*C*4^L) | Space: O(R*C)
    public boolean existBrute(char[][] board, String word) {
        int r = board.length, c = board[0].length;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                boolean[][] vis = new boolean[r][c];
                if (dfsWordBrute(board, word, 0, i, j, vis)) return true;
            }
        }
        return false;
    }

    private boolean dfsWordBrute(char[][] b, String w, int k, int i, int j, boolean[][] vis) {
        if (k == w.length()) return true;
        if (i < 0 || j < 0 || i >= b.length || j >= b[0].length) return false;
        if (vis[i][j] || b[i][j] != w.charAt(k)) return false;

        vis[i][j] = true;
        boolean ok = dfsWordBrute(b, w, k + 1, i + 1, j, vis)
                || dfsWordBrute(b, w, k + 1, i - 1, j, vis)
                || dfsWordBrute(b, w, k + 1, i, j + 1, vis)
                || dfsWordBrute(b, w, k + 1, i, j - 1, vis);
        vis[i][j] = false;
        return ok;
    }

    // Optimized: in-place marking (no new visited per start)
    // Time: O(R*C*4^L) | Space: O(L) recursion
    public boolean exist(char[][] board, String word) {
        int r = board.length, c = board[0].length;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (dfsWordOpt(board, word, 0, i, j)) return true;
            }
        }
        return false;
    }

    private boolean dfsWordOpt(char[][] b, String w, int k, int i, int j) {
        if (k == w.length()) return true;
        if (i < 0 || j < 0 || i >= b.length || j >= b[0].length) return false;
        if (b[i][j] != w.charAt(k)) return false;

        char tmp = b[i][j];
        b[i][j] = '\0'; // mark visited
        boolean ok = dfsWordOpt(b, w, k + 1, i + 1, j)
                || dfsWordOpt(b, w, k + 1, i - 1, j)
                || dfsWordOpt(b, w, k + 1, i, j + 1)
                || dfsWordOpt(b, w, k + 1, i, j - 1);
        b[i][j] = tmp; // restore
        return ok;
    }

    // ==========================================================
    // 39) Implement Trie Prefix Tree
    // ==========================================================
    // Brute: store all words, startsWith scans all
    // insert: O(1) avg | search: O(1) avg | startsWith: O(totalWords * prefixLen)
    static class TrieBrute {
        private final Set<String> words = new HashSet<>();

        public void insert(String word) {
            words.add(word);
        }

        public boolean search(String word) {
            return words.contains(word);
        }

        public boolean startsWith(String prefix) {
            for (String w : words) {
                if (w.startsWith(prefix)) return true;
            }
            return false;
        }
    }

    // Optimized Trie
    static class Trie {
        static class Node {
            Node[] next = new Node[26];
            boolean end;
        }

        private final Node root = new Node();

        // Time: O(L) | Space: O(L) new nodes worst-case
        public void insert(String word) {
            Node cur = root;
            for (int i = 0; i < word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                if (cur.next[idx] == null) cur.next[idx] = new Node();
                cur = cur.next[idx];
            }
            cur.end = true;
        }

        // Time: O(L) | Space: O(1)
        public boolean search(String word) {
            Node node = walk(word);
            return node != null && node.end;
        }

        // Time: O(L) | Space: O(1)
        public boolean startsWith(String prefix) {
            return walk(prefix) != null;
        }

        private Node walk(String s) {
            Node cur = root;
            for (int i = 0; i < s.length(); i++) {
                int idx = s.charAt(i) - 'a';
                if (idx < 0 || idx >= 26) return null; // basic guard
                if (cur.next[idx] == null) return null;
                cur = cur.next[idx];
            }
            return cur;
        }
    }

    // ==========================================================
    // 40) Design Add and Search Words Data Structure
    // ==========================================================

    // Brute: store list; search by checking each word with pattern
    static class WordDictionaryBrute {
        private final List<String> words = new ArrayList<>();

        // Time: O(1) | Space: O(1) extra
        public void addWord(String word) {
            words.add(word);
        }

        // Time: O(N * L) | Space: O(1)
        public boolean searchBrute(String pattern) {
            for (String w : words) {
                if (w.length() != pattern.length()) continue;
                if (match(pattern, w)) return true;
            }
            return false;
        }

        private boolean match(String p, String w) {
            for (int i = 0; i < p.length(); i++) {
                char pc = p.charAt(i);
                if (pc != '.' && pc != w.charAt(i)) return false;
            }
            return true;
        }
    }

    // Optimized: Trie + DFS for '.'
    static class WordDictionary {
        static class TrieNode {
            TrieNode[] next = new TrieNode[26];
            boolean end;
        }

        private final TrieNode root = new TrieNode();

        // Time: O(L) | Space: O(L) new nodes
        public void addWord(String word) {
            TrieNode cur = root;
            for (int i = 0; i < word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                if (cur.next[idx] == null) cur.next[idx] = new TrieNode();
                cur = cur.next[idx];
            }
            cur.end = true;
        }

        // Time: worst O(26^L) (with lots of '.') | Space: O(L) recursion
        public boolean search(String word) {
            return dfs(word, 0, root);
        }

        private boolean dfs(String w, int i, TrieNode node) {
            if (node == null) return false;
            if (i == w.length()) return node.end;

            char c = w.charAt(i);
            if (c == '.') {
                for (int k = 0; k < 26; k++) {
                    if (node.next[k] != null && dfs(w, i + 1, node.next[k])) return true;
                }
                return false;
            } else {
                return dfs(w, i + 1, node.next[c - 'a']);
            }
        }
    }

    // ==========================================================
    // 41) Word Search II
    // ==========================================================

    // Time: O(M*N*4^L) per word worst | Space: O(L)
    public List<String> findWordsBrute(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        int m = board.length, n = board[0].length;
        for (String w : words) {
            boolean found = false;
            boolean[][] vis = new boolean[m][n];
            for (int r = 0; r < m && !found; r++) {
                for (int c = 0; c < n && !found; c++) {
                    if (dfsWord(board, vis, r, c, w, 0)) {
                        res.add(w);
                        found = true;
                    }
                }
            }
        }
        return res;
    }

    private boolean dfsWord(char[][] b, boolean[][] vis, int r, int c, String w, int i) {
        if (i == w.length()) return true;
        if (r < 0 || c < 0 || r >= b.length || c >= b[0].length) return false;
        if (vis[r][c] || b[r][c] != w.charAt(i)) return false;

        vis[r][c] = true;
        boolean ok = dfsWord(b, vis, r + 1, c, w, i + 1)
                || dfsWord(b, vis, r - 1, c, w, i + 1)
                || dfsWord(b, vis, r, c + 1, w, i + 1)
                || dfsWord(b, vis, r, c - 1, w, i + 1);
        vis[r][c] = false;
        return ok;
    }

    // Optimized: Trie + one DFS over board
    static class WS2TrieNode {
        WS2TrieNode[] next = new WS2TrieNode[26];
        String word; // store full word at terminal
    }

    // Time: ~O(M*N*4^Lmax) but pruned heavily by trie | Space: O(total chars + recursion)
    public List<String> findWords(char[][] board, String[] words) {
        WS2TrieNode root = buildWS2Trie(words);
        List<String> res = new ArrayList<>();
        int m = board.length, n = board[0].length;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                dfsWS2(board, r, c, root, res);
            }
        }
        return res;
    }

    private WS2TrieNode buildWS2Trie(String[] words) {
        WS2TrieNode root = new WS2TrieNode();
        for (String w : words) {
            WS2TrieNode cur = root;
            for (int i = 0; i < w.length(); i++) {
                int idx = w.charAt(i) - 'a';
                if (cur.next[idx] == null) cur.next[idx] = new WS2TrieNode();
                cur = cur.next[idx];
            }
            cur.word = w;
        }
        return root;
    }

    private void dfsWS2(char[][] b, int r, int c, WS2TrieNode node, List<String> res) {
        if (r < 0 || c < 0 || r >= b.length || c >= b[0].length) return;
        char ch = b[r][c];
        if (ch == '#') return;

        WS2TrieNode nxt = node.next[ch - 'a'];
        if (nxt == null) return;

        if (nxt.word != null) {
            res.add(nxt.word);
            nxt.word = null; // de-dup
        }

        b[r][c] = '#';
        dfsWS2(b, r + 1, c, nxt, res);
        dfsWS2(b, r - 1, c, nxt, res);
        dfsWS2(b, r, c + 1, nxt, res);
        dfsWS2(b, r, c - 1, nxt, res);
        b[r][c] = ch;
    }

    // ==========================================================
    // 42) Number of Islands
    // ==========================================================

    // Brute-ish: BFS/DFS flood fill (standard)
    // Time: O(M*N) | Space: O(M*N) worst
    public int numIslandsBrute(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        boolean[][] vis = new boolean[m][n];
        int count = 0;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == '1' && !vis[r][c]) {
                    count++;
                    bfsIsland(grid, vis, r, c);
                }
            }
        }
        return count;
    }

    private void bfsIsland(char[][] g, boolean[][] vis, int sr, int sc) {
        int m = g.length, n = g[0].length;
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.add(new int[]{sr, sc});
        vis[sr][sc] = true;

        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int k = 0; k < 4; k++) {
                int nr = cur[0] + dr[k], nc = cur[1] + dc[k];
                if (nr < 0 || nc < 0 || nr >= m || nc >= n) continue;
                if (vis[nr][nc] || g[nr][nc] != '1') continue;
                vis[nr][nc] = true;
                q.add(new int[]{nr, nc});
            }
        }
    }

    // Optimized: Union-Find (also O(M*N), good for interview variety)
    // Time: O(M*N * α(MN)) | Space: O(M*N)
    public int numIslands(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        DSU dsu = new DSU(m * n);
        int lands = 0;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == '1') lands++;
                else dsu.block(r * n + c);
            }
        }

        int[] dr = {1, 0};
        int[] dc = {0, 1};
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] != '1') continue;
                int id = r * n + c;
                for (int k = 0; k < 2; k++) {
                    int nr = r + dr[k], nc = c + dc[k];
                    if (nr >= m || nc >= n) continue;
                    if (grid[nr][nc] != '1') continue;
                    if (dsu.union(id, nr * n + nc)) lands--;
                }
            }
        }
        return lands;
    }

    static class DSU {
        int[] parent, rank;
        boolean[] blocked;

        DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            blocked = new boolean[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        void block(int x) { blocked[x] = true; }

        int find(int x) {
            while (parent[x] != x) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return x;
        }

        boolean union(int a, int b) {
            if (blocked[a] || blocked[b]) return false;
            int ra = find(a), rb = find(b);
            if (ra == rb) return false;
            if (rank[ra] < rank[rb]) parent[ra] = rb;
            else if (rank[ra] > rank[rb]) parent[rb] = ra;
            else { parent[rb] = ra; rank[ra]++; }
            return true;
        }
    }

    // ==========================================================
    // 43) Clone Graph
    // ==========================================================

    static class Node {
        public int val;
        public List<Node> neighbors;
        public Node() { val = 0; neighbors = new ArrayList<>(); }
        public Node(int _val) { val = _val; neighbors = new ArrayList<>(); }
        public Node(int _val, ArrayList<Node> _neighbors) { val = _val; neighbors = _neighbors; }
    }

    // Brute-ish: BFS clone (standard) using map
    // Time: O(V+E) | Space: O(V)
    public Node cloneGraphBrute(Node node) {
        if (node == null) return null;
        Map<Node, Node> map = new HashMap<>();
        ArrayDeque<Node> q = new ArrayDeque<>();
        q.add(node);
        map.put(node, new Node(node.val));

        while (!q.isEmpty()) {
            Node cur = q.poll();
            for (Node nei : cur.neighbors) {
                if (!map.containsKey(nei)) {
                    map.put(nei, new Node(nei.val));
                    q.add(nei);
                }
                map.get(cur).neighbors.add(map.get(nei));
            }
        }
        return map.get(node);
    }

    // Optimized: DFS clone (same big-O, different style)
    // Time: O(V+E) | Space: O(V) (map + recursion)
    public Node cloneGraph(Node node) {
        return cloneDfs(node, new HashMap<>());
    }

    private Node cloneDfs(Node node, Map<Node, Node> map) {
        if (node == null) return null;
        if (map.containsKey(node)) return map.get(node);
        Node copy = new Node(node.val);
        map.put(node, copy);
        for (Node nei : node.neighbors) {
            copy.neighbors.add(cloneDfs(nei, map));
        }
        return copy;
    }

    // ==========================================================
    // 44) Pacific Atlantic Water Flow
    // ==========================================================

    // Brute: for each cell, DFS to check reach both oceans
    // Time: O(M*N*(M*N)) worst | Space: O(M*N)
    public List<List<Integer>> pacificAtlanticBrute(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        List<List<Integer>> res = new ArrayList<>();

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                boolean pac = canReach(heights, r, c, true);
                boolean atl = canReach(heights, r, c, false);
                if (pac && atl) res.add(Arrays.asList(r, c));
            }
        }
        return res;
    }

    private boolean canReach(int[][] h, int sr, int sc, boolean pacific) {
        int m = h.length, n = h[0].length;
        boolean[][] vis = new boolean[m][n];
        ArrayDeque<int[]> st = new ArrayDeque<>();
        st.push(new int[]{sr, sc});
        vis[sr][sc] = true;

        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        while (!st.isEmpty()) {
            int[] cur = st.pop();
            int r = cur[0], c = cur[1];

            if (pacific) {
                if (r == 0 || c == 0) return true;
            } else {
                if (r == m - 1 || c == n - 1) return true;
            }

            for (int k = 0; k < 4; k++) {
                int nr = r + dr[k], nc = c + dc[k];
                if (nr < 0 || nc < 0 || nr >= m || nc >= n) continue;
                if (vis[nr][nc]) continue;
                // water flows from high -> low; from start, you can go to lower/equal
                if (h[nr][nc] > h[r][c]) continue;
                vis[nr][nc] = true;
                st.push(new int[]{nr, nc});
            }
        }
        return false;
    }

    // Optimized: reverse DFS/BFS from oceans inward (non-decreasing heights)
    // Time: O(M*N) | Space: O(M*N)
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        boolean[][] pac = new boolean[m][n];
        boolean[][] atl = new boolean[m][n];

        for (int c = 0; c < n; c++) {
            dfsOcean(heights, 0, c, pac);
            dfsOcean(heights, m - 1, c, atl);
        }
        for (int r = 0; r < m; r++) {
            dfsOcean(heights, r, 0, pac);
            dfsOcean(heights, r, n - 1, atl);
        }

        List<List<Integer>> res = new ArrayList<>();
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (pac[r][c] && atl[r][c]) res.add(Arrays.asList(r, c));
            }
        }
        return res;
    }

    private void dfsOcean(int[][] h, int r, int c, boolean[][] seen) {
        int m = h.length, n = h[0].length;
        if (seen[r][c]) return;
        seen[r][c] = true;

        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};
        for (int k = 0; k < 4; k++) {
            int nr = r + dr[k], nc = c + dc[k];
            if (nr < 0 || nc < 0 || nr >= m || nc >= n) continue;
            if (seen[nr][nc]) continue;
            // reverse: can go to higher/equal
            if (h[nr][nc] < h[r][c]) continue;
            dfsOcean(h, nr, nc, seen);
        }
    }

    // ==========================================================
    // 45) Course Schedule
    // ==========================================================

    // Brute-ish: DFS cycle detection
    // Time: O(V+E) | Space: O(V+E)
    public boolean canFinishBrute(int numCourses, int[][] prerequisites) {
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) g.add(new ArrayList<>());
        for (int[] p : prerequisites) g.get(p[1]).add(p[0]);

        int[] state = new int[numCourses]; // 0 unvisited, 1 visiting, 2 done
        for (int i = 0; i < numCourses; i++) {
            if (state[i] == 0 && hasCycle(i, g, state)) return false;
        }
        return true;
    }

    private boolean hasCycle(int u, List<List<Integer>> g, int[] state) {
        state[u] = 1;
        for (int v : g.get(u)) {
            if (state[v] == 1) return true;
            if (state[v] == 0 && hasCycle(v, g, state)) return true;
        }
        state[u] = 2;
        return false;
    }

    // Optimized: Kahn's BFS topo
    // Time: O(V+E) | Space: O(V+E)
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) g.add(new ArrayList<>());
        int[] indeg = new int[numCourses];

        for (int[] p : prerequisites) {
            g.get(p[1]).add(p[0]);
            indeg[p[0]]++;
        }

        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) if (indeg[i] == 0) q.add(i);

        int taken = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            taken++;
            for (int v : g.get(u)) {
                if (--indeg[v] == 0) q.add(v);
            }
        }
        return taken == numCourses;
    }

    // ==========================================================
    // 46) Graph Valid Tree
    // ==========================================================

    // Brute-ish: DFS cycle + connectivity
    // Time: O(V+E) | Space: O(V+E)
    public boolean validTreeBrute(int n, int[][] edges) {
        if (edges.length != n - 1) return false; // necessary
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) g.add(new ArrayList<>());
        for (int[] e : edges) {
            g.get(e[0]).add(e[1]);
            g.get(e[1]).add(e[0]);
        }

        boolean[] vis = new boolean[n];
        if (dfsTree(0, -1, g, vis)) return false; // cycle found
        for (boolean b : vis) if (!b) return false; // not connected
        return true;
    }

    private boolean dfsTree(int u, int parent, List<List<Integer>> g, boolean[] vis) {
        vis[u] = true;
        for (int v : g.get(u)) {
            if (v == parent) continue;
            if (vis[v]) return true; // cycle
            if (dfsTree(v, u, g, vis)) return true;
        }
        return false;
    }

    // Optimized: Union-Find
    // Time: O(E α(N)) | Space: O(N)
    public boolean validTree(int n, int[][] edges) {
        if (edges.length != n - 1) return false;
        UF uf = new UF(n);
        for (int[] e : edges) {
            if (!uf.union(e[0], e[1])) return false; // cycle
        }
        return true;
    }

    static class UF {
        int[] p, r;
        UF(int n) { p = new int[n]; r = new int[n]; for (int i = 0; i < n; i++) p[i] = i; }
        int find(int x) { while (p[x] != x) { p[x] = p[p[x]]; x = p[x]; } return x; }
        boolean union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return false;
            if (r[ra] < r[rb]) p[ra] = rb;
            else if (r[ra] > r[rb]) p[rb] = ra;
            else { p[rb] = ra; r[ra]++; }
            return true;
        }
    }

    // ==========================================================
    // 47) Number of Connected Components in an Undirected Graph
    // ==========================================================

    // Brute-ish: DFS count
    // Time: O(V+E) | Space: O(V+E)
    public int countComponentsBrute(int n, int[][] edges) {
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) g.add(new ArrayList<>());
        for (int[] e : edges) {
            g.get(e[0]).add(e[1]);
            g.get(e[1]).add(e[0]);
        }

        boolean[] vis = new boolean[n];
        int comps = 0;
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                comps++;
                ArrayDeque<Integer> st = new ArrayDeque<>();
                st.push(i);
                vis[i] = true;
                while (!st.isEmpty()) {
                    int u = st.pop();
                    for (int v : g.get(u)) {
                        if (!vis[v]) {
                            vis[v] = true;
                            st.push(v);
                        }
                    }
                }
            }
        }
        return comps;
    }

    // Optimized: Union-Find
    // Time: O(E α(N)) | Space: O(N)
    public int countComponents(int n, int[][] edges) {
        UF uf = new UF(n);
        int comps = n;
        for (int[] e : edges) {
            if (uf.union(e[0], e[1])) comps--;
        }
        return comps;
    }

    // ==========================================================
    // 48) Alien Dictionary
    // ==========================================================

    // Brute: compare all pairs of words to add constraints (heavier)
    // Time: O(N^2 * L) | Space: O(U + E)
    public String alienOrderBrute(String[] words) {
        Map<Character, Set<Character>> g = new HashMap<>();
        Map<Character, Integer> indeg = new HashMap<>();
        for (String w : words) for (char ch : w.toCharArray()) {
            g.putIfAbsent(ch, new HashSet<>());
            indeg.putIfAbsent(ch, 0);
        }

        int n = words.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (!addEdge(words[i], words[j], g, indeg)) return "";
            }
        }
        return topoOrder(g, indeg);
    }

    // Optimized: compare adjacent only
    // Time: O(N * L) | Space: O(U + E)
    public String alienOrder(String[] words) {
        Map<Character, Set<Character>> g = new HashMap<>();
        Map<Character, Integer> indeg = new HashMap<>();
        for (String w : words) for (char ch : w.toCharArray()) {
            g.putIfAbsent(ch, new HashSet<>());
            indeg.putIfAbsent(ch, 0);
        }

        for (int i = 0; i < words.length - 1; i++) {
            if (!addEdge(words[i], words[i + 1], g, indeg)) return "";
        }
        return topoOrder(g, indeg);
    }

    private boolean addEdge(String a, String b, Map<Character, Set<Character>> g, Map<Character, Integer> indeg) {
        int i = 0, min = Math.min(a.length(), b.length());
        while (i < min && a.charAt(i) == b.charAt(i)) i++;

        // invalid: prefix case like "abc" before "ab"
        if (i == min && a.length() > b.length()) return false;

        if (i < min) {
            char u = a.charAt(i), v = b.charAt(i);
            if (!g.get(u).contains(v)) {
                g.get(u).add(v);
                indeg.put(v, indeg.get(v) + 1);
            }
        }
        return true;
    }

    private String topoOrder(Map<Character, Set<Character>> g, Map<Character, Integer> indeg) {
        ArrayDeque<Character> q = new ArrayDeque<>();
        for (char ch : indeg.keySet()) if (indeg.get(ch) == 0) q.add(ch);

        StringBuilder sb = new StringBuilder();
        while (!q.isEmpty()) {
            char u = q.poll();
            sb.append(u);
            for (char v : g.get(u)) {
                indeg.put(v, indeg.get(v) - 1);
                if (indeg.get(v) == 0) q.add(v);
            }
        }
        return sb.length() == indeg.size() ? sb.toString() : "";
    }

    // ==========================================================
    // 49) Climbing Stairs
    // ==========================================================

    // Time: O(2^N) | Space: O(N)
    public int climbStairsBrute(int n) {
        return climbRec(n);
    }

    private int climbRec(int n) {
        if (n == 0) return 1;
        if (n < 0) return 0;
        return climbRec(n - 1) + climbRec(n - 2);
    }

    // Time: O(N) | Space: O(1)
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

    // ==========================================================
    // 50) House Robber
    // ==========================================================

    // Time: O(2^N) | Space: O(N)
    public int robBrute(int[] nums) {
        return robRec(nums, 0);
    }

    private int robRec(int[] nums, int i) {
        if (i >= nums.length) return 0;
        return Math.max(nums[i] + robRec(nums, i + 2), robRec(nums, i + 1));
    }

    // Time: O(N) | Space: O(1)
    public int rob(int[] nums) {
        int prev2 = 0, prev1 = 0;
        for (int x : nums) {
            int pick = prev2 + x;
            int skip = prev1;
            int cur = Math.max(pick, skip);
            prev2 = prev1;
            prev1 = cur;
        }
        return prev1;
    }

    // ==========================================================
    // 51) House Robber II
    // ==========================================================

    // Time: O(2^N) | Space: O(N)
    public int rob2Brute(int[] nums) {
        if (nums.length == 1) return nums[0];
        // brute: recursion with two cases (exclude first or exclude last)
        return Math.max(robRangeRec(nums, 0, nums.length - 2, 0),
                robRangeRec(nums, 1, nums.length - 1, 0));
    }

    private int robRangeRec(int[] nums, int l, int r, int i) {
        int idx = l + i;
        if (idx > r) return 0;
        return Math.max(nums[idx] + robRangeRec(nums, l, r, i + 2),
                robRangeRec(nums, l, r, i + 1));
    }

    // Time: O(N) | Space: O(1)
    public int rob2(int[] nums) {
        if (nums.length == 1) return nums[0];
        return Math.max(robLinear(nums, 0, nums.length - 2),
                robLinear(nums, 1, nums.length - 1));
    }

    private int robLinear(int[] nums, int l, int r) {
        int prev2 = 0, prev1 = 0;
        for (int i = l; i <= r; i++) {
            int cur = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = cur;
        }
        return prev1;
    }

    // ==========================================================
    // 52) Longest Palindromic Substring
    // ==========================================================

    // Time: O(N^3) | Space: O(1)
    public String longestPalindromeBrute(String s) {
        int n = s.length();
        int bestL = 0, bestR = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (j - i > bestR - bestL && isPal(s, i, j)) {
                    bestL = i; bestR = j;
                }
            }
        }
        return s.substring(bestL, bestR + 1);
    }

    private boolean isPal(String s, int l, int r) {
        while (l < r) {
            if (s.charAt(l++) != s.charAt(r--)) return false;
        }
        return true;
    }

    // Time: O(N^2) | Space: O(1)
    public String longestPalindrome(String s) {
        int n = s.length();
        int bestL = 0, bestR = -1;

        for (int i = 0; i < n; i++) {
            int[] odd = expand(s, i, i);
            int[] even = expand(s, i, i + 1);
            int[] cur = (odd[1] - odd[0] >= even[1] - even[0]) ? odd : even;
            if (cur[1] - cur[0] > bestR - bestL) {
                bestL = cur[0]; bestR = cur[1];
            }
        }
        return s.substring(bestL, bestR + 1);
    }

    private int[] expand(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--; r++;
        }
        return new int[]{l + 1, r - 1};
    }

    // ==========================================================
    // 53) Palindromic Substrings
    // ==========================================================

    // Time: O(N^3) | Space: O(1)
    public int countSubstringsBrute(String s) {
        int n = s.length();
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (isPal(s, i, j)) cnt++;
            }
        }
        return cnt;
    }

    // Time: O(N^2) | Space: O(1)
    public int countSubstrings(String s) {
        int n = s.length();
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            cnt += countExpand(s, i, i);
            cnt += countExpand(s, i, i + 1);
        }
        return cnt;
    }

    private int countExpand(String s, int l, int r) {
        int cnt = 0;
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            cnt++;
            l--; r++;
        }
        return cnt;
    }

    // ==========================================================
    // 54) Decode Ways
    // ==========================================================

    // Time: O(2^N) | Space: O(N)
    public int numDecodingsBrute(String s) {
        return decodeRec(s, 0);
    }

    private int decodeRec(String s, int i) {
        if (i == s.length()) return 1;
        if (s.charAt(i) == '0') return 0;

        int ways = decodeRec(s, i + 1);
        if (i + 1 < s.length()) {
            int val = (s.charAt(i) - '0') * 10 + (s.charAt(i + 1) - '0');
            if (val >= 10 && val <= 26) ways += decodeRec(s, i + 2);
        }
        return ways;
    }

    // Time: O(N) | Space: O(1)
    public int numDecodings(String s) {
        int n = s.length();
        if (n == 0) return 0;

        int dp2 = 1; // dp[i+2]
        int dp1 = s.charAt(n - 1) == '0' ? 0 : 1; // dp[i+1] for i=n-2 base
        if (n == 1) return dp1;

        for (int i = n - 2; i >= 0; i--) {
            int cur;
            if (s.charAt(i) == '0') {
                cur = 0;
            } else {
                cur = dp1;
                int val = (s.charAt(i) - '0') * 10 + (s.charAt(i + 1) - '0');
                if (val >= 10 && val <= 26) cur += dp2;
            }
            dp2 = dp1;
            dp1 = cur;
        }
        return dp1;
    }

    // ==========================================================
    // 55) Coin Change
    // ==========================================================

    // Brute: try all combinations (DFS)
    // Time: exponential | Space: O(amount)
    public int coinChangeBrute(int[] coins, int amount) {
        int ans = coinDfs(coins, amount);
        return ans >= Integer.MAX_VALUE / 2 ? -1 : ans;
    }

    private int coinDfs(int[] coins, int rem) {
        if (rem == 0) return 0;
        if (rem < 0) return Integer.MAX_VALUE / 2;
        int best = Integer.MAX_VALUE / 2;
        for (int c : coins) {
            best = Math.min(best, 1 + coinDfs(coins, rem - c));
        }
        return best;
    }

    // Optimized: DP bottom-up
    // Time: O(amount * #coins) | Space: O(amount)
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int a = 1; a <= amount; a++) {
            for (int c : coins) {
                if (a - c >= 0) dp[a] = Math.min(dp[a], dp[a - c] + 1);
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    // ==========================================================
    // 56) Maximum Product Subarray
    // ==========================================================

    // Time: O(N^2) | Space: O(1)
    public int maxProductBrute(int[] nums) {
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int prod = 1;
            for (int j = i; j < nums.length; j++) {
                prod *= nums[j];
                best = Math.max(best, prod);
            }
        }
        return best;
    }

    // Time: O(N) | Space: O(1)
    public int maxProduct(int[] nums) {
        int curMax = nums[0], curMin = nums[0], ans = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int x = nums[i];
            if (x < 0) { int t = curMax; curMax = curMin; curMin = t; }
            curMax = Math.max(x, curMax * x);
            curMin = Math.min(x, curMin * x);
            ans = Math.max(ans, curMax);
        }
        return ans;
    }

    // ==========================================================
    // 57) Word Break
    // ==========================================================

    // Brute: recursion try all splits
    // Time: exponential | Space: O(N) recursion
    public boolean wordBreakBrute(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>(wordDict);
        return wbRec(s, 0, set);
    }

    private boolean wbRec(String s, int i, Set<String> dict) {
        if (i == s.length()) return true;
        for (int j = i + 1; j <= s.length(); j++) {
            if (dict.contains(s.substring(i, j)) && wbRec(s, j, dict)) return true;
        }
        return false;
    }

    // Optimized: DP
    // Time: O(N^2) | Space: O(N)
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    // ==========================================================
    // 58) Longest Increasing Subsequence
    // ==========================================================

    // Time: O(N^2) | Space: O(N)
    public int lengthOfLISBrute(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int best = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            best = Math.max(best, dp[i]);
        }
        return best;
    }

    // Optimized: patience sorting + binary search
    // Time: O(N log N) | Space: O(N)
    public int lengthOfLIS(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;

        for (int x : nums) {
            int l = 0, r = size;
            while (l < r) {
                int mid = l + (r - l) / 2;
                if (tails[mid] < x) l = mid + 1;
                else r = mid;
            }
            tails[l] = x;
            if (l == size) size++;
        }
        return size;
    }

    // ==========================================================
    // 59) Unique Paths
    // ==========================================================

    // Brute: recursion
    // Time: exponential | Space: O(m+n)
    public int uniquePathsBrute(int m, int n) {
        return upRec(0, 0, m, n);
    }

    private int upRec(int r, int c, int m, int n) {
        if (r == m - 1 && c == n - 1) return 1;
        if (r >= m || c >= n) return 0;
        return upRec(r + 1, c, m, n) + upRec(r, c + 1, m, n);
    }

    // Optimized: DP 1D
    // Time: O(m*n) | Space: O(n)
    public int uniquePaths(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int r = 1; r < m; r++) {
            for (int c = 1; c < n; c++) {
                dp[c] += dp[c - 1];
            }
        }
        return dp[n - 1];
    }

    // ==========================================================
    // 60) Longest Common Subsequence
    // ==========================================================
    // Time: O(2^(m+n)) | Space: O(m+n) (recursion stack)
    public int longestCommonSubsequenceBrute(String text1, String text2) {
        return lcsBrute(text1, text2, 0, 0);
    }

    private int lcsBrute(String a, String b, int i, int j) {
        if (i == a.length() || j == b.length()) return 0;
        if (a.charAt(i) == b.charAt(j)) return 1 + lcsBrute(a, b, i + 1, j + 1);
        return Math.max(lcsBrute(a, b, i + 1, j), lcsBrute(a, b, i, j + 1));
    }

    // Time: O(m*n) | Space: O(m*n)
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            char c1 = text1.charAt(i - 1);
            for (int j = 1; j <= n; j++) {
                char c2 = text2.charAt(j - 1);
                if (c1 == c2) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[m][n];
    }

    // ==========================================================
    // 61) Maximum Subarray
    // ==========================================================
    // Time: O(n^2) | Space: O(1)
    // ==========================================================
    // Input: nums = [2,-3,4,-2,2,1,-1,4]
    //
    // Output: 8
    // ==========================================================
    public int maxSubArrayBrute(int[] nums) {
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                best = Math.max(best, sum);
            }
        }
        return best;
    }

    // Time: O(n) | Space: O(1) (Kadane)
    public int maxSubArray(int[] nums) {
        int best = nums[0], cur = nums[0];
        for (int i = 1; i < nums.length; i++) {
            cur = Math.max(nums[i], cur + nums[i]);
            best = Math.max(best, cur);
        }
        return best;
    }

    // ==========================================================
    // 62) Jump Game
    // ==========================================================
    // Time: O(2^n) worst | Space: O(n) recursion stack
    public boolean canJumpBrute(int[] nums) {
        return canJumpDfs(nums, 0);
    }

    private boolean canJumpDfs(int[] nums, int i) {
        if (i >= nums.length - 1) return true;
        int far = Math.min(nums.length - 1, i + nums[i]);
        for (int next = i + 1; next <= far; next++) {
            if (canJumpDfs(nums, next)) return true;
        }
        return false;
    }

    // Time: O(n) | Space: O(1) (greedy)
    public boolean canJump(int[] nums) {
        int farthest = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > farthest) return false;
            farthest = Math.max(farthest, i + nums[i]);
            if (farthest >= nums.length - 1) return true;
        }
        return true;
    }

    // ==========================================================
    // 63) Insert Interval
    // ==========================================================
    // Brute idea: insert + sort + merge (doesn't assume input is sorted).
    // Time: O(n log n) | Space: O(n)
    public int[][] insertBrute(int[][] intervals, int[] newInterval) {
        int n = intervals.length;
        int[][] all = new int[n + 1][2];
        for (int i = 0; i < n; i++) all[i] = intervals[i];
        all[n] = newInterval;

        Arrays.sort(all, (a, b) -> a[0] - b[0]);

        List<int[]> merged = new ArrayList<>();
        for (int[] it : all) {
            if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < it[0]) {
                merged.add(new int[]{it[0], it[1]});
            } else {
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], it[1]);
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }

    // Optimized: input intervals are sorted and non-overlapping.
    // Time: O(n) | Space: O(n)
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> res = new ArrayList<>();
        int i = 0, n = intervals.length;

        // add all before newInterval
        while (i < n && intervals[i][1] < newInterval[0]) {
            res.add(intervals[i]);
            i++;
        }

        // merge overlaps
        int start = newInterval[0], end = newInterval[1];
        while (i < n && intervals[i][0] <= end) {
            start = Math.min(start, intervals[i][0]);
            end = Math.max(end, intervals[i][1]);
            i++;
        }
        res.add(new int[]{start, end});

        // add rest
        while (i < n) {
            res.add(intervals[i]);
            i++;
        }

        return res.toArray(new int[res.size()][]);
    }

    // ==========================================================
    // 64) Merge Intervals
    // ==========================================================
    // Brute: repeatedly merge any overlapping pair until stable.
    // Time: O(n^2) worst | Space: O(n)
    public int[][] mergeBrute(int[][] intervals) {
        List<int[]> list = new ArrayList<>();
        for (int[] it : intervals) list.add(new int[]{it[0], it[1]});

        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    int[] a = list.get(i), b = list.get(j);
                    if (overlap(a, b)) {
                        int[] m = new int[]{Math.min(a[0], b[0]), Math.max(a[1], b[1])};
                        list.remove(j);
                        list.set(i, m);
                        changed = true;
                        break;
                    }
                }
                if (changed) break;
            }
        }

        list.sort((x, y) -> x[0] - y[0]);
        return list.toArray(new int[list.size()][]);
    }

    private boolean overlap(int[] a, int[] b) {
        return Math.max(a[0], b[0]) <= Math.min(a[1], b[1]);
    }

    // Time: O(n log n) | Space: O(n)
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        List<int[]> res = new ArrayList<>();
        for (int[] it : intervals) {
            if (res.isEmpty() || res.get(res.size() - 1)[1] < it[0]) {
                res.add(new int[]{it[0], it[1]});
            } else {
                res.get(res.size() - 1)[1] = Math.max(res.get(res.size() - 1)[1], it[1]);
            }
        }
        return res.toArray(new int[res.size()][]);
    }

    // ==========================================================
    // 65) Non-overlapping Intervals
    // ==========================================================
    // Brute-ish DP (like LIS on sorted-by-start):
    // pick maximum set of non-overlapping => answer = n - maxKeep
    // Time: O(n^2) | Space: O(n)
    public int eraseOverlapIntervalsBrute(int[][] intervals) {
        if (intervals.length == 0) return 0;
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        int n = intervals.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int bestKeep = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (intervals[j][1] <= intervals[i][0]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            bestKeep = Math.max(bestKeep, dp[i]);
        }
        return n - bestKeep;
    }

    // Greedy by earliest end time.
    // Time: O(n log n) | Space: O(1) extra (sort in-place)
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length == 0) return 0;
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

        int removed = 0;
        int prevEnd = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < prevEnd) {
                removed++;
            } else {
                prevEnd = intervals[i][1];
            }
        }
        return removed;
    }

    // ==========================================================
    // 66) Meeting Rooms
    // ==========================================================
    // Brute: check every pair overlap.
    // Time: O(n^2) | Space: O(1)
    public boolean canAttendMeetingsBrute(int[][] intervals) {
        for (int i = 0; i < intervals.length; i++) {
            for (int j = i + 1; j < intervals.length; j++) {
                if (overlapHalfOpen(intervals[i], intervals[j])) return false;
            }
        }
        return true;
    }

    // Time: O(n log n) | Space: O(1) extra (sort in-place)
    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1]) return false;
        }
        return true;
    }

    // Half-open overlap for meeting intervals [start, end)
    private boolean overlapHalfOpen(int[] a, int[] b) {
        int start = Math.max(a[0], b[0]);
        int end = Math.min(a[1], b[1]);
        return start < end;
    }

    // ==========================================================
    // 67) Meeting Rooms II
    // ==========================================================
    // Brute: maintain list of room end times; scan to find a free room each time.
    // Time: O(n^2) | Space: O(n)
    public int minMeetingRoomsBrute(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        List<Integer> ends = new ArrayList<>();

        for (int[] it : intervals) {
            boolean placed = false;
            for (int i = 0; i < ends.size(); i++) {
                if (ends.get(i) <= it[0]) {
                    ends.set(i, it[1]);
                    placed = true;
                    break;
                }
            }
            if (!placed) ends.add(it[1]);
        }
        return ends.size();
    }

    // Optimized: min-heap by earliest end.
    // Time: O(n log n) | Space: O(n)
    public int minMeetingRooms(int[][] intervals) {
        if (intervals.length == 0) return 0;
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(intervals[0][1]);

        for (int i = 1; i < intervals.length; i++) {
            if (pq.peek() <= intervals[i][0]) pq.poll();
            pq.offer(intervals[i][1]);
        }
        return pq.size();
    }

    // ==========================================================
    // 68) Rotate Image
    // ==========================================================
    // Brute: create a new matrix.
    // Time: O(n^2) | Space: O(n^2)
    public void rotateBrute(int[][] matrix) {
        int n = matrix.length;
        int[][] copy = new int[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                copy[c][n - 1 - r] = matrix[r][c];
            }
        }
        for (int r = 0; r < n; r++) {
            System.arraycopy(copy[r], 0, matrix[r], 0, n);
        }
    }

    // Optimized in-place: transpose + reverse each row.
    // Time: O(n^2) | Space: O(1)
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        // transpose
        for (int r = 0; r < n; r++) {
            for (int c = r + 1; c < n; c++) {
                int tmp = matrix[r][c];
                matrix[r][c] = matrix[c][r];
                matrix[c][r] = tmp;
            }
        }

        // reverse each row
        for (int r = 0; r < n; r++) {
            int l = 0, rr = n - 1;
            while (l < rr) {
                int tmp = matrix[r][l];
                matrix[r][l] = matrix[r][rr];
                matrix[r][rr] = tmp;
                l++;
                rr--;
            }
        }
    }

    // ==========================================================
    // 69) Spiral Matrix
    // ==========================================================
    // Brute: visited grid simulation.
    // Time: O(m*n) | Space: O(m*n)
    public List<Integer> spiralOrderBrute(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean[][] seen = new boolean[m][n];
        List<Integer> res = new ArrayList<>(m * n);

        int[] dr = {0, 1, 0, -1};
        int[] dc = {1, 0, -1, 0};
        int r = 0, c = 0, di = 0;

        for (int k = 0; k < m * n; k++) {
            res.add(matrix[r][c]);
            seen[r][c] = true;

            int nr = r + dr[di], nc = c + dc[di];
            if (nr < 0 || nr >= m || nc < 0 || nc >= n || seen[nr][nc]) {
                di = (di + 1) % 4;
                nr = r + dr[di];
                nc = c + dc[di];
            }
            r = nr;
            c = nc;
        }

        return res;
    }

    // Optimized: boundaries.
    // Time: O(m*n) | Space: O(1) extra
    public List<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        List<Integer> res = new ArrayList<>(m * n);

        int top = 0, bottom = m - 1, left = 0, right = n - 1;

        while (top <= bottom && left <= right) {
            for (int c = left; c <= right; c++) res.add(matrix[top][c]);
            top++;

            for (int r = top; r <= bottom; r++) res.add(matrix[r][right]);
            right--;

            if (top <= bottom) {
                for (int c = right; c >= left; c--) res.add(matrix[bottom][c]);
                bottom--;
            }

            if (left <= right) {
                for (int r = bottom; r >= top; r--) res.add(matrix[r][left]);
                left++;
            }
        }

        return res;
    }

    // ==========================================================
    // 70) Set Matrix Zeroes
    // ==========================================================
    // Brute: create a copy and zero accordingly.
    // Time: O(m*n) | Space: O(m*n)
    public void setZeroesBrute(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] copy = new int[m][n];
        for (int r = 0; r < m; r++) System.arraycopy(matrix[r], 0, copy[r], 0, n);

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (copy[r][c] == 0) {
                    for (int k = 0; k < n; k++) matrix[r][k] = 0;
                    for (int k = 0; k < m; k++) matrix[k][c] = 0;
                }
            }
        }
    }

    // Optimized: use first row/col as markers.
    // Time: O(m*n) | Space: O(1)
    public void setZeroes(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;

        boolean firstRowZero = false, firstColZero = false;

        for (int c = 0; c < n; c++) if (matrix[0][c] == 0) firstRowZero = true;
        for (int r = 0; r < m; r++) if (matrix[r][0] == 0) firstColZero = true;

        for (int r = 1; r < m; r++) {
            for (int c = 1; c < n; c++) {
                if (matrix[r][c] == 0) {
                    matrix[r][0] = 0;
                    matrix[0][c] = 0;
                }
            }
        }

        for (int r = 1; r < m; r++) {
            for (int c = 1; c < n; c++) {
                if (matrix[r][0] == 0 || matrix[0][c] == 0) matrix[r][c] = 0;
            }
        }

        if (firstRowZero) for (int c = 0; c < n; c++) matrix[0][c] = 0;
        if (firstColZero) for (int r = 0; r < m; r++) matrix[r][0] = 0;
    }

    // ==========================================================
    // 71) Number of 1 Bits
    // ==========================================================
    // Brute: check 32 bits.
    // Time: O(1) | Space: O(1)
    public int hammingWeightBrute(int n) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            count += (n >>> i) & 1;
        }
        return count;
    }

    // Optimized: Brian Kernighan's trick.
    // Time: O(k) where k=#set bits | Space: O(1)
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n &= (n - 1);
            count++;
        }
        return count;
    }

    // ==========================================================
    // 72) Counting Bits
    // ==========================================================
    // Brute: count bits for each i.
    // Time: O(n * 32) => O(n) | Space: O(1) besides output
    public int[] countBitsBrute(int n) {
        int[] ans = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            ans[i] = hammingWeightBrute(i);
        }
        return ans;
    }

    // Optimized DP: ans[i] = ans[i >> 1] + (i & 1)
    // Time: O(n) | Space: O(1) besides output
    public int[] countBits(int n) {
        int[] ans = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            ans[i] = ans[i >> 1] + (i & 1);
        }
        return ans;
    }

    // ==========================================================
    // 73) Reverse Bits
    // ==========================================================
    // Brute: build reversed bit-by-bit.
    // Time: O(1) (32 steps) | Space: O(1)
    public int reverseBitsBrute(int n) {
        int res = 0;
        for (int i = 0; i < 32; i++) {
            res <<= 1;
            res |= (n & 1);
            n >>>= 1;
        }
        return res;
    }

    // Optimized: bit tricks with masks (still O(1), fewer ops than 32-loop).
    // Time: O(1) | Space: O(1)
    public int reverseBits(int n) {
        n = (n >>> 16) | (n << 16);
        n = ((n & 0xff00ff00) >>> 8) | ((n & 0x00ff00ff) << 8);
        n = ((n & 0xf0f0f0f0) >>> 4) | ((n & 0x0f0f0f0f) << 4);
        n = ((n & 0xcccccccc) >>> 2) | ((n & 0x33333333) << 2);
        n = ((n & 0xaaaaaaaa) >>> 1) | ((n & 0x55555555) << 1);
        return n;
    }

    // ==========================================================
    // 74) Missing Number
    // ==========================================================
    // Brute: use boolean seen array.
    // Time: O(n) | Space: O(n)
    public int missingNumberBrute(int[] nums) {
        boolean[] seen = new boolean[nums.length + 1];
        for (int x : nums) seen[x] = true;
        for (int i = 0; i < seen.length; i++) {
            if (!seen[i]) return i;
        }
        return -1;
    }

    // Optimized: XOR.
    // Time: O(n) | Space: O(1)
    public int missingNumber(int[] nums) {
        int xor = 0;
        for (int i = 0; i <= nums.length; i++) xor ^= i;
        for (int x : nums) xor ^= x;
        return xor;
    }

    // ==========================================================
    // 75) Sum of Two Integers
    // ==========================================================
    // Brute: use '+' (not allowed by the original LC constraint, but included as brute baseline).
    // Time: O(1) | Space: O(1)
    public int getSumBrute(int a, int b) {
        return a + b;
    }

    // Optimized: bitwise addition (no + or -).
    // Time: O(1) (at most 32 iterations) | Space: O(1)
    public int getSum(int a, int b) {
        while (b != 0) {
            int carry = (a & b) << 1;
            a = a ^ b;
            b = carry;
        }
        return a;
    }
}


