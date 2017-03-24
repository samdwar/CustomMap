import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by sam on 18/8/16.
 */
class CustomMap<K, V> implements GenericMap<K, V> {

    private LinkedList<Entry<K, V>>[] hashTable;
    /*Init with initial size of Map*/
    private static final int SIZE_OF_MAP = 5;

    public CustomMap() {
        hashTable = new LinkedList[SIZE_OF_MAP];

    }


    /*
    * Class that hold key and value and a pointer so that we can point to next item.
    * */
    static class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
        }


        /* Override hasCode method to generate unique hashCode*/
        @Override
        public int hashCode() {
            int prime = 13;
            int mul = 11;
            if (key != null) {
                int hashCode = prime * mul + key.hashCode();
                return hashCode;
            }
            return 0;
        }

        /*Override equals method to compare if any collison happens in the bucket due to repeated hascode*/
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass().getName() != o.getClass().getName()) {
                return false;
            }
            Entry entry = (Entry) o;
            if (this.key == entry.key) {
                return true;
            }
            return false;
        }
    }


    @Override
    public void put(K newKey, V data) {
        if (newKey == null)
            return;

        synchronized (CustomMap.class) {
            int hash = hash(newKey);
            Entry<K, V> newEntryForMap = new Entry<K, V>(newKey, data, null);
            if (hashTable[hash] == null) {
                hashTable[hash] = new LinkedList<>();
                hashTable[hash].add(newEntryForMap);
            } else {
                LinkedList<Entry<K, V>> entryLinkedList = hashTable[hash];
                Iterator<Entry<K, V>> iterator = entryLinkedList.listIterator();
                while (iterator.hasNext()) {
                    Entry<K, V> entry = iterator.next();
                    if (entry.key.equals(newKey)) {
                        entry.value = data;
                        return;
                    }
                }
                hashTable[hash].add(newEntryForMap);
            }
        }
    }

    @Override
    public V get(K key) {
        int hash = hash(key);
        if (hashTable[hash] == null) {
            return null;
        } else {
            LinkedList<Entry<K, V>> currentEntryList = hashTable[hash];
            for (Entry<K, V> currentEntry : currentEntryList) {
                if (currentEntry.key.equals(key))
                    return currentEntry.value;
            }
            return null;
        }
    }

    @Override
    public boolean delete(K keyForDelete) {
        synchronized (CustomMap.class) {
            int hash = hash(keyForDelete);

            if (hashTable[hash] == null) {
                return false;
            } else {
                LinkedList<Entry<K, V>> entryLinkedList = hashTable[hash];

                Iterator<Entry<K, V>> iterator = entryLinkedList.listIterator();
                while (iterator.hasNext()) {
                    Entry<K, V> entry = iterator.next();
                    if (entry.key.equals(keyForDelete)) {
                        iterator.remove();
                        return true;
                    }
                }
            }
            return false;
        }
    }


    @Override
    public boolean exist(K key) {
        int hash = hash(key);
        if (hashTable[hash] == null) {
            return false;
        } else {
            LinkedList<Entry<K, V>> entryLinkedList = hashTable[hash];
            Iterator<Entry<K, V>> iterator = entryLinkedList.listIterator();
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();
                if (entry.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void showAllKeyValueInMap() {

        for (int i = 0; i < SIZE_OF_MAP; i++) {
            if (hashTable[i] != null) {
                LinkedList<Entry<K, V>> entryList = hashTable[i];
                for (Entry<K, V> entry : entryList) {
                    System.out.println(entry.key + "->" + entry.value);
                }
            }
        }

    }


    private int hash(K key) {
        return Math.abs(key.hashCode()) % SIZE_OF_MAP;
    }

}



