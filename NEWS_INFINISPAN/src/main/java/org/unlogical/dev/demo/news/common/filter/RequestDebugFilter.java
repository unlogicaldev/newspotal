package org.unlogical.dev.demo.news.common.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;

/**
 * 
 * 웹 요청에 대해서 HTTP Request Parameter, HTTP Session, Attribute, Cookie의 로그를 남기는 Debug Filter.
 * first author KBG
 * @author LYH
 * @version 1.0 2009.08.21
 */
public class RequestDebugFilter implements Filter {

	private static final Logger logger = Logger.getLogger(RequestDebugFilter.class.getName());

    /**
     * Filter Config
     */
    protected FilterConfig filterConfig = null;

    public void init(FilterConfig config) throws ServletException {
        this.filterConfig = config;
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    		
            HttpServletRequest servletRequest = (HttpServletRequest) request;            
            String url = getURLQueryString(servletRequest);
            
//            HttpSession session = servletRequest.getSession(false);
//    		if(session == null){
//    			session = servletRequest.getSession();
//    			session.setAttribute("REQ_URI", "");
//    		}
//    		String reqUri = session.getAttribute("REQ_URI").toString();
//    		Long rTime = (new Date()).getTime();
//    		if( reqUri.equals(url)){
//    			Long reqTime = (Long)session.getAttribute("REQ_TIME");
//    			if((rTime-reqTime) < 300){
//        			logger.warning("!!## same url request : "+url+":"+(rTime-reqTime));
//    				return;
//    			}else if(reqUri.indexOf("user/createUser") > -1){
//        			logger.warning("!!## same url request : "+url);
//    				return;
//    			}
//    		}
//    		
//    		session.setAttribute("REQ_URI", url);
//    		session.setAttribute("REQ_TIME", rTime);
            
            
            StringBuilder buffer = new StringBuilder();
            buffer.append("HTTP Request URL is [");
            buffer.append(url);
            buffer.append("]");
            buffer.append("\n\n");
            buffer.append(" [== Request Parameters ==]\n\n").append(new HttpRequestInfo(servletRequest).toParameter()).append("\n");
            buffer.append(" [== Request Attributes ==]\n\n").append(new HttpRequestInfo(servletRequest).toAttribute()).append("\n");
            buffer.append(" [== Request Session    ==]\n\n").append(new HttpRequestInfo(servletRequest).toSession()).append("\n");
            buffer.append(" [== Request Cookies    ==]\n\n").append(new HttpRequestInfo(servletRequest).toCookies()).append("\n");
            logger.warning(buffer.toString());
            
            chain.doFilter(request, response);
    }

    /**
     * {@link HttpServletRequest}의 URI와 Query String을 이용하여 Full URI를 구성한다.
     *
     * @param request HttpServletRequest
     * @return URI
     */
    private static String getURLQueryString(HttpServletRequest request) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(request.getRequestURI());
        if (null != request.getQueryString()) {
            if (!"".equals(request.getQueryString())) {
                // Query String이 null이 아니면 URI에 추가한다.
                buffer.append("?");
                buffer.append(request.getQueryString());
            }
        }
        return buffer.toString();
    }

    /**
     * Filter Config를 설정한다.
     *
     * @param config {@link javax.servlet.FilterConfig}
     */
    public void setFilterConfig(FilterConfig config) {
        filterConfig = config;
    }

    /**
     * HttpRequest의 파라미터 정보를 필터를 이용하여 출력하기 위한 Value Object.
     *
     * @author LYH
     * @version 1.0
     */
    class HttpRequestInfo {

        private HttpServletRequest request = null;

        /**
         * 기본 생성자.
         *
         * @param request {@link HttpServletRequest}
         */
        public HttpRequestInfo(HttpServletRequest request) {
            this.request = request;
        }

        /**
         * Http Request의 Parameter에 대한 문자열을 반환한다.
         *
         * @return Http Request 정보의 문자열
         */
        public String toParameter() {
            StringBuilder stringBuilder = new StringBuilder();
            Map map = request.getParameterMap();
            Set keys = map.keySet();
            for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                String[] values = (String[]) map.get(key);

                // key 값을 출력할 문자열 구성
                stringBuilder.append("   ").append(key);
                stringBuilder.append("  \t: ");

                for (int i = 0; i < values.length; i++) {
                    String value = values[i];

                    if (i == 0) {
                        // 입력 파라미터가 1개인 경우
                        stringBuilder.append(value).append("\n");
                    } else {
                        // 입력 파라미터가 한 개 이상의 배열인 경우
                        stringBuilder.append(value).append("[").append(i).append("]");
                        if ((i - 1) != values.length) {
                            // 맨 마지막이 아닌 경우에는 모두 ,을 추가한다.
                            stringBuilder.append(", ");
                        }
                    }
                }
            }
            if (request.getParameterMap().size() < 1) {
                // HttpServletRequest에 파라미터가 하나도 없음.
                stringBuilder.append("   파라미터가 없습니다.\n");
            }
            return stringBuilder.toString();
        }

        /**
         * Http Request의 Attribute에 대한 문자열을 반환한다.
         *
         * @return Http Request 정보의 문자열
         */
        public String toAttribute() {
            StringBuilder stringBuilder = new StringBuilder();
            Enumeration map = request.getAttributeNames();
            while (map.hasMoreElements()) {
                String key = (String) map.nextElement();
                Object value = request.getAttribute(key);
                // key 값을 출력할 문자열 구성
                stringBuilder.append("   ").append(key);
                stringBuilder.append("  \t: ");
                stringBuilder.append(value).append("\n");
            }
            return stringBuilder.toString();
        }

        /**
         * Http Request의 Session에 대한 문자열을 반환한다.
         *
         * @return Http Request 정보의 문자열
         */
        public String toSession() {
            StringBuilder stringBuilder = new StringBuilder();
            HttpSession session = request.getSession(false);
            if (session != null) {
                stringBuilder.append("   Session Id  : ").append(session.getId()).append("\n");
                stringBuilder.append("   Creation Time  : ").append(parseDate(new Date(session.getCreationTime()), "yyyy-MM-dd a hh:mm:ss.SSS")).append("\n");
                stringBuilder.append("   Last Accessed Time  : ").append(parseDate(new Date(session.getLastAccessedTime()), "yyyy-MM-dd a hh:mm:ss.SSS")).append("\n");
                stringBuilder.append("   Max Inactive Interval  : ").append(session.getMaxInactiveInterval()).append("\n");

                Enumeration map = session.getAttributeNames();
                while (map.hasMoreElements()) {
                    String key = (String) map.nextElement();
                    Object value = session.getAttribute(key);
                    // key 값을 출력할 문자열 구성
                    stringBuilder.append("   ").append(key);
                    stringBuilder.append("  \t: ");
                    stringBuilder.append(value).append("\n");
                }
            } else {
                stringBuilder.append("   세션이 없습니다.\n");
            }
            return stringBuilder.toString();
        }

        /**
         * Http Request의 Cookie에 대한 문자열을 반환한다.
         *
         * @return Http Request 정보의 문자열
         */
        public String toCookies() {
            Cookie[] cookies = request.getCookies();
            StringBuilder stringBuilder = new StringBuilder();
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie cookie = cookies[i];
                    stringBuilder.append("   ").append(cookie.getName());
                    stringBuilder.append("  \t: ");
                    stringBuilder.append(cookie.getValue()).append("\n");
                }
            } else {
                stringBuilder.append("   쿠키가 없습니다.");
            }
            stringBuilder.append("\n");
            return stringBuilder.toString();
        }
    }

    /**
     * 지정한 날자를 패턴에 맞도록 문자열 날짜로 변환한다.
     *
     * @param date    날짜
     * @param pattern 패턴 (예; YYYYMMDD)
     * @return 문자열 날짜
     */
    private static String parseDate(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return formatter.format(calendar.getTime());
    }
}