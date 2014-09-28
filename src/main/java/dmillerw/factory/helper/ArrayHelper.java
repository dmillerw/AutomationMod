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
}
