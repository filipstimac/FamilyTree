import java.util.ArrayList;

public class Person {

    private String name;
    private int nrKids;
    private ArrayList<String> kids;

    public Person(String name, String kidName) {
        this.name = name;
        this.nrKids = 1;
        this.kids = new ArrayList<String>();
        this.kids.add(kidName);
    }

    public Person(String name, ArrayList<String> kids) {
        this.name = name;
        this.kids = kids;
        this.nrKids = this.kids.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNrKids() {
        return nrKids;
    }

    public void setNrKids(int nrKids) {
        this.nrKids = nrKids;
    }

    public ArrayList<String> getKids() {
        return kids;
    }

    public void setKids(ArrayList<String> kids) {
        this.kids = kids;
    }

    public void addKid(String name) {
        this.nrKids++;
        this.kids.add(name);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", nrKids=" + nrKids +
                ", kids=" + kids +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if(object == null) return false;
        if(!(object instanceof Person)) return false;
        final Person person = (Person) object;
        return (person.name != null && this.name.equals(person.name) && this.nrKids == person.nrKids && this.kids.equals(person.kids));
    }
}
