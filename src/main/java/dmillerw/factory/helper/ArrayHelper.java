package dmillerw.factory.helper;

import java.lang.reflect.Array;

/**
 * @author dmillerw
 */
public class ArrayHelper {

    public static <T> T[] handleGenericArray(Object object, Class<T> type) {
        T[] array = (T[]) Array.newInstance(type, ((Object[]) object).length);
        for (int i = 0; i < ((Object[]) object).length; i++) {
            array[i] = (T) ((Object[]) object)[i];
        }
        return array;
    }

    public static <T> T[] rotateArray(Class<T> type, T[] array, int size) {
        T[] ret = (T[]) Array.newInstance(type, size * size);
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                ret[i + j * size] = array[size - j - 1 + i * size];
            }
        }
        return ret;
    }
}
