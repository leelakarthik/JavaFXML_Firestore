import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;


class Message_Mail {
    private static String message = "";
    private static void MessageAPI(String message, String mobile){

        String authkey = "268675AEfz6c9y55c9447f5";
        String senderId = "JavaFX";
        String route="4";
        String mainUrl="http://api.msg91.com/api/sendhttp.php?";

        URLConnection myURLConnection;
        URL myURL;
        BufferedReader reader;

        String encoded_message=URLEncoder.encode(message);

        String sbPostData = mainUrl + "authkey=" + authkey +
                "&mobiles=" + mobile +
                "&message=" + encoded_message +
                "&route=" + route +
                "&sender=" + senderId;
        mainUrl = sbPostData;
        try
        {
            myURL = new URL(mainUrl);
            myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
            String response;
            while ((response = reader.readLine()) != null)
                System.out.println(response);
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    static void VerifyMessage(String number,String Uname,String Code){
        message = "Dear,"+Uname+" your Verification Code is:"+Code;
        MessageAPI(message,number);
    }

    static void VerifyEmail(String email,String Uname,String Code){
        message = "Dear,"+Uname+" your Verification Code is:"+Code;
        Mail(email,"VerificationCode",message);
    }

    static void LoginMessage(String name, String number, int f){

        Timestamp ts = new Timestamp(new Date().getTime());
        if(f==0) {
            message = "Dear," + name.toUpperCase() + " your account has been logged in. At " + ts;
            MessageAPI(message,number);
        }
        else{
            message = "Dear," + name.toUpperCase() + " someone tired to access your account. At " + ts;
            MessageAPI(message,number);
        }
    }
    static void SignUpMessage(String name,String UID, String number){
        Timestamp ts = new Timestamp(new Date().getTime());
        message = "Dear," + name.toUpperCase() + " your account has been created in JavaFX, with Username "+UID+". At " + ts +". Check your email for Details";
        MessageAPI(message,number);
    }
    static void Mail(String to, String sub, String msg){
        //Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("mail.leela.projects@gmail.com","LeelaProjects@21");
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {throw new RuntimeException(e);}
    }
}

