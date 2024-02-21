### Java Lambda:
In Java, Lambda expressions are used to express instances of `functional interfaces` (An interface with a single abstract method is called a functional interface, SAM in kotlin).

* Note: that lambda expressions can only be used to implement functional interfaces. So, have to define a functional interface first then implement the lambda block, then call. Or a Framework can define the Interface, consumer needs to define the implementation. 

* lambda body can contain zero, one, or more statements. 
* for single statement curly brackets are not mandatory and `return` is implicit
* return type of the anonymous function is the same as the type of the value returned within the code block, or void if nothing is returned.

```java
// FuncInterface is an functional interface (An interface with single abstract method)
interface FuncInterface
{
	void abstractFun(int x);

	// A non-abstract (or default) function
	default void normalFun() {
	    System.out.println("Hello");
	}
}

class Test
{
	public static void main(String args[])
	{
		// lambda expression to implements abstractFun() Functional Interface
		FuncInterface fobj = (int x) -> System.out.println(2*x);
		fobj.abstractFun(7); // outputs 14

        FuncInterface fob2 = (int x) -> {
            System.out.println(x);
            System.out.println(2*x);
        };
	}
}
```
### @FunctionalInterface Annotation and Built-in FI:
@FunctionalInterface annotation is used to ensure that the functional interface can’t have more than one abstract method. In case more than one abstract methods are present, the compiler flags an ‘Unexpected @FunctionalInterface annotation’ message. However, it is not mandatory to use this annotation.

- Runnable –> This interface only contains the run() method.
- Comparable –> This interface only contains the compareTo() method.
- ActionListener –> This interface only contains the actionPerformed() method.
- Callable –> This interface only contains the call() method.


### Java SE and four main kinds of functional interfaces:
- Consumer FI => accepts only one argument or a gentrified argument has no return value (returns nothing). Bi-Consumer interface takes two arguments.

- Predicate FI => In scientific logic, a function that accepts an argument and, in return, generates a boolean value as an answer is known as a predicate. there are IntPredicate, DoublePredicate, and LongPredicate. Bi-Predicate is an extension of the Predicate functional interface which accepts two arguments, does some processing, and returns the boolean value.

- Function FI => it's a type of functional interface that receives a single argument and returns a value after the required processing. There is also Bi-Function variant

- Supplier FI => this does not take any input/argument but returns some output. there are BooleanSupplier, DoubleSupplier, LongSupplier, and IntSupplier. 


Guide => https://www.geeksforgeeks.org/functional-interfaces-java/