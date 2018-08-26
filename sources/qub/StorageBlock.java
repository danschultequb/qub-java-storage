package qub;

/**
 * A StorageBlock within a QubStorage system.
 */
public class StorageBlock
{
    private final StorageBlockManager manager;
    private final StorageBlockId id;

    /**
     * Create a new reference to a StorageBlock that is stored in the StorageBlockManager with the
     * provided StorageBlockId.
     * @param manager The manager that contains the StorageBlock.
     * @param id The id of the StorageBlock.
     */
    public StorageBlock(StorageBlockManager manager, StorageBlockId id)
    {
        this.manager = manager;
        this.id = id;
    }

    /**
     * Get the StorageBlockId of this StorageBlock.
     * @return The StorageBlockId of this StorageBlock.
     */
    public StorageBlockId getId()
    {
        return id;
    }

    /**
     * Get whether or not this StorageBlock exists.
     * @return Whether or not this StorageBlock exists.
     */
    public Result<Boolean> exists()
    {
        return manager.storageBlockExists(id);
    }

    /**
     * Get the timestamp that this StorageBlock was created at.
     * @return The timestamp that this StorageBlock was created at.
     */
    public Result<DateTime> getTimestamp()
    {
        return manager.getStorageBlockTimestamp(id);
    }

    /**
     * Get the contents of this StorageBlock.
     * @return The contents of this StorageBlock.
     */
    public Result<ByteReadStream> getContents()
    {
        return manager.getStorageBlockContents(id);
    }

    /**
     * Delete this StorageBlock.
     * @return Whether or not this StorageBlock was deleted.
     */
    public Result<Boolean> delete()
    {
        return manager.deleteStorageBlock(id);
    }

    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof StorageBlock && equals((StorageBlock)rhs);
    }

    public boolean equals(StorageBlock rhs)
    {
        return rhs != null && this.manager.equals(rhs.manager) && this.id.equals(rhs.id);
    }
}
