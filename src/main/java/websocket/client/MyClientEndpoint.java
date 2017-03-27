package websocket.client;

import java.io.IOException;
import javax.websocket.*;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientEndpoint(
)
public class MyClientEndpoint {
    final Logger logger = LoggerFactory.getLogger(MyClientEndpoint.class);
    private static Session server = null;
    private static int counter = 0;

    @OnOpen
    public void onOpen(Session session) {
        server = session;
        System.out.println("Connected to endpoint: " + session.getBasicRemote());
//        try {
//            Message response = new Message();
//            response.setParam1("client0 message");
//            response.setParam2("Param2");
//            logger.info("TO server: " + response.getParam1());
//            session.getBasicRemote().sendObject(response);
//        } catch (IOException | EncodeException ex) {
//            logger.info(MyClientEndpoint.class.getName(), ex);
//        }
    }

    @OnMessage
    public void message(String message, Session session) throws IOException, EncodeException {
        counter++;
        System.out.println(counter);
        System.out.println(message);
        if (counter == 15){
            try {
                server.getBasicRemote().sendObject(buildCommand(2,2,3, 30).toJSONString());
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

    @OnError
    public void processError(Throwable t) {
        t.printStackTrace();
    }

    public static JSONObject buildCommand(int player, int from, int to, int size){
        JSONObject json = new JSONObject();
        json.put("player", player);
        json.put("from", from);
        json.put("to", to);
        json.put("size", size);
        return json;
    }
}
