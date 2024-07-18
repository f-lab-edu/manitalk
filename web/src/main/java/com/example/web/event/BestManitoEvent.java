package com.example.web.event;

import com.example.web.enums.EventType;
import com.example.web.vo.BestManitoVo;
import lombok.Getter;

import java.util.List;

@Getter
public class BestManitoEvent extends RoomEvent {
    private final List<BestManitoVo> bestManitos;

    public BestManitoEvent(Integer roomId, List<BestManitoVo> bestManitos) {
        super(EventType.BEST_MANITO, roomId);
        this.bestManitos = bestManitos;
    }
}
