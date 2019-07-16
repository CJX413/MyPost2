package com.email.post.Tools;

import com.email.post.Data.UserData;
import com.email.post.Model.ReceiveModel;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class ReceiveMailTools {
    /**
     * 接收邮件
     *
     * @return 无
     * @param邮箱的用户名和密码
     */
    public void receiveMail(String userName, String passWord) {
        Store store = null;
        Folder folder = null;
        int messageCount = 0;
        try {
            Properties props = new Properties();
            props.setProperty("mail.popStore.protocol", "pop3");       // 使用pop3协议
            props.setProperty("mail.pop3.port", UserData.PORT);           // 端口
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.pop3.ssl.enable", true);
            props.put("mail.pop3.ssl.socketFactory", sf);
            //props.setProperty("mail.debug", "true");
            props.setProperty("mail.pop3.host", UserData.POP3);
            Session session = Session.getInstance(props);
            store = session.getStore("pop3");
            store.connect(UserData.POP3, Integer.parseInt(UserData.PORT), userName, passWord);
            //获得邮箱内的邮件夹Folder对象，以"只读"打开
            folder = store.getFolder("INBOX");//打开收件箱
            folder.open(Folder.READ_ONLY);//设置只读
            // 获得收件箱的邮件列表
            Message[] messages = folder.getMessages();
            Date RecentTime = UserData.dao.SelectRecentTime();
            messageCount = folder.getMessageCount();// 获取所有邮件个数
            //获取新邮件处理
            System.out.println("============>>邮件总数："+messageCount);
            ReceiveModel receiveModel = new ReceiveModel();
            if (RecentTime == null) {
                for (Message message : messages) {
                    receiveModel.setReceiver(UserData.USER_NAME);
                    receiveModel.setSender(getFrom(message));
                    receiveModel.setTopical(getSubject(message));
                    receiveModel.setSendtime(message.getSentDate());
                    StringBuffer content = new StringBuffer(200);
                    getMailContent((Part)message,content);
                    receiveModel.setContent(content.toString());
                    UserData.dao.receivemail(receiveModel);
                }
            } else {
                for (Message message : messages) {
                    int compareTo = RecentTime.compareTo(message.getSentDate());
                    if (compareTo == -1) {
                        receiveModel.setReceiver(UserData.USER_NAME);
                        receiveModel.setSender(getFrom(message));
                        receiveModel.setTopical(getSubject(message));
                        receiveModel.setSendtime(message.getSentDate());
                        StringBuffer content = new StringBuffer(250);
                        getMailContent((Part)message,content);
                        receiveModel.setContent(content.toString());
                        UserData.dao.receivemail(receiveModel);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (folder != null && folder.isOpen()) {
                try {
                    folder.close(true);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
            if (store.isConnected()) {
                try {
                    store.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获得发件人的地址
     *
     * @param message：Message
     * @return 发件人的地址
     */
    private String getFrom(Message message) throws Exception {
        InternetAddress[] address = (InternetAddress[]) ((MimeMessage) message).getFrom();
        String from = address[0].getAddress();
        if (from == null) {
            from = "";
        }
        return from;
    }

    /**
     * 获得邮件主题
     *
     * @param message：Message
     * @return 邮件主题
     */
    private String getSubject(Message message) throws Exception {
        String subject = "";
        if (((MimeMessage) message).getSubject() != null) {
            subject = MimeUtility.decodeText(((MimeMessage) message).getSubject());// 将邮件主题解码
        }
        return subject;
    }

    /**
     * 获取邮件内容
     *
     * @param_part：Part
     */

    private void getMailContent(Part part, StringBuffer content) throws MessagingException, IOException {
        //如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断
        boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
        if (part.isMimeType("text/*") && !isContainTextAttach) {
            content.append(part.getContent().toString());
        } else if (part.isMimeType("message/rfc822")) {
            getMailContent((Part) part.getContent(), content);
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 1; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                getMailContent(bodyPart, content);
            }
        }
    }
}
