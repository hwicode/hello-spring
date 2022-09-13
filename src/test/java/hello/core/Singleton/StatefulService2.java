package hello.core.Singleton;

public class StatefulService2 {

    // 싱글톤 방식은 무상태로 설계해야 한다 -> 필드에 변경가능한 공유값 설정 X
    // 필드 대신에 지역변수, 파라미터, ThreadLocal 등을 사용해야 한다
    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        return price;
    }
}
