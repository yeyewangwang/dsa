import java.util.ArrayList;
import java.util.function.Consumer;

public class SimpleSort {

    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int current = arr[i];
            int j = i - 1;
            // Shift elements of arr[0...i-1] that are greater than current.
            // to one position ahead of their current position.
            // [GPT FIXED] add j >= 0, not arr[j] = arr[j + 1]
            while (j >= 0 && arr[j] >= current) {
                arr[j + 1] = arr[j];
                j--;
            }
            // Place the current element at the correct position.
            arr[j + 1] = current;
        }
    }

    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            // Bubble up the ith largest element
            // move the highest unsorted value to its correct position.
            for (int j = 0; j < n - i - 1; j++) {
                // Swap arr[j + 1] and arr[j]
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped)
                break; // No swaps means array is already sorted.
        }
    }

    public static void mergeSort(int[] arr) {
        mergeSortWithIndex(arr, 0, arr.length - 1);
    }

    private static void mergeSortWithIndex(int[] arr, int i, int j) {
        // Note that i and j are inclusive indices
        if (i == j)
            return; // Base case
        int m = Math.floorDiv(i + j, 2);
        mergeSortWithIndex(arr, i, m); // Sort left half
        mergeSortWithIndex(arr, m + 1, j); // Sort right half

        merge(arr, i, m, j);
    }

    private static void merge(int[] arr, int i, int m, int j) {
        int[] left = new int[m - i + 1];
        int[] right = new int[j - m];

        System.arraycopy(arr, i, left, 0, m - i + 1);
        System.arraycopy(arr, m + 1, right, 0, j - m);

        // Merge the first and the second half
        // You are stuck with the last of the longer left partition, only if you
        // have exhastively looked at all of the right partition.
        int leftIdx = 0, rightIdx = 0, k = i;
        while (leftIdx < left.length && rightIdx < right.length) {
            if (left[leftIdx] > right[rightIdx]) {
                arr[k++] = right[rightIdx++];
            } else {
                arr[k++] = left[leftIdx++];
            }
        }

        while (leftIdx < left.length) {
            arr[k++] = left[leftIdx++];
        }
        while (rightIdx < right.length) {
            arr[k++] = right[rightIdx++];
        }

    }

    public static void main(String[] args) {
        int[] arr = { 12, 2, 1, 9, 8 };
        int[] arr2 = { 0, 1 };
        int[] arr3 = { 1, 0 };
        int[] arr4 = { 1 };
        int[] arr5 = { -3, -5, 5, -9 };
        ArrayList<int[]> testArrays = new ArrayList<>();
        testArrays.add(arr);
        testArrays.add(arr2);
        testArrays.add(arr3);
        testArrays.add(arr4);
        testArrays.add(arr5);

        // Assign all sorting functions to be sorted as Consumer objects.
        Consumer<int[]> insertionSort = x -> insertionSort(x);
        Consumer<int[]> bubbleSort = x -> bubbleSort(x);
        Consumer<int[]> mergeSort = x -> mergeSort(x);
        ArrayList<Consumer<int[]>> functions = new ArrayList<>();
        functions.add(insertionSort);
        functions.add(bubbleSort);
        functions.add(mergeSort);

        for (Consumer<int[]> sortFunc : functions) {
            // Copy all test arrays.
            ArrayList<int[]> testArrsCopy = new ArrayList<>();
            for (int[] testArr : testArrays) {
                int[] arrCopy = new int[testArr.length];
                System.arraycopy(testArr, 0, arrCopy, 0, testArr.length);
                testArrsCopy.add(arrCopy);
            }

            for (int[] currArr : testArrsCopy) {
                System.out.print("Original: ");
                for (int num : currArr) {
                    System.out.print(num + " ");
                }
                System.out.println();

                sortFunc.accept(currArr);

                System.out.print("Sorted: ");
                for (int num : currArr) {
                    System.out.print(num + " ");
                }
                System.out.println();
            }
        }
    }
}