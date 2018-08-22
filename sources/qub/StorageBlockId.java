package qub;

public class StorageBlockId
{
    private final GUID guid;

    private StorageBlockId(GUID guid)
    {
        this.guid = guid;
    }

    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof StorageBlockId && equals((StorageBlockId)rhs);
    }

    public boolean equals(StorageBlockId rhs)
    {
        return rhs != null && guid.equals(rhs.guid);
    }

    @Override
    public int hashCode()
    {
        return guid.hashCode();
    }

    @Override
    public String toString()
    {
        return guid.toString();
    }

    public static StorageBlockId createRandom()
    {
        return new StorageBlockId(GUID.createRandom());
    }

    public static StorageBlockId parseString(String storageBlockIdString)
    {
        return new StorageBlockId(GUID.parseString(storageBlockIdString));
    }
}
