package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.Member;
import com.project.tour.domain.VoiceCus;
import com.project.tour.oauth.dto.BaseAuthUserRepository;
import com.project.tour.oauth.model.BaseAuthUser;
import com.project.tour.repository.VoiceCusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VoiceCusService {

    private final VoiceCusRepository voiceCusRepository;

    @Autowired
    private final BaseAuthUserRepository baseAuthUserRepository;

    public List<VoiceCus> getList(){

        return voiceCusRepository.findAll();
    }

    public Page<VoiceCus> getList(Pageable pageable){

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() -1,
                pageable.getPageSize(),Sort.by(sorts));


        return voiceCusRepository.findAll(pageable);
    }

    public VoiceCus getVoiceCus(Integer id) {
        Optional<VoiceCus> op = voiceCusRepository.findById(id);

        if(op.isPresent())
            return op.get();
        else
            throw new DataNotFoundException("해당 게시글을 찾을 수 없습니다.");
    }

    public String email(Principal principal){


        BaseAuthUser socialId=baseAuthUserRepository.findById(principal.getName());

        System.out.println(socialId.getEmail());

        return socialId.getEmail();

    }

    public void create(String subject, String content, String types, Member author){


        VoiceCus voiceCus = new VoiceCus();

        voiceCus.setSubject(subject);
        voiceCus.setContent(content);
        voiceCus.setTypes(types);
        voiceCus.setCreatedDate(LocalDateTime.now());
        voiceCus.setAuthor(author);

        voiceCusRepository.save(voiceCus);
    }

    public void modify(VoiceCus voiceCus,String subject, String content,String types){

        voiceCus.setSubject(subject);
        voiceCus.setContent(content);
        voiceCus.setTypes(types);

        voiceCusRepository.save(voiceCus);
    }

    public void delete(VoiceCus voiceCus){

        voiceCusRepository.delete(voiceCus);
    }


    public Page<VoiceCus> search(String types,Pageable pageable){

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() -1,
                pageable.getPageSize(),Sort.by(sorts));

        if(types.equals("total")){
            return voiceCusRepository.findAll(pageable);
        }else {
            return voiceCusRepository.findByTypes(types,pageable);
        }

    }



}
