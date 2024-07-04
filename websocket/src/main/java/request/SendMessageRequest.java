package request;

import com.example.websocket.enums.MessageType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SendMessageRequest {

    private Integer roomId;

    private Integer userId;

    private MessageType messageType;

    private String content;
}
