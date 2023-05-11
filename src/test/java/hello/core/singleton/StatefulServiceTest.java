package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatefulService statefulService = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);

        // ThreadA : A 사용자 10000원 주문
        int userAPrice = statefulService.order("userA", 10000);

        // ThreadB : B 사용자 20000원 주문
        int userBPrice = statefulService1.order("userB", 20000);

//        int price = statefulService.getPrice();
        System.out.println("price = " + userAPrice);

//        Assertions.assertThat(statefulService.getPrice()).isEqualTo(20000);

    }


    static class TestConfig {

        @Bean
        public StatefulService statefulService () {
            return new StatefulService();
        }
    }
}

