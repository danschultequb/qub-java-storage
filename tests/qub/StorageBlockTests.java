package qub;

public class StorageBlockTests
{
    private static InMemoryStorageBlockManager createBlockManager(Test test)
    {
        return new InMemoryStorageBlockManager(test.getClock());
    }

    public static void test(TestRunner runner)
    {
        runner.testGroup(StorageBlock.class, () ->
        {
            runner.testGroup("equals(Object)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    final StorageBlockId id = StorageBlockId.createRandom();
                    final StorageBlock block = new StorageBlock(blockManager, id);

                    test.assertFalse(block.equals((Object)null));
                });

                runner.test("with non-StorageBlock", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    final StorageBlockId id = StorageBlockId.createRandom();
                    final StorageBlock block = new StorageBlock(blockManager, id);

                    test.assertFalse(block.equals((Object)"test"));
                });

                runner.test("with StorageBlock with same manager but different id", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    final StorageBlockId id1 = StorageBlockId.createRandom();
                    final StorageBlock block = new StorageBlock(blockManager, id1);

                    final StorageBlockId id2 = StorageBlockId.createRandom();
                    test.assertNotEqual(id1, id2);
                    test.assertFalse(block.equals((Object)new StorageBlock(blockManager, id2)));
                });

                runner.test("with StorageBlock with same manager and equal id", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    final StorageBlockId id1 = StorageBlockId.createRandom();
                    final StorageBlock block = new StorageBlock(blockManager, id1);

                    final StorageBlockId id2 = StorageBlockId.parseString(id1.toString());
                    test.assertEqual(id1, id2);
                    test.assertTrue(block.equals((Object)new StorageBlock(blockManager, id2)));
                });

                runner.test("with StorageBlock with same manager and same id", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    final StorageBlockId id = StorageBlockId.createRandom();
                    final StorageBlock block = new StorageBlock(blockManager, id);

                    test.assertTrue(block.equals((Object)new StorageBlock(blockManager, id)));
                });

                runner.test("with StorageBlock with different manager and same id", (Test test) ->
                {
                    final StorageBlockManager blockManager1 = createBlockManager(test);

                    final StorageBlockId id = StorageBlockId.createRandom();
                    final StorageBlock block = new StorageBlock(blockManager1, id);

                    final StorageBlockManager blockManager2 = createBlockManager(test);

                    test.assertFalse(block.equals((Object)new StorageBlock(blockManager2, id)));
                });
            });

            runner.testGroup("equals(StorageBlock)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    final StorageBlockId id = StorageBlockId.createRandom();
                    final StorageBlock block = new StorageBlock(blockManager, id);

                    test.assertFalse(block.equals((StorageBlock)null));
                });

                runner.test("with StorageBlock with same manager but different id", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    final StorageBlockId id1 = StorageBlockId.createRandom();
                    final StorageBlock block = new StorageBlock(blockManager, id1);

                    final StorageBlockId id2 = StorageBlockId.createRandom();
                    test.assertNotEqual(id1, id2);
                    test.assertFalse(block.equals(new StorageBlock(blockManager, id2)));
                });

                runner.test("with StorageBlock with same manager and equal id", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    final StorageBlockId id1 = StorageBlockId.createRandom();
                    final StorageBlock block = new StorageBlock(blockManager, id1);

                    final StorageBlockId id2 = StorageBlockId.parseString(id1.toString());
                    test.assertEqual(id1, id2);
                    test.assertTrue(block.equals(new StorageBlock(blockManager, id2)));
                });

                runner.test("with StorageBlock with same manager and same id", (Test test) ->
                {
                    final StorageBlockManager blockManager = createBlockManager(test);

                    final StorageBlockId id = StorageBlockId.createRandom();
                    final StorageBlock block = new StorageBlock(blockManager, id);

                    test.assertTrue(block.equals(new StorageBlock(blockManager, id)));
                });

                runner.test("with StorageBlock with different manager and same id", (Test test) ->
                {
                    final StorageBlockManager blockManager1 = createBlockManager(test);

                    final StorageBlockId id = StorageBlockId.createRandom();
                    final StorageBlock block = new StorageBlock(blockManager1, id);

                    final StorageBlockManager blockManager2 = createBlockManager(test);

                    test.assertFalse(block.equals(new StorageBlock(blockManager2, id)));
                });
            });
        });
    }
}
