package com.xin.manager;

import com.xin.momo.Adapter.DialogueMessage;
import com.xin.momo.utils.L;
import com.xin.service.CoreServiceCallBack;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/1/2.
 */
public class ChatConnectionManager {

    public final static int CHAT_MESSAGE_SEND_TO_CHAT_ROOM = 0x500001;
    public final static int CHAT_MESSAGE_SEND_TO_CHAT_LIST = 0x500002;
    private XMPPTCPConnection xmpptcpConnection;
    private ChatManager mChatManager;
    private HashMap<String, Chat> chatHashMap;
    private CoreServiceCallBack mCoreServiceCallBack;
    private Chat activeChat= null;
    private String activeFriend;


    public ChatConnectionManager() {

        xmpptcpConnection = ConnectionControl.getConnectionSingleton().getXmpptcpConnection();
        mChatManager = ChatManager.getInstanceFor(xmpptcpConnection);
        mChatManager.addChatListener(new Listener());
        chatHashMap = new HashMap<>();
    }



    public Chat getChat(String JID){

        activeChat = chatHashMap.get(JID);
        if(activeChat == null){

            activeChat = mChatManager.createChat(JID, null);
            chatHashMap.put(JID, activeChat);
        }
        activeFriend = JID;
        return activeChat;
    }

    public void setCoreServiceCallBack(CoreServiceCallBack coreServiceCallBack){

        mCoreServiceCallBack = coreServiceCallBack;

    }


    class Listener implements ChatManagerListener {

        @Override
        public void chatCreated(Chat chat, boolean b) {

            chat.addMessageListener(new MessageListener() {
                @Override
                public void processMessage(Chat chat, Message message) {


                    DialogueMessage mes = DialogueMessageManager.getFriendDialogueMessage(message);
                    String friendName = message.getFrom().split("/")[0];
                    if(friendName.equals(activeFriend)){

                       mCoreServiceCallBack.ChatManagerSendMessageToService(mes, CHAT_MESSAGE_SEND_TO_CHAT_ROOM);
                    }
                    else{

                        L.i("message " + message.getBody() + "  " + message.getFrom());
                    }
                }
            });
        }
    }
}
