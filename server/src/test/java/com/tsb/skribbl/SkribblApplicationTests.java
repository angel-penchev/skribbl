package com.tsb.skribbl;

import com.tsb.skribbl.model.game.DrawingLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.util.concurrent.TimeUnit.SECONDS;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SkribblApplicationTests {
//	@LocalServerPort
//	private Integer port;
//
//	private WebSocketStompClient webSocketStompClient;
//
//	@BeforeEach
//	public void setup() {
//		ArrayList<?> arrayList = new ArrayList<>();
//		this.webSocketStompClient = new WebSocketStompClient(new SockJsClient(
//				List.of(new WebSocketTransport(new StandardWebSocketClient()))));
//	}
//
//	@Test
//	public void verifyGreetingIsReceived() throws Exception {
//
//		BlockingQueue<String> blockingQueue = new ArrayBlockingQueue(1);
//
//		webSocketStompClient.setMessageConverter(new StringMessageConverter());
//
//		StompSession session = webSocketStompClient
//				.connect(getWsPath(), new StompSessionHandlerAdapter() {})
//				.get(1, SECONDS);
//
//		session.subscribe("/topic/greetings", new StompFrameHandler() {
//
//			@Override
//			public Type getPayloadType(StompHeaders headers) {
//				return String.class;
//			}
//
//			@Override
//			public void handleFrame(StompHeaders headers, Object payload) {
//				System.out.println("Received message: " + payload);
//				blockingQueue.add((String) payload);
//			}
//		});
//
//		session.send("/room/{roomId}/board", new DrawingLine(
//				0,
//				0,
//				1,
//				2,
//				"black",
//				2
//		));
//
//		assertEquals(new DrawingLine(
//				0,
//				0,
//				1,
//				2,
//				"black",
//				2
//		), blockingQueue.poll(1, SECONDS));
//	}
//
//	private void assertEquals(DrawingLine s, String poll) {
//	}
//
//	private String getWsPath() {
//		return String.format("ws://localhost:%d/ws-endpoint", port);
//	}
}
