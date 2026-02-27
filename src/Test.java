
public class Test {
    public static void main(String[] args) {

        B b = new B();
        // upcast

        System.out.println(((A) b).value);  // ?
        System.out.println(b.value);  // ?

        b.print();  // ?
        b.print();  // ?
    }
}

class A {
    String value = "A";

    void print() {
        System.out.println("A print: " + value);
    }
}

class B extends A {
    String value = "B";

    @Override
    void print() {
        System.out.println("B print: " + value);
    }
}

