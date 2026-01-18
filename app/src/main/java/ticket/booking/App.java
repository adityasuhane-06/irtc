package ticket.booking;
import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import java.util.Arrays;
import java.util.stream.Collectors;
public class App {
    public static void main(String[] args){
        List<Integer> l=Arrays.asList(1,2,3,4,5,6,7,8,9);
        List<Integer> evenNumbers=l.stream().filter(n->n%2==0).collect(Collectors.toList());
        System.out.println(evenNumbers);
        List<Integer> squaredNumbers = l.stream().map(e->e*e).collect(Collectors.toList());
    }

    public static Predicate<Integer> isEven(){
        return n->n%2==0;
    }
}
    