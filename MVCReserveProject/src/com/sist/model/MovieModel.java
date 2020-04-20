package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;
import com.sist.dao.MovieDAO;
import com.sist.dao.MovieVO;
import com.sist.dao.ReserveTheaterVO;

import java.text.SimpleDateFormat;
import java.util.*;
@Controller
public class MovieModel {
	
	//영화예매를 클릭하면 => 시작하자마자 일단 전체를 다 뿌림 모든 목록을 다 보여줌=> but 다른 항목들은 비활성화!=> 디폴트 페이지 
	@RequestMapping("movie/reserve.do")
	public String movie_reserve(HttpServletRequest request, HttpServletResponse response)
	{
		
		
		return "reserve.jsp"; //같은 폴더안에 있는 다른 파일로 가는 경우 
	}
	
	@RequestMapping("movie/movie.do")
	public String movie_movie(HttpServletRequest request, HttpServletResponse response)
	{
		//db가져오기
		//static이기 때문에 따로 생성x
		List<MovieVO> list=MovieDAO.movieListData();
		request.setAttribute("mlist", list);
		
		return "movie.jsp";
	}
	
	
	@RequestMapping("movie/theater.do")
	public String movie_theater(HttpServletRequest request, HttpServletResponse response)
	{
		String tno=request.getParameter("tno");
		
		//dao연결하기
		//결과값 theater.jsp에게 보내면 reserve.jsp에서 읽어감...?
		//ajax로 값을 가져오기 때문에 include와 같은 효과이다. 따라서 reserve에서 movie.jsp의 정보를 theater.jsp로 정보를 보낼수있다. 굳이 movie.jsp에서 theater.jsp로 직접 보낼필가 없다.
		//ajax는 소스 전체가 부모와 자식의 소스가 하나가 된다. 따라서 값을 어디서든 id나 class를 읽어서 어디서든 값을 보내고 받기가 쉬움!
		
		//tno가져오기
		StringTokenizer st=new StringTokenizer(tno, ",");
		//몇개인지 모르니까
		List<ReserveTheaterVO> list= new ArrayList<ReserveTheaterVO>();
		
		while(st.hasMoreTokens()) //개수만큼 돌아
		{
			ReserveTheaterVO vo=MovieDAO.movieTheaterData(Integer.parseInt(st.nextToken()));
			list.add(vo);	
		}
		
		request.setAttribute("tList", list);
		
		return "theater.jsp";
		
	}
	
	
	@RequestMapping("movie/date.do")
	public String movie_date(HttpServletRequest request, HttpServletResponse response)
	{
		String strYear=request.getParameter("year");
		String strMonth=request.getParameter("month");
		//디폴트 잡기
		String today=new SimpleDateFormat("yyyy-M-d").format(new Date());
		
		StringTokenizer st=new StringTokenizer(today,"-");
		
		//오늘의 날짜 정보 받은 것들을 모두 잘라놓고 아래서 디폴트로 사용!
		String sy=st.nextToken();
		String sm=st.nextToken();
		String sd=st.nextToken();
		
		//디폴트 정보
		if(strYear==null)
			strYear=sy;
		if(strMonth==null)
			strMonth=sm;
		
		int year=Integer.parseInt(strYear);
		int month=Integer.parseInt(strMonth);
		int day=Integer.parseInt(sd);
		
		String[] strWeek={"일","월","화","수","목","금","토"};
		
		//전년도까지 합
		int total=(year-1)*365
				+(year-1)/4
				-(year-1)/100
				+(year-1)/400;
		
		
		//전달까지 합
		int[] lastDay={31,28,31,30,31,30,31,31,30,31,30,31};
		
		if((year%4==0 && year%100!=0) || (year%400==0))
			lastDay[1]=29;
		else 
			lastDay[1]=28;
		
		for(int i=0; i<month-1;i++)
		{
			total+=lastDay[i]; //전달까지 합!
			
		}
		
		total++; //4월 1일자부터 시작! => 그 날짜의 요일을 알아야함!
		
		int week=total%7; //7로 나눔!!
		
		
		//날짜 정보 date.jsp로 보냄..
		request.setAttribute("lastday", lastDay[month-1]);
		request.setAttribute("week", week);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("strWeek", strWeek);
		
		return "date.jsp";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}