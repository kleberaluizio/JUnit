package binary_search;

import java.util.Arrays;

public class Main {
    public static void main(String[] args)
    {
        int[] arr = new int[10000];

        for(int i=1;i<=10000;i++) {
            arr[i-1] = i;
        }
        int target = 99;
        int low = 0;
        int high = arr.length - 1;
        int mid;

        int iterations = 0;
        // Use of efficient algorithms such
        // as binary search
        while (low <= high) {
            System.out.println("Iteration: " + iterations++);

            mid = (low + high) / 2;

            if (arr[mid] == target) {
                System.out.println("Target found at index "
                        + mid);
                return;
            }

            else if (arr[mid] > target) {
                high = mid - 1;
            }

            else {
                low = mid + 1;
            }
        }
        System.out.println("Target not found");
    }
}
