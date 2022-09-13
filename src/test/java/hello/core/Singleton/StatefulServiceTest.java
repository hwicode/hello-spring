package hello.core.Singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

class StatefulServiceTest {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    void statefulService1Singleton() {
        StatefulService1 statefulService1 = applicationContext.getBean(StatefulService1.class);
        StatefulService1 statefulService2 = applicationContext.getBean(StatefulService1.class);

        // ThreadA: A사용자 10000원 주문
        statefulService1.order("userA", 10000);
        // ThreadB: B사용자 20000원 주문
        statefulService2.order("userB", 20000);

        //ThreadA: 사용자A 주문 금액 조회
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);

        assertThat(statefulService1.getPrice()).isEqualTo(20000);

    }

    @Test // 공유 필드는 진짜 조심해야 한다! 스프링 빈은 항상 무상태로 설계하자!!
    void statefulService2Singleton() {
        StatefulService2 statefulService1 = applicationContext.getBean(StatefulService2.class);
        StatefulService2 statefulService2 = applicationContext.getBean(StatefulService2.class);

        // ThreadA: A사용자 10000원 주문
        int userAPrice = statefulService1.order("userA", 10000);
        // ThreadB: B사용자 20000원 주문
        int userBPrice = statefulService2.order("userB", 20000);

        //ThreadA: 사용자A 주문 금액 조회
        System.out.println("price = " + userAPrice);

        assertThat(userAPrice).isEqualTo(10000);
    }

    static class TestConfig {

        @Bean
        public StatefulService1 statefulService1() {
            return new StatefulService1();
        }

        @Bean
        public StatefulService2 statefulService2() {
            return new StatefulService2();
        }

    }

}