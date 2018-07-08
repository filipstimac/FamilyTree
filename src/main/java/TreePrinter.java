import java.util.ArrayList;

public class TreePrinter {

    public static void main(String[] args) {
        FileHandler fh = new FileHandler("relationships");
        ArrayList<Person> personList = fh.getPersons();

        try {
            FileHandler.findCyclicRelationship(personList);
        } catch (CyclicRelationshipException e) {
            System.out.println("ERROR: Cyclic relationship detected.");
            System.exit(1);
        }

        ArrayList<String> parentless = findParentless(personList);

        StringBuilder sb = new StringBuilder();
        for(String parentlessPerson: parentless) recursion(sb, personList, new ArrayList<String>(), parentlessPerson);
        System.out.println(sb);
    }

    private static void recursion(StringBuilder sb, ArrayList<Person> personList, ArrayList<String> grandparents, String name) {
        Person person = FileHandler.findPerson(name, personList);
        for(String grandparent : grandparents) sb.append("\t");
        sb.append(name);
        sb.append("\n");
        if(person == null) return;
        for(String childName : person.getKids()) {
            ArrayList<String> newGrandparents = new ArrayList<String>(grandparents);
            newGrandparents.add(name);
            recursion(sb, personList, newGrandparents, childName);
        }
    }

    private static ArrayList<String> findParentless(ArrayList<Person> personList) {
        ArrayList<String> parentless = new ArrayList<String>();
        boolean isChild = false;
        for(Person person1 : personList) {
            for(Person person2 : personList) {
                if(person2.getKids().contains(person1.getName())) {
                    isChild = true;
                    break;
                }
            }
            if(!isChild) parentless.add(person1.getName());
            isChild = false;
        }
        return parentless;
    }
}