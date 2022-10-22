package com.project.tour.service;

import com.project.tour.domain.Member;
import com.project.tour.domain.Notice;
import com.project.tour.domain.NoticeForm;
import com.project.tour.domain.NoticeRecommend;
import com.project.tour.repository.MemberRepository;
import com.project.tour.repository.NoticeRecommendRepository;
import com.project.tour.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;
    private final NoticeRecommendRepository noticeRecommendRepository;

    //저장 후 객체 리턴하기
    public Notice create(NoticeForm noticeForm){

        Notice notice = new Notice();

        notice.setSubject(noticeForm.getSubject());
        notice.setCategory(noticeForm.getCategory());
        notice.setCreated(LocalDateTime.now());
        notice.setNoticeImage(noticeForm.getNoticeImage());
        notice.setContent(noticeForm.getContent());
        notice.setHitCount(0);
        notice.setPin(noticeForm.isPin());

        noticeRepository.save(notice);

        return notice;

    }

    //페이징 처리
    public Page<Notice> getList(Pageable pageable){

        List<Sort.Order> sorts = new ArrayList<Sort.Order>();
        sorts.add(Sort.Order.desc("pin"));
        sorts.add(Sort.Order.desc("id"));

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ?
                        0 : pageable.getPageNumber() -1, //반환할 페이지
                pageable.getPageSize(), //반환할 리스트 갯수
                Sort.by(sorts)); //정렬 매개변수 적용

        return noticeRepository.findAll(pageable);

    }

    //noticeNum으로 검색하기
    public Notice getNotice(Long id){

        Optional<Notice> notice = noticeRepository.findById(id);

        return notice.get();
    }

    //noticeNum으로 글제목 검색하기
    public String getSubject(Long id){

        Optional<Notice> notice = noticeRepository.findById(id);

        if(!notice.isPresent()){
            return "";
        }else {
            return notice.get().getSubject();
        }

    }

    public void updateNotice(NoticeForm noticeForm,Long id){

        Optional<Notice> notice = noticeRepository.findById(id);

        notice.get().setSubject(noticeForm.getSubject());
        notice.get().setCategory(noticeForm.getCategory());
        notice.get().setCreated(LocalDateTime.now());
        notice.get().setNoticeImage(noticeForm.getNoticeImage());
        notice.get().setContent(noticeForm.getContent());
        notice.get().setPin(noticeForm.isPin());

        noticeRepository.save(notice.get());

    }

    //게시글 삭제
    public void deleteNotice(Long id){

        Optional<Notice> notice = noticeRepository.findById(id);
        noticeRepository.delete(notice.get());

    }

    //조회수 올리기
    public void updateHitCount(int hitCount, Long id){

        Optional<Notice> notice = noticeRepository.findById(id);
        notice.get().setHitCount(hitCount);

        noticeRepository.save(notice.get());

    }

    //고정된 게시글 수
    public int searchPin(){

        return noticeRepository.countPin();

    }

    //추천해요 추가
    public void createRecommend(Member member,Long id){

        Notice notice = noticeRepository.findById(id).get(); //member 찾기

        NoticeRecommend noticeRecommend = new NoticeRecommend();

        noticeRecommend.setMember(member);
        noticeRecommend.setNotice(notice);

        noticeRecommendRepository.save(noticeRecommend);

    }

    //추천해요 취소
    public void deleteRecommend(Member member,Long id){

        Notice notice = noticeRepository.findById(id).get(); //member 찾기
        Optional<NoticeRecommend> noticeRecommend = noticeRecommendRepository.findByMemberAndNotice(member,notice);

        noticeRecommendRepository.delete(noticeRecommend.get());

    }

    //추천해요 상태 확인
    public int searchRecommend(Member member, Long id){

        Notice notice = noticeRepository.findById(id).get(); //member 찾기
        Optional<NoticeRecommend> noticeRecommend = noticeRecommendRepository.findByMemberAndNotice(member,notice);

        if(noticeRecommend.isPresent()){
            return 1;
        }else{
            return 0;
        }
    }

}
