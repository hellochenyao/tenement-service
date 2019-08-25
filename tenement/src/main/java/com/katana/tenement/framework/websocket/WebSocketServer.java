package com.katana.tenement.framework.websocket;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.katana.tenement.dao.app.PrivateMsgRepo;
import com.katana.tenement.dao.app.vo.userinfo.UserInfoVo;
import com.katana.tenement.framework.dto.message.Message;
import com.katana.tenement.framework.util.DateUtils;
import com.katana.tenement.service.app.PrivateMsgService;
import com.katana.tenement.service.app.UserInfoService;
import com.katana.tenement.service.app.bo.privateMsg.PrivateMsgBo;
import com.katana.tenement.service.app.bo.privateMsg.PrivateMsgFilterBo;
import com.katana.tenement.service.app.bo.userinfo.UserInfoBo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.server.standard.SpringConfigurator;

@ServerEndpoint(value = "/im/{userId}")
@Component
@Slf4j
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //旧：concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //private static CopyOnWriteArraySet<ImController> webSocketSet = new CopyOnWriteArraySet<ImController>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //新：使用map对象，便于根据userId来获取对应的WebSocket
    private static ConcurrentHashMap<String, WebSocketServer> websocketList = new ConcurrentHashMap<>();
    //接收sid
    private String userId="";

    private static PrivateMsgService privateMsgService;

    private static UserInfoService userInfoService;

    @Autowired
    private void setPrivateMsgService(PrivateMsgService privateMsgService){
        WebSocketServer.privateMsgService = privateMsgService;
    }

    @Autowired
    private void setUserInfoService(UserInfoService userInfoService){
        WebSocketServer.userInfoService = userInfoService;
    }
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        this.session = session;
        websocketList.put(userId,this);
        log.info("websocketList->"+JSON.toJSONString(websocketList));
        //webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        log.info("有新窗口开始监听:"+userId+",当前在线人数为" + getOnlineCount());
        this.userId=userId;
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(websocketList.get(this.userId)!=null){
            websocketList.remove(this.userId);
            //webSocketSet.remove(this);  //从set中删除
            subOnlineCount();           //在线数减1
            log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口"+userId+"的信息:"+message);
        if(StringUtils.isNotBlank(message)){
            JSONArray list=JSONArray.parseArray(message);
            for (int i = 0; i < list.size(); i++) {
                try {
                    //解析发送的报文
                    JSONObject object = list.getJSONObject(i);
                    String toUserId=object.getString("toUserId");
                    String contentText=object.getString("contentText");
                    object.put("fromUserId",this.userId);
                    //传送给对应用户的websocket
                    if(StringUtils.isNotBlank(toUserId)&&StringUtils.isNotBlank(contentText)){
                        WebSocketServer socketx=websocketList.get(toUserId);
                        String createTime = DateUtils.getLocalDateTimeStr(LocalDateTime.now());
                        int toUser = Integer.parseInt(toUserId);
                        int user = Integer.parseInt(userId);
                        //需要进行转换，userId
                        if(socketx!=null){
                            UserInfoVo userInfo = userInfoService.info(Integer.parseInt(userId));
                            Message messageText = new Message();
                            messageText.setAvatar(userInfo.getAvatar());
                            messageText.setContent(contentText);
                            messageText.setNickName(userInfo.getNickName());
                            messageText.setCreateTime(createTime);
                            messageText.setUserid(user);
                            socketx.sendMessage(JSONObject.toJSONString(messageText));
                            //此处可以放置相关业务代码，例如存储到数据库
                        }
                        PrivateMsgBo privateMsgBo = new PrivateMsgBo();
                        privateMsgBo.setUpdateTime(LocalDateTime.now());
                        privateMsgBo.setCreateTime(LocalDateTime.now());
                        privateMsgBo.setContent(contentText);
                        privateMsgBo.setReceiveUserid(toUser);
                        privateMsgBo.setUserid(user);
                        privateMsgBo.setIsRead(-1);
                        privateMsgService.saveMsg(privateMsgBo);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     * */
    /*public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
        log.info("推送消息到窗口"+userId+"，推送内容:"+message);
        for (ImController item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if(userId==null) {
                    item.sendMessage(message);
                }else if(item.userId.equals(userId)){
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }*/

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
