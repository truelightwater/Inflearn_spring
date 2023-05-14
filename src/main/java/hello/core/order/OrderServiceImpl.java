package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
// @RequiredArgsConstructor // lombok : final 이 붙은 필드를 모아서 생성자를 자동으로 사용하게 한다.
public class OrderServiceImpl implements OrderService {

    // (필수) final : 무조건 값이 있어야 한다 !
    // (불변) 값이 변경되면 안된다.
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;


    /*@Autowired
    private DiscountPolicy rateDiscountPolicy; // 조회 대상 빈이 2개 이상일때, 필드 명 매칭으로 지정할 수 있다.*/

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
