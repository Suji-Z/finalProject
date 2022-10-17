package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.EstimateInquiry;
import com.project.tour.domain.Member;
import com.project.tour.domain.QnA;
import com.project.tour.domain.QnAForm;
import com.project.tour.repository.QnARepository;
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
public class QnAService {

    private final QnARepository qnARepository;

    public Page<QnA> getList(Pageable pageable){
        List<Sort.Order> sort = new ArrayList<Sort.Order>();
        sort.add(Sort.Order.desc("id")); //EstimateNum

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ?
                        0 : pageable.getPageNumber() -1, //반환할 페이지
                pageable.getPageSize(), //반환할 리스트 갯수
                Sort.by(sort)); //정렬 매개변수 적용

        return qnARepository.findAll(pageable);

    }
    //질문 작성
    public QnA create(QnAForm qnAForm, Member member){

        QnA qna = new QnA();

        qna.setSubject(qnAForm.getSubject());
        qna.setContent(qnAForm.getContent());
        qna.setQnacategory(qnAForm.getQnacategory());
        qna.setPassword(qnAForm.getPassword());
        qna.setCreated(LocalDateTime.now());
        qna.setName(member.getName());
        qna.setMember(member);

        return qnARepository.save(qna);

        //created, id
    }

    //질문 수정

    public void modify(QnA qnA, QnAForm qnAForm){

        qnA.setSubject(qnAForm.getSubject());
        qnA.setQnacategory(qnAForm.getQnacategory());
        qnA.setContent(qnAForm.getContent());
        qnA.setPassword(qnAForm.getPassword());

        qnARepository.save(qnA);

    }

    //질문 삭제

    public void delete(QnA qna){

        qnARepository.delete(qna);
    }


    //글 불러오기
    public QnA getquestion(Long id) {
        Optional<QnA> qna = qnARepository.findById(id);

        if(qna.isPresent())
            return qna.get();
        else
            throw new DataNotFoundException("확인할수 없는 게시물 입니다.");
    }

}
