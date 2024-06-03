package com.example.web.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "group_room_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupRoomDetail {

    @Id
    @Column(name = "room_id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "room_id")
    @JsonBackReference
    @JsonIgnoreProperties
    private Room room;

    @Setter
    @Column(nullable = false)
    private String roomName;

    @Setter
    @Column(nullable = false)
    private Integer roomOwnerId;

    @Setter
    @Column(nullable = false)
    private String enterCode;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public GroupRoomDetail(Room room) {
        this.room = room;
    }
}
