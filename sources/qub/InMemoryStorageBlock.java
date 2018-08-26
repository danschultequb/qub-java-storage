package qub;

public class InMemoryStorageBlock
{
    private final StorageBlockId id;
    private final byte[] contents;
    private final DateTime timestamp;

    public InMemoryStorageBlock(StorageBlockId id, byte[] contents, DateTime timestamp)
    {
        this.id = id;
        this.contents = contents;
        this.timestamp = timestamp;
    }

    public StorageBlockId getId()
    {
        return id;
    }

    public byte[] getContents()
    {
        return contents;
    }

    public DateTime getTimestamp()
    {
        return timestamp;
    }
}
