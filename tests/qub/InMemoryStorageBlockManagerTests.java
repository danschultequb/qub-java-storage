package qub;

public class InMemoryStorageBlockManagerTests
{
    private static InMemoryStorageBlockManager createBlockManager(Test test)
    {
        return new InMemoryStorageBlockManager(test.getClock());
    }

    public static void test(TestRunner runner)
    {
        runner.testGroup(InMemoryStorageBlockManager.class, () ->
        {
            runner.testGroup("constructor", () ->
            {
                runner.test("with null clock", (Test test) ->
                {
                    test.assertThrows(() -> new InMemoryStorageBlockManager(null));
                });
            });

            runner.testGroup("createStorageBlock(Action1<StorageBlockWriter>)", () ->
            {
                runner.test("with null action", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    test.assertThrows(() -> blockManager.createStorageBlock(null));
                });

                runner.test("with empty action", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    final DateTime beforeBlockCreation = test.getClock().getCurrentDateTime();

                    final StorageBlock block1 = blockManager.createStorageBlock((ByteWriteStream writer) -> {});
                    test.assertNotNull(block1);
                    test.assertNotNull(block1.getId());

                    final StorageBlock block2 = blockManager.getStorageBlock(block1.getId());
                    test.assertNotNull(block2);
                    test.assertNotNull(block2.getId());
                    test.assertEqual(block2.getId(), block1.getId());
                    test.assertEqual(block2, block1);

                    test.assertSuccess(true, block1.exists());
                    test.assertSuccess(true, blockManager.storageBlockExists(block1.getId()));

                    final Result<DateTime> blockTimestamp = block1.getTimestamp();
                    test.assertSuccess(blockTimestamp);
                    test.assertLessThanOrEqualTo(beforeBlockCreation, blockTimestamp.getValue());
                });

                runner.test("with non-empty action", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    final DateTime beforeBlockCreation = test.getClock().getCurrentDateTime();

                    final StorageBlock block = blockManager.createStorageBlock((ByteWriteStream writer) ->
                    {
                        writer.write(new byte[] { 0, 1, 2, 3, 4 });
                    });
                    test.assertNotNull(block);
                    test.assertNotNull(block.getId());
                    test.assertSuccess(true, block.exists());

                    final Result<ByteReadStream> contentsResult = block.getContents();
                    test.assertSuccess(contentsResult);
                    final Result<byte[]> contentsArrayResult = contentsResult.getValue().readAllBytes();
                    test.assertSuccess(new byte[] { 0, 1, 2, 3, 4 }, contentsArrayResult);

                    final Result<DateTime> blockTimestamp = block.getTimestamp();
                    test.assertSuccess(blockTimestamp);
                    test.assertLessThanOrEqualTo(beforeBlockCreation, blockTimestamp.getValue());
                });
            });

            runner.testGroup("deleteStorageBlock(StorageBlockId)", () ->
            {
                runner.test("with null id", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    test.assertThrows(() -> blockManager.deleteStorageBlock(null));
                });

                runner.test("with id of block that doesn't exist", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    final StorageBlockId id = StorageBlockId.createRandom();
                    test.assertError(new NotFoundException("StorageBlock with id " + id), blockManager.deleteStorageBlock(id));
                    test.assertSuccess(false, blockManager.storageBlockExists(id));
                });

                runner.test("with id of block that exists", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    final StorageBlock block = blockManager.createStorageBlock((ByteWriteStream writeStream) -> {});
                    test.assertSuccess(true, block.exists());

                    test.assertSuccess(true, block.delete());
                    test.assertSuccess(false, block.exists());

                    test.assertError(new NotFoundException("StorageBlock with id " + block.getId()), block.delete());
                    test.assertSuccess(false, block.exists());
                });
            });

            runner.testGroup("getStorageBlock(StorageBlockId)", () ->
            {
                runner.test("with null id", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    test.assertThrows(() -> blockManager.getStorageBlock(null));
                });

                runner.test("with id of block that doesn't exist", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    final StorageBlock block = blockManager.getStorageBlock(StorageBlockId.createRandom());
                    test.assertNotNull(block);
                    test.assertNotNull(block.getId());
                    test.assertSuccess(false, block.exists());
                });
            });

            runner.testGroup("storageBlockExists(StorageBlockId)", () ->
            {
                runner.test("with null id", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    test.assertThrows(() -> blockManager.storageBlockExists(null));
                });

                runner.test("with id of block that doesn't exist", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    test.assertSuccess(false, blockManager.storageBlockExists(StorageBlockId.createRandom()));
                });
            });

            runner.testGroup("getStorageBlockContents(StorageBlockId)", () ->
            {
                runner.test("with null id", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    test.assertThrows(() -> blockManager.getStorageBlockContents(null));
                });

                runner.test("with id of block that doesn't exist", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    final StorageBlockId id = StorageBlockId.createRandom();
                    test.assertError(new NotFoundException("StorageBlock with id " + id), blockManager.getStorageBlockContents(id));
                });
            });

            runner.testGroup("getStorageBlockTimestamp(StorageBlockId)", () ->
            {
                runner.test("with null id", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    test.assertThrows(() -> blockManager.getStorageBlockTimestamp(null));
                });

                runner.test("with id of block that doesn't exist", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    final StorageBlockId id = StorageBlockId.createRandom();
                    test.assertError(new NotFoundException("StorageBlock with id " + id), blockManager.getStorageBlockTimestamp(id));
                });
            });
        });
    }
}