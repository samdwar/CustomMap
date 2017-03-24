/**
 * Created by sam on 18/8/16.
 */


public class Main {

    public static void main(String[] args) {
        CustomMap<Integer, Integer> customMap = new CustomMap<Integer, Integer>();
        System.out.println("Integer Value Map");
        customMap.put(21, 12);
        customMap.put(25, 121);
        customMap.put(30, 151);
        customMap.put(33, 15);
        customMap.put(35, 89);
        customMap.put(45, 98);
        customMap.put(55, 898);
        customMap.put(55, 897);
        System.out.println("customMap.get(25) = " + customMap.get(25));

        System.out.println("All Value in Map");
        customMap.showAllKeyValueInMap();

        customMap.delete(21);
        System.out.println("After Removing key 21");
        customMap.showAllKeyValueInMap();

        System.out.println("String Value Map");
        CustomMap<Integer, String> newStringMap = new CustomMap<>();

        newStringMap.put(1, "String value1");
        newStringMap.put(2, "String value2");
        System.out.println("newStringMap.get(2) = " + newStringMap.get(2));
        newStringMap.showAllKeyValueInMap();

        System.out.println("Double Value Map");
        CustomMap<Integer, Double> doubleCustomMap = new CustomMap<>();
        doubleCustomMap.put(1, 23.56);
        doubleCustomMap.put(2, 234.54);
        doubleCustomMap.showAllKeyValueInMap();

        System.out.println("Long value Map");
        CustomMap<Integer, Long> longCustomMap = new CustomMap<>();
        longCustomMap.put(1, 2l);
        longCustomMap.put(2, 3l);
        longCustomMap.showAllKeyValueInMap();

        System.out.println("longCustomMap.exist(1) = " + longCustomMap.exist(1));
        System.out.println("longCustomMap.exist(3) = " + longCustomMap.exist(3));


    }
}
