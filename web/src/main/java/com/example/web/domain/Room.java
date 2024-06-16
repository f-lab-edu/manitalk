package com.example.web.domain;

import com.example.web.enums.RoomType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "rooms")
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 1, nullable = false)
    private RoomType type = RoomType.G;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "room", fetch = FetchType.LAZY)
    @Setter
    @JsonManagedReference
    private GroupRoomDetail groupRoomDetail;

    @OneToOne(mappedBy = "room", fetch = FetchType.LAZY)
    @Setter
    @JsonManagedReference
    private ManitoRoomDetail manitoRoomDetail;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    @Setter
    @JsonManagedReference
    private List<UserRoom> userRoom;

    public Room(RoomType type) {
        this.type = type;
    }
}
