package com.mainuldip.Payroll;
import java.util.Optional;
class JavaOptional {
    public static void main(String[] args)
    {

        // creating a string array
        String[] str = new String[5];

        // Setting value for 2nd index
        str[2] = "Testing Optionals in Java";

        // It returns an empty instance of Optional class
        Optional<String> empty = Optional.empty();
        System.out.println(empty); // Optional.empty

        // It returns a non-empty Optional
        Optional<String> value = Optional.of(str[2]);
        System.out.println(value); // Optional[Testing Optionals in Java]
        System.out.println(value.get()); // Testing Optionals in Java
        System.out.println(value.hashCode()); // 1119689728
        System.out.println(value.isPresent());// true

        Optional<String> emptyString = Optional.empty();
        String opCheck1 = emptyString.isPresent() ? emptyString.get() : "Its still not initialized";
        System.out.println(opCheck1); // Its still not initialized
        @SuppressWarnings("unused")
        String opCheck2 = emptyString.isPresent() ? emptyString.get() : emptyString.orElseThrow(()->new RuntimeException("emptyString throwing runtime exception"));
    }
}
