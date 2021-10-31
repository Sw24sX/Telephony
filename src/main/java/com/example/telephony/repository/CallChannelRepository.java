package com.example.telephony.repository;

import com.example.telephony.domain.CallChannel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallChannelRepository extends JpaRepository<CallChannel, Long> {
}
