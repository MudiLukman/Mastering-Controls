import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Person {

    private IntegerProperty age;
    private StringProperty name;
    private ObjectProperty<LocalDate> dob;

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name=" + name +
                ", dob=" + dob +
                '}';
    }

    public Person() {
    }

    public Person(IntegerProperty age, StringProperty name, ObjectProperty<LocalDate> dob) {

        this.age = age;
        this.name = name;
        this.dob = dob;
    }

    public int getAge() {

        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public LocalDate getDob() {
        return dob.get();
    }

    public ObjectProperty<LocalDate> dobProperty() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob.set(dob);
    }
}
