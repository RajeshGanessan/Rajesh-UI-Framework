package Utils.EmailUtil;

import java.net.MalformedURLException;
import java.net.URL;

import Base.BaseTest;
import Constants.AppConstants;
import Utils.PageUtils.HelperComponents;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;

public class MailTriggerAPI extends BaseTest {

	public  void sendEmail() throws EmailException, MalformedURLException {


		String filePath = HelperComponents.lastFileModified(System.getProperty("user.dir") + "/Build-Reports");

		//HTML Email template
		String htmlContent = "<!DOCTYPE html><html lang=\"en\">"
				+ " <head> <meta charset=\"UTF-8\" /> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" /> <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\" /> <title>MyStartupEquity</title> "
				+ "<style> p { margin: 0px; } </style> </head> <body> <p> "
				+ "<img style=\"display: block; margin-left: auto; margin-right: auto;\""
				+ " src=\"https://s3.ap-south-1.amazonaws.com/static.mystartupequity.com/assets/esop-logo.png\" "
				+ "alt=\"MyStartupEquity\" width=\"150\" height=\"50\" /> </p> <p>&nbsp;</p> "
				+ "<div style=\"margin: 10px;\"> <strong><p>Hello Team, </p><strong>"
				+ " <p>&nbsp;</p> <p><span style=\"font-weight: 400;\">Greetings from MyStartupEquity!</span></p> <p>&nbsp;</p> <p>"
				+ " <span style=\"font-weight: 400;\" >The My Startup Equity Automation Suite has been Executed Successfully</span > </p> <p>&nbsp;</p> <p> "
				+ "<span style=\"font-weight: 400;\" > Hereby attaching the Execution Report from the Automation Suite. </span> </p> <p>&nbsp;</p> <p> <span style=\"font-weight: 400;\" >Kindly find the attached Report, revert back in case of queries</span > "
				+ "</p> <p>&nbsp;</p> <p><span style=\"font-weight: 400;\">Regards,</span></p> <p><span style=\"font-weight: 400;\">Team MSE</span></p> </div> </body></html>'\n";

		System.out.println("<-- Sending Email process Started -->");
		URL url = new URL("https://s3.ap-south-1.amazonaws.com");
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(filePath);
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("MyStartUp Equity-Automation Suite Report");
		attachment.setName("MyStartUp Equity-Automation Suite-Report.html");

		// Create the email message
		ImageHtmlEmail email = new ImageHtmlEmail();
		email.setDataSourceResolver(new DataSourceUrlResolver(url));
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setSSLOnConnect(true);
		email.setAuthenticator(new DefaultAuthenticator("rajeshg.gmx@gmail.com", AppConstants.APP_PASS));
		email.addTo("grajeshr97@gmail.com", "Rajesh G");
		email.addCc("deepanjali.kumari@mystartupequity.com");
		email.setFrom("rajesh@LetsVenture.com", "MyStartupEquity-Automation");
		email.setSubject("MyStartUp Equity-Automation Suite Report");
		email.setHtmlMsg(htmlContent);

		//alternate message
		email.setTextMsg("Your email client does not support HTML messages");

		// add the attachment
		email.attach(attachment);

		// send the email
		email.send();
		System.out.println("<-- The report has been Emailed Successfully -->");
	}

}
