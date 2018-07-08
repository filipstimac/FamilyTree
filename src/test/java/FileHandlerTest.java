import org.junit.Test;

public class FileHandlerTest {

    @Test
    public void getPersonsShouldReturnArrayListContainingAllPersons() {
        FileHandler tester = new FileHandler("relationships");
        tester.getPersons();
    }

}
