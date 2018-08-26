package qub;

/**
 * An interface that specifies the operations that a StorageBlockManager must implement. A
 * StorageBlockManager is the core of QubStorage.
 */
public interface StorageBlockManager
{
    /**
     * Create a new StorageBlock using the provided action. The bytes that are written will be
     * given a new StorageBlockId and the StorageBlock is returned.
     * @param createBlockContentsAction The Action that will be invoked to create the StorageBlock's
     *                                  contents.
     * @return The newly created StorageBlock.
     */
    StorageBlock createStorageBlock(Action1<ByteWriteStream> createBlockContentsAction);

    /**
     * Get whether or not a StorageBlock with the provided id exists.
     * @param id The id of the StorageBlock.
     * @return Whether or not a StorageBlock with the provided id exists.
     */
    Result<Boolean> storageBlockExists(StorageBlockId id);

    /**
     * Get a reference to the StorageBlock with the provided id.
     * @param id The id of the StorageBlock.
     * @return A StorageBlock reference with the provided id.
     */
    StorageBlock getStorageBlock(StorageBlockId id);

    /**
     * Delete the StorageBlock with the provided id.
     * @param id The id of the StorageBlock.
     * @return Whether or not the StorageBlock was deleted.
     */
    Result<Boolean> deleteStorageBlock(StorageBlockId id);

    /**
     * Get the timestamp that the StorageBlock with the provided id was created at.
     * @param id The id of the StorageBlock.
     * @return The timestamp that the StorageBlock with the provided id was created at.
     */
    Result<DateTime> getStorageBlockTimestamp(StorageBlockId id);

    /**
     * Get the contents of the StorageBlock with the provided id.
     * @param id The id of the StorageBlock.
     * @return The contents of the StorageBlock with the provided id.
     */
    Result<ByteReadStream> getStorageBlockContents(StorageBlockId id);
}