package ca.indigogames.ubloxagps.utility;

public class Array {
    public static <TData> void reverse(TData[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            TData tmp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = tmp;
        }
    }
}
