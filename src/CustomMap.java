/**
 * Created by sam on 18/8/16.
 */
class CustomMap<K, V> implements GenericMap<K, V> {

    private Entry<K, V>[] hashTable;
    /*Init with initial size of Map*/
    private static final int SIZE_OF_MAP = 5;

    public CustomMap() {
        hashTable = new Entry[SIZE_OF_MAP];

    }


    /*
    * Class that hold key and value and a pointer so that we can point to next item.
    * */
    static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
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
                hashTable[hash] = newEntryForMap;
            } else {
                Entry<K, V> previous = null;
                Entry<K, V> current = hashTable[hash];

                while (current != null) {
                    if (current.key.equals(newKey)) {
                        if (previous == null) {
                            /*Bcoz previous is null we are on first node on that bucket*/
                            newEntryForMap.next = current.next;
                            hashTable[hash] = newEntryForMap;
                            return;
                        } else {
                            newEntryForMap.next = current.next;
                            previous.next = newEntryForMap;
                            return;
                        }
                    }
                    previous = current;
                    current = current.next;
                }
                previous.next = newEntryForMap;
            }
        }
    }

    @Override
    public V get(K key) {
        int hash = hash(key);
        if (hashTable[hash] == null) {
            return null;
        } else {
            Entry<K, V> currentEntry = hashTable[hash];
            while (currentEntry != null) {
                if (currentEntry.key.equals(key))
                    return currentEntry.value;
                currentEntry = currentEntry.next;
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
                Entry<K, V> previous = null;
                Entry<K, V> current = hashTable[hash];

                while (current != null) {
                    if (current.key.equals(keyForDelete)) {
                        if (previous == null) {
                            /*We are on the first node it self so point to next*/
                            hashTable[hash] = hashTable[hash].next;
                            return true;
                        } else {
                            previous.next = current.next;
                            return true;
                        }
                    }
                    previous = current;
                    current = current.next;
                }
                return false;
            }
        }
    }

    @Override
    public boolean exist(K key) {
        int hash = hash(key);
        if (hashTable[hash] == null) {
            return false;
        } else {
            Entry<K, V> currentEntry = hashTable[hash];
            while (currentEntry != null) {
                if (currentEntry.key.equals(key)) {
                    return true;
                }
                currentEntry = currentEntry.next;
            }
        }
        return false;
    }

    @Override
    public void showAllKeyValueInMap() {

        for (int i = 0; i < SIZE_OF_MAP; i++) {
            if (hashTable[i] != null) {
                Entry<K, V> entry = hashTable[i];
                while (entry != null) {
                    System.out.println(entry.key + "->" + entry.value);
                    entry = entry.next;
                }
            }
        }

    }


    private int hash(K key) {
        return Math.abs(key.hashCode()) % SIZE_OF_MAP;
    }

}



