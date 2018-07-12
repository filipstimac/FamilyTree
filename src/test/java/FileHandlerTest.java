import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FileHandlerTest {

    private static final String PERSONS_DEFAULT = "Adam Ivan\n" +
            "Marko Stjepan\n" +
            "Stjepan Adam\n" +
            "Robert Stjepan\n" +
            "Fran Ivan\n" +
            "Leopold Luka";
    private static final String PERSONS_CYCLIC = PERSONS_DEFAULT + "\nIvan Marko";
    private static final String FILENAME = "test";

    private FileHandler fileHandler = null;

    @Before
    public void beforeEachTest() {
        this.fileHandler = new FileHandler(FILENAME);
    }

    @Test
    public void getPersonsShouldReturnArrayListContainingAllPersons() throws CyclicRelationshipException {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(FILENAME));
            out.write(PERSONS_DEFAULT);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileHandler tester = new FileHandler(FILENAME);
        ArrayList<Person> result = tester.getPersons();
        ArrayList<Person> expected = new java.util.ArrayList<Person>();
        expected.add(new Person("Ivan", new ArrayList<String>(Arrays.asList("Adam", "Fran"))));
        expected.add(new Person("Stjepan", new ArrayList<String>(Arrays.asList("Marko", "Robert"))));
        expected.add(new Person("Adam", new ArrayList<String>(Collections.singletonList("Stjepan"))));
        expected.add(new Person("Luka", new ArrayList<String>(Collections.singletonList("Leopold"))));

        Assert.assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    public void getPersonsShouldReturnEmptyArrayEmptyFile() throws CyclicRelationshipException {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(FILENAME));
            out.write("");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileHandler tester = new FileHandler(FILENAME);
        ArrayList<Person> result = tester.getPersons();

        Assert.assertArrayEquals((new ArrayList<Person>()).toArray(), result.toArray());
    }

    @Test(expected = CyclicRelationshipException.class)
    public void getPersonShouldThrowExceptionIfCyclicDetected() throws CyclicRelationshipException {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(FILENAME));
            out.write(PERSONS_CYCLIC);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.fileHandler.getPersons();
    }

    @Test
    public void findPersonShouldReturnCorrectPerson() throws CyclicRelationshipException {
        ArrayList<Person> personList = new java.util.ArrayList<Person>();
        personList.add(new Person("Ivan", new ArrayList<String>(Arrays.asList("Adam", "Fran"))));

        Person expected = new Person("Ivan", new ArrayList<String>(Arrays.asList("Adam", "Fran")));
        Person result = FileHandler.findPerson("Ivan", personList);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void FindPersonShouldReturnNullIfNotFound() {
        ArrayList<Person> personList = new java.util.ArrayList<Person>();

        Person result = FileHandler.findPerson("Ivan", personList);
        Assert.assertNull(result);
    }

    @Test(expected = CyclicRelationshipException.class)
    public void findCyclicRelationshipShouldThrowExceptionWhenDetected() throws CyclicRelationshipException {
        ArrayList<Person> personList = new java.util.ArrayList<Person>();
        personList.add(new Person("Ivan", new ArrayList<String>(Arrays.asList("Stjepan", "Fran"))));
        personList.add(new Person("Stjepan", new ArrayList<String>(Arrays.asList("Ivan", "Robert"))));
        this.fileHandler.setPersonList(personList);

        this.fileHandler.findCyclicRelationship();
    }

    @After
    public void afterEachTest() {
        File file = new File(FILENAME);
        file.delete();
    }
}
