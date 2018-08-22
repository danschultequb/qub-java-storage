package qub;

public class InMemoryStorageBlockManagerTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(InMemoryStorageBlockManager.class, () ->
        {
            runner.test("constructor", (Test test) ->
            {
                test.assertNotNull(new InMemoryStorageBlockManager());
            });
        });
    }
}