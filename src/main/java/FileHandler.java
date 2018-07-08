import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {

    private String file;

    public FileHandler(String file) {
        this.file = file;
    }

    public ArrayList<Person> getPersons() {
        ArrayList<Person> personList = new ArrayList<Person>();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(this.file));

            String line;
            while((line = br.readLine()) != null) {
                String[] tmp = line.split(" ");
                Person person = findPerson(tmp[1], personList);
                if(person == null) {
                    personList.add(new Person(tmp[1], tmp[0]));
                }
                else {
                    person.addKid(tmp[0]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Error reading the line.");
        }
        return personList;
    }

    public static Person findPerson(String name, ArrayList<Person> personList) {
        for(Person person : personList) {
            if(person.getName().equals(name)) return person;
        }
        return null;
    }

    public static void findCyclicRelationship(ArrayList<Person> personList) throws CyclicRelationshipException {
        for(Person person : personList) {
            findCyclicRelationshipByPerson(personList, new ArrayList<String>(), person.getName());
        }
    }

    private static void findCyclicRelationshipByPerson(ArrayList<Person> personList, ArrayList<String> grandparents, String name) throws CyclicRelationshipException {
        Person person = FileHandler.findPerson(name, personList);
        if(grandparents.contains(name)) throw new CyclicRelationshipException();
        if(person == null) return;
        for(String childName : person.getKids()) {
            ArrayList<String> newGrandparents = new ArrayList<String>(grandparents);
            newGrandparents.add(name);
            findCyclicRelationshipByPerson(personList, newGrandparents, childName);
        }
    }
}
