import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {

    private String file;
    private ArrayList<Person> personList;

    public FileHandler(String file) {
        this.file = file;
        this.personList = new ArrayList<Person>();
    }

    public ArrayList<Person> getPersons() throws CyclicRelationshipException {
        this.personList = new ArrayList<Person>();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(this.file));

            String line;
            while((line = br.readLine()) != null) {
                String[] tmp = line.split(" ");
                Person person = findPerson(tmp[1], this.personList);
                if(person == null) {
                    this.personList.add(new Person(tmp[1], tmp[0]));
                }
                else {
                    person.addKid(tmp[0]);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Error reading the line.");
            System.exit(1);
        }
        findCyclicRelationship();
        return this.personList;
    }

    static Person findPerson(String name, ArrayList<Person> personList) {
        for(Person person : personList) {
            if(person.getName().equals(name)) return person;
        }
        return null;
    }

    public void findCyclicRelationship() throws CyclicRelationshipException {
        for(Person person : this.personList) {
            findCyclicRelationshipByPerson(new ArrayList<String>(), person.getName());
        }
    }

    public void findCyclicRelationshipByPerson(ArrayList<String> grandparents, String name) throws CyclicRelationshipException {
        Person person = findPerson(name, this.personList);
        if(grandparents.contains(name)) throw new CyclicRelationshipException();
        if(person == null) return;
        for(String childName : person.getKids()) {
            ArrayList<String> newGrandparents = new ArrayList<String>(grandparents);
            newGrandparents.add(name);
            findCyclicRelationshipByPerson(newGrandparents, childName);
        }
    }

    public void setPersonList(ArrayList<Person> personList) {
        this.personList = personList;
    }
}
