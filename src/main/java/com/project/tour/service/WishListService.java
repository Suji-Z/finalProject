package com.project.tour.service;

import com.project.tour.domain.Member;
import com.project.tour.domain.Package;
import com.project.tour.domain.WishList;
import com.project.tour.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WishListService {

    private final WishListRepository wishListRepository;


    public void wishList(Package packages, Member member){

        WishList wishList = new WishList();

        wishList.setPackages(packages);
        wishList.setMember(member);

        wishListRepository.save(wishList);


    }

    public void deleteWish(Long id1, Long id2){
        Optional<WishList> wishList = wishListRepository.findByMember_IdAndPackages_Id(id1, id2);

        wishListRepository.delete(wishList.get());


    }

    public int getWishList(Long id1,Long id2){

        Optional<WishList> wishList = wishListRepository.findByMember_IdAndPackages_Id(id1, id2);

        if(wishList.isPresent())
            return 1;
        else
            return 0;


    }



}
