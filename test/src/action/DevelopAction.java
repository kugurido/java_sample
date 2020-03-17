package action;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

public class DevelopAction  extends Action{

	@Override
	public String execute(HttpServletRequest request)  {

		LocalDateTime date1 = LocalDateTime.now();
		DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String fdate = df2.format(date1);
		request.setAttribute("time", fdate);


		return "/top.jsp";
	}

}
