package com.xin.manager;

import com.xin.momo.Adapter.DialogueMessage;

import org.jivesoftware.smack.packet.Message;

/**
 * Created by Administrator on 2015/1/2.
 */
public class DialogueMessageManager {

    private DialogueMessageManager() {

    }

    public static DialogueMessage getFriendDialogueMessage(Message message){


        DialogueMessage dialogueMessage = new DialogueMessage();
        dialogueMessage.setMsg(message.getBody());
        dialogueMessage.setContentType(DialogueMessage.MESSAGE_CONTENT_TYPE_STRING);
        dialogueMessage.setType(DialogueMessage.DIALOGUE_MESSAGE_TYPE_FRIEND);
        return dialogueMessage;
    }

    public static Message getUserMessage(DialogueMessage message){

        Message msg = new Message();
        if(message.getContentType() == DialogueMessage.MESSAGE_CONTENT_TYPE_STRING) {

            msg.setBody(String.valueOf(message.getMsg()));
            msg.setType(Message.Type.chat);
        }

        return msg;
    }

}
