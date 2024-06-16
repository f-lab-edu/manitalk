package com.example.web.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "manito_room_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManitoRoomDetail {

    @Id
    @Column(name = "room_id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "room_id")
    @JsonBackReference
    @JsonIgnoreProperties
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "group_room_id")
    @JsonBackReference
    @JsonIgnoreProperties
    private GroupRoomDetail groupRoomDetail;

    @Column
    private LocalDateTime expiresAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public ManitoRoomDetail(Room room, GroupRoomDetail groupRoomDetail, LocalDateTime expiresAt) {
        this.room = room;
        this.groupRoomDetail = groupRoomDetail;
        this.expiresAt = expiresAt;
    }
}
