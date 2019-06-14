import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 执笔
 * @date 2019/4/16 15:59
 */
public class ArrTest {

    public static void main(String[] args) {
        List list = new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        List ss = new ArrayList();
        Object[] objects = list.toArray();
        Object[] s = new Object[50];
        List<Object> list1 = Arrays.asList(s);
        System.arraycopy(objects,3,s,0,50);
        System.out.println(list);
        System.out.println(list1);
    }
}
