package qub;

public class InMemoryStorageBlockManager implements StorageBlockManager
{
    private final Clock clock;
    private final List<InMemoryStorageBlock> blocks;

    public InMemoryStorageBlockManager(Clock clock)
    {
        PreCondition.assertNotNull(clock, "clock");

        this.blocks = new ArrayList<InMemoryStorageBlock>();
        this.clock = clock;
    }

    @Override
    public StorageBlock createStorageBlock(Action1<ByteWriteStream> createBlockContentsAction)
    {
        PreCondition.assertNotNull(createBlockContentsAction, "createBlockContentsAction");

        final InMemoryByteStream contents = new InMemoryByteStream();

        createBlockContentsAction.run(contents);

        contents.endOfStream();

        final DateTime timestamp = clock.getCurrentDateTime();
        final StorageBlockId blockId = StorageBlockId.createRandom();
        blocks.add(new InMemoryStorageBlock(blockId, contents.getBytes(), timestamp));

        contents.dispose();

        final StorageBlock result = new StorageBlock(this, blockId);

        PostCondition.assertNotNull(result, "result");

        return result;
    }

    @Override
    public Result<Boolean> storageBlockExists(StorageBlockId id)
    {
        PreCondition.assertNotNull(id, "id");

        final Result<Boolean> result = (getInMemoryStorageBlock(id) != null ? Result.successTrue() : Result.successFalse());

        PostCondition.assertNotNull(result, "result");

        return result;
    }

    @Override
    public StorageBlock getStorageBlock(StorageBlockId id)
    {
        PreCondition.assertNotNull(id, "id");

        final StorageBlock result = new StorageBlock(this, id);

        PostCondition.assertNotNull(result, "result");

        return result;
    }

    @Override
    public Result<Boolean> deleteStorageBlock(final StorageBlockId id)
    {
        PreCondition.assertNotNull(id, "id");

        Result<Boolean> result;

        final InMemoryStorageBlock removedBlock = blocks.removeFirst(new Function1<InMemoryStorageBlock,Boolean>()
        {
            @Override
            public Boolean run(InMemoryStorageBlock block)
            {
                return block.getId().equals(id);
            }
        });
        if (removedBlock == null)
        {
            result = Result.<Boolean>error(new NotFoundException("StorageBlock with id " + id));
        }
        else
        {
            result = Result.successTrue();
        }

        PostCondition.assertNotNull(result, "result");

        return result;
    }

    @Override
    public Result<DateTime> getStorageBlockTimestamp(StorageBlockId id)
    {
        PreCondition.assertNotNull(id, "id");

        Result<DateTime> result;

        final InMemoryStorageBlock inMemoryBlock = getInMemoryStorageBlock(id);
        if (inMemoryBlock == null)
        {
            result = Result.<DateTime>error(new NotFoundException("StorageBlock with id " + id));
        }
        else
        {
            result = Result.<DateTime>success(inMemoryBlock.getTimestamp());
        }

        PostCondition.assertNotNull(result, "result");

        return result;
    }

    @Override
    public Result<ByteReadStream> getStorageBlockContents(StorageBlockId id)
    {
        PreCondition.assertNotNull(id, "id");

        Result<ByteReadStream> result;

        final InMemoryStorageBlock inMemoryBlock = getInMemoryStorageBlock(id);
        if (inMemoryBlock == null)
        {
            result = Result.<ByteReadStream>error(new NotFoundException("StorageBlock with id " + id));
        }
        else
        {
            result = Result.<ByteReadStream>success(new InMemoryByteStream(inMemoryBlock.getContents()).endOfStream());
        }

        PostCondition.assertNotNull(result, "result");

        return result;
    }

    private InMemoryStorageBlock getInMemoryStorageBlock(final StorageBlockId id)
    {
        return blocks.first(new Function1<InMemoryStorageBlock,Boolean>()
        {
            @Override
            public Boolean run(InMemoryStorageBlock block)
            {
                return block.getId().equals(id);
            }
        });
    }
}