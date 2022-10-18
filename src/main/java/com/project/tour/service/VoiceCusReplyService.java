package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.Member;
import com.project.tour.domain.VoiceCus;
import com.project.tour.domain.VoiceCusReply;
import com.project.tour.repository.VoiceCusReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VoiceCusReplyService {
    private final VoiceCusReplyRepository voiceCusReplyRepository;

    public VoiceCusReply create(VoiceCus voiceCus, String content){

        VoiceCusReply reply = new VoiceCusReply();

        reply.setVoiceCus(voiceCus);
        reply.setCreatedDate(LocalDateTime.now());
        reply.setContent(content);

        voiceCusReplyRepository.save(reply);

        return reply;

    }

    public VoiceCusReply getReply(Integer id){

        Optional<VoiceCusReply> reply = voiceCusReplyRepository.findById(id);

        if(reply.isPresent()) {
            return reply.get();
        }else {
            throw new DataNotFoundException("답글이 없습니다.");
        }
    }

    public void modify(VoiceCusReply voiceCusReply,String content){

        voiceCusReply.setContent(content);

        voiceCusReplyRepository.save(voiceCusReply);
    }

    public void delete(VoiceCusReply voiceCusReply){

        voiceCusReplyRepository.delete(voiceCusReply);
    }

}
