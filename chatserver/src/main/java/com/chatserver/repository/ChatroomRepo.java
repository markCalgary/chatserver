package com.chatserver.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatserver.entity.ChatroomEntity;
import com.chatserver.entity.MessageEntity;
import com.chatserver.entity.UserEntity;

@Repository
public interface ChatroomRepo extends JpaRepository <ChatroomEntity, BigInteger>{

}
