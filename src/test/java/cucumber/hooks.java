package cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.After;

public class hooks {
    @Before
    public void setup()
    {
        System.out.println("Before scenario");
    }
    @After
    public void teardown()
    {
        System.out.println("After scenario");
    }
}
