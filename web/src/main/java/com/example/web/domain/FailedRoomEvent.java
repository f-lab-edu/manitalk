package com.example.web.domain;

import com.example.web.vo.RoomEventVo;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "failed_events")
@Getter
public class FailedRoomEvent {

    @Id
    private String id;

    private final Integer roomId;

    private final RoomEventVo roomEventVo;

    public FailedRoomEvent(Integer roomId, RoomEventVo roomEventVo) {
        this.roomId = roomId;
        this.roomEventVo = roomEventVo;
    }

    @CreatedDate
    private LocalDateTime timestamp;
}
