package com.example.websocket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisMessageSubscriber implements MessageListener {

    private final WebSocketMessageBroadcaster messageBroadcaster;
    private final RedisMessageListenerContainer container;
    private final Set<String> subscribedChannels = new HashSet<>();

    @Value("${redis.channel.pattern}")
    String channelPattern;

    @Value("${room.channel.prefix}")
    String roomChannelPrefix;

    public void subscribeRoomChannel(Integer roomId) {
        String fullChannel = channelPattern + roomChannelPrefix + roomId;
        if (!subscribedChannels.contains(fullChannel)) {
            // TODO: 로깅 작업을 추가합니다.
            System.out.println("subscribe: " + channelPattern + roomChannelPrefix + roomId);

            container.addMessageListener(this, new ChannelTopic(fullChannel));
            subscribedChannels.add(fullChannel);
        }
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String messageBody = new String(message.getBody());
        String channel = new String(message.getChannel()).replaceAll(channelPattern, "");

        // TODO: 로깅 작업을 추가합니다.
        System.out.println("channel: " + channel + " message: " + messageBody);

        messageBroadcaster.broadcast(channel, messageBody);
    }
}
