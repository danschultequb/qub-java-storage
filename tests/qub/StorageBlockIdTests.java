package qub;

public class StorageBlockIdTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(StorageBlockId.class, () ->
        {
            runner.test("createRandom()", (Test test) ->
            {
                final StorageBlockId id = StorageBlockId.createRandom();
                test.assertNotEqual(id, StorageBlockId.createRandom());
            });

            runner.testGroup("parseString(String)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    test.assertThrows(() -> StorageBlockId.parseString(null), new NullPointerException());
                });

                runner.test("with empty", (Test test) ->
                {
                    test.assertThrows(() -> StorageBlockId.parseString(""), new IllegalArgumentException("Invalid UUID string: "));
                });

                runner.test("with d47a458f-279f-41c3-a10b-61e47b503b78", (Test test) ->
                {
                    final StorageBlockId id = StorageBlockId.parseString("d47a458f-279f-41c3-a10b-61e47b503b78");
                    test.assertEqual("d47a458f-279f-41c3-a10b-61e47b503b78", id.toString());
                });
            });

            runner.testGroup("equals(Object)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    test.assertFalse(StorageBlockId.createRandom().equals((Object)null));
                });

                runner.test("with \"id\"", (Test test) ->
                {
                    test.assertFalse(StorageBlockId.createRandom().equals((Object)"id"));
                });

                runner.test("with same", (Test test) ->
                {
                    final StorageBlockId id = StorageBlockId.createRandom();
                    test.assertTrue(id.equals((Object)id));
                });
            });

            runner.testGroup("equals(StorageBlockId)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    test.assertFalse(StorageBlockId.createRandom().equals((StorageBlockId)null));
                });

                runner.test("with same", (Test test) ->
                {
                    final StorageBlockId id = StorageBlockId.createRandom();
                    test.assertTrue(id.equals(id));
                });
            });

            runner.test("hashCode()", (Test test) ->
            {
                final StorageBlockId id = StorageBlockId.createRandom();
                test.assertEqual(id.hashCode(), id.hashCode());
            });
        });
    }
}
