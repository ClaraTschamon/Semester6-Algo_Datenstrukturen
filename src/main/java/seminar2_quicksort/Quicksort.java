package seminar2_quicksort;

import java.util.Arrays;

public class Quicksort {

    public static void main(String[] args) {
        Quicksort quicksort = new Quicksort();
        //int[] initialArray = new int [] {12, 1, 9, 16, 7, 6, 8, 2, 4, 10, 16, 0, 3, 20, 5};
        int[] initialArray = new int [] {12, 1, 9, 4, 7, 10};

        System.out.println("Before:");
        System.out.println(Arrays.toString(initialArray));

        int[] newArray = quicksort.quicksort(initialArray, 0, initialArray.length - 1);

        System.out.println("\nAfter:");
        System.out.println(Arrays.toString(newArray));
    }

    public int[] quicksort(int[] array, int lowIndex, int highIndex) {
        if (lowIndex >= highIndex) {
            return array;
        }

        int pivot = partition(array, lowIndex, highIndex);

        quicksort(array, lowIndex, pivot-1);
        quicksort(array, pivot+1, highIndex);

        return array;
    }


    private int partition(int[] array, int lowIndex, int highIndex) {
        int pivot = array[highIndex]; // choose the last element as the pivot
        int upperPointer = lowIndex - 1;

        for (int lowerPointer = lowIndex; lowerPointer < highIndex; lowerPointer++) {
            if (array[lowerPointer] < pivot) {
                upperPointer++;
                // swap array[upperPointer] and array[lowerPointer]
                int temp = array[upperPointer];
                array[upperPointer] = array[lowerPointer];
                array[lowerPointer] = temp;
                // print the array after each swap
                System.out.println(Arrays.toString(array));
            }
        }

        // swap array[upperPointer+1] and array[highIndex]
        // am schluss wird das pivot element, welches ganz hinten ist,
        // an die richtige stelle (lowindex+1) getauscht.
        int temp = array[upperPointer+1];
        array[upperPointer+1] = array[highIndex];
        array[highIndex] = temp;
        return upperPointer+1;
    }
}
