package com.example.web.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "group_room_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted = false")
@EqualsAndHashCode
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

    @OneToMany(mappedBy = "groupRoomDetail", fetch = FetchType.LAZY)
    @BatchSize(size = 25)
    @Setter
    @JsonManagedReference
    private List<ManitoRoomDetail> manitoRoomDetails;

    @Setter
    @Column(nullable = false)
    private String roomName;

    @Setter
    @Column(nullable = false)
    private Integer roomOwnerId;

    @Setter
    @Column(nullable = false)
    private String enterCode;

    @Setter
    private boolean deleted = Boolean.FALSE;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public GroupRoomDetail(Room room) {
        this.room = room;
    }
}
