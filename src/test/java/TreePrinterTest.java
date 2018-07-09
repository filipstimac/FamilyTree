import org.junit.Before;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.when;

public class TreePrinterTest {

    private FileHandler fileHandler = null;

    @Before
    public void beforeEachTest() {
        ArrayList<Person> personList = new java.util.ArrayList<Person>();
        personList.add(new Person("Ivan", new ArrayList<String>(Arrays.asList("Adam", "Fran"))));
        personList.add(new Person("Adam", new ArrayList<String>(Arrays.asList("Stjepan"))));
        personList.add(new Person("Stjepan", new ArrayList<String>(Arrays.asList("Marko", "Robert"))));
        personList.add(new Person("Luka", new ArrayList<String>(Arrays.asList("Leopold"))));

        fileHandler = Mockito.mock(FileHandler.class);
        //when(fileHandler.getPersons()).thenReturn(personList);
    }

}
