package com.project.tour.service;

import com.project.tour.domain.*;
import com.project.tour.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MypageService {

    private final ReviewRepository reviewRepository;

    private final EstimateRepository estimateRepository;

    private final QnARepository qnARepository;

    private final MemberRepository memberRepository;

    private final CouponRepository couponRepository;

    private final BookingRepository bookingRepository;

    private final VoiceCusRepository voiceCusRepository;

    private final PayRepository payRepository;

    private final WishListRepository wishListRepository;

    private final ShortReviewRepository shortReviewRepository;

    private final NoticeReplyRepository noticeReplyRepository;

    //여행후기
    public List<Review> getMypageReview(Long id){

        List<Review> op = reviewRepository.findByAuthor_Id(id);

        return op;


    }

    //리뷰
    public List<ShortReview> getMypageShortR(Long id){

        List<ShortReview> op = shortReviewRepository.findByUserName_Id(id);

        return op;

    }


    //견적문의
    public Page<EstimateInquiry> getMypageEstimate(String email, Pageable pageable){

        List<Sort.Order> sort = new ArrayList<Sort.Order>();
        sort.add(Sort.Order.desc("id")); //MemberId

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ?
                        0 : pageable.getPageNumber() -1, //반환할 페이지
                pageable.getPageSize(), //반환할 리스트 갯수
                Sort.by(sort)); //정렬 매개변수 적용


        return  estimateRepository.findByEmail(email,pageable);
    }

    //QnA
    public Page<QnA> getMypageQnA(Long id,Pageable pageable){

        List<Sort.Order> sort = new ArrayList<Sort.Order>();
        sort.add(Sort.Order.desc("id")); //MemberId

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ?
                        0 : pageable.getPageNumber() -1, //반환할 페이지
                pageable.getPageSize(), //반환할 리스트 갯수
                Sort.by(sort)); //정렬 매개변수 적용

        return qnARepository.findByMember_Id(id,pageable);

    }

    //비밀번호 변경
    public void updatePwd(Member member, String password){

        member.setPassword(password);

        memberRepository.save(member);

    }

    //쿠폰
    public List<Coupon> getMypageCoupon(String couponNum){

        List<String> coupons = Arrays.asList(couponNum.split(","));
        List<Coupon> couponlists = couponRepository.findByIdIn(coupons);

        return couponlists;

    }

    //회원정보 수정
    public void updateProfile(Member member, String name, String birth, String keyword, String phoneNum){
        member.setName(name);
        member.setBirth(birth);
        member.setKeyword(keyword);
        member.setPhone(phoneNum);

        memberRepository.save(member);
    }


    //예약내역
    public Page<UserBooking> getMypageBooking(Long id,Pageable pageable){




        List<Sort.Order> sort = new ArrayList<Sort.Order>();
        sort.add(Sort.Order.desc("id")); //MemberId

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ?
                        0 : pageable.getPageNumber() -1, //반환할 페이지
                pageable.getPageSize(), //반환할 리스트 갯수
                Sort.by(sort)); //정렬 매개변수 적용

        return bookingRepository.findByMember_Id(id,pageable);

    }

    //예약취소내역
    public List<UserBooking> getMypageCancelBooking(Long id,List<Integer> status){

        List<UserBooking> op = bookingRepository.findByMember_IdAndBookingStatusIn(id,status);

        return op;

    }


    //고객의소리
    public Page<VoiceCus> getMypageVcus(Long id, Pageable pageable){

        List<Sort.Order> sort = new ArrayList<Sort.Order>();
        sort.add(Sort.Order.desc("id")); //MemberId

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ?
                        0 : pageable.getPageNumber() -1, //반환할 페이지
                pageable.getPageSize(), //반환할 리스트 갯수
                Sort.by(sort)); //정렬 매개변수 적용

        return voiceCusRepository.findByAuthor_Id(id,pageable);

    }

    //결제완료한 멤버의 결제리스트(사용포인트출력)
    public List<Pay> getMypagePay(Long id){

       List<Pay> result = payRepository.findByMember_Id(id);

        return result;
    }

    //위시리스트
    public List<WishList> getWishList(Long id){

        List<WishList> result = wishListRepository.findByMember_Id(id);

        return result;
    }

    public List<NoticeReply> getSavedPoint(Long id){

        List<NoticeReply> result = noticeReplyRepository.findByMember_Id(id);

        return result;
    }

    //회원탈퇴
    public void unregister(Member member){
        memberRepository.delete(member);
    }



}
