import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TreePrinterTest {

    private static final String PERSONS_DEFAULT = "Adam Ivan\n" +
            "Marko Stjepan\n" +
            "Stjepan Adam\n" +
            "Robert Stjepan\n" +
            "Fran Ivan\n" +
            "Leopold Luka";
    private static final String PERSONS_SEPARATE_PARENTS = PERSONS_DEFAULT + "\nMarko Marija";
    private static final String FILENAME = "test";
    private static final String RESULT_DEFAULT = "Ivan\n" +
            "\tAdam\n" +
            "\t\tStjepan\n" +
            "\t\t\tMarko\n" +
            "\t\t\tRobert\n" +
            "\tFran\n" +
            "Luka\n" +
            "\tLeopold\n";
    private static final String RESULT_IVAN = "Ivan\n" +
            "\tAdam\n" +
            "\t\tStjepan\n" +
            "\t\t\tMarko\n" +
            "\t\t\tRobert\n" +
            "\tFran\n";
    private static final String RESULT_SEPARATE_PARENTS = RESULT_DEFAULT +
            "Marija\n" +
            "\tMarko\n";
    private static final String EMPTY_STRING = "";

    private FileHandler fileHandler = null;

    @Test
    public void printTreeShouldReturnFullFamilyTreeFromFile() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(FILENAME));
            out.write(PERSONS_DEFAULT);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = TreePrinter.printTree(FILENAME);
        Assert.assertEquals(RESULT_DEFAULT, result);

        File file = new File(FILENAME);
        file.delete();
    }

    @Test
    public void printTreeShouldReturnEmptyStringForEmptyFile() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(FILENAME));
            out.write("");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = TreePrinter.printTree(FILENAME);
        Assert.assertEquals(EMPTY_STRING, result);

        File file = new File(FILENAME);
        file.delete();
    }

    @Test
    public void printTreeShouldReturnTwoParentOfSamePersonSeparated() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(FILENAME));
            out.write(PERSONS_SEPARATE_PARENTS);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = TreePrinter.printTree(FILENAME);
        Assert.assertEquals(RESULT_SEPARATE_PARENTS, result);

        File file = new File(FILENAME);
        file.delete();
    }

    @Test
    public void recursionShouldAddPersonsTreeToStringBuilder() {
        ArrayList<Person> personList = new java.util.ArrayList<Person>();
        personList.add(new Person("Ivan", new ArrayList<String>(Arrays.asList("Adam", "Fran"))));
        personList.add(new Person("Stjepan", new ArrayList<String>(Arrays.asList("Marko", "Robert"))));
        personList.add(new Person("Adam", new ArrayList<String>(Collections.singletonList("Stjepan"))));
        personList.add(new Person("Luka", new ArrayList<String>(Collections.singletonList("Leopold"))));

        StringBuilder stringBuilder = new StringBuilder();
        TreePrinter.recursion(stringBuilder, personList, new ArrayList<String>(), "Ivan");

        Assert.assertEquals(RESULT_IVAN, stringBuilder.toString());
    }

    @Test
    public void findParentlessShouldReturnListOfParentlessPersons() {
        ArrayList<Person> personList = new java.util.ArrayList<Person>();
        personList.add(new Person("Ivan", new ArrayList<String>(Arrays.asList("Adam", "Fran"))));
        personList.add(new Person("Stjepan", new ArrayList<String>(Arrays.asList("Marko", "Robert"))));
        personList.add(new Person("Adam", new ArrayList<String>(Collections.singletonList("Stjepan"))));
        personList.add(new Person("Luka", new ArrayList<String>(Collections.singletonList("Leopold"))));

        ArrayList<String> result = TreePrinter.findParentless(personList);
        Assert.assertEquals(new ArrayList<String>(Arrays.asList("Ivan", "Luka")), result);
    }

    @Test
    public void findParentlessShouldReturnEmptyListForEmptyList() {
        ArrayList<Person> personList = new java.util.ArrayList<Person>();

        ArrayList<String> result = TreePrinter.findParentless(personList);
        Assert.assertEquals(new ArrayList<String>(), result);
    }
}
