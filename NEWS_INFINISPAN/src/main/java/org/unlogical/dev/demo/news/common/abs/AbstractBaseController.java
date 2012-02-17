/*****************************************************************************
 * 순천시 ATMS 구축
 *****************************************************************************
 * @(com.its.suncheon.common.abs)AbstractBaseController.java
 * @author  : 진재우
 * @version : ver 1.0
 * @Created : 2011/12/28
 *
 * 개요 : 공통 콘트롤러 추상화 클래스
 * 
 * UPDATE : <날짜>.<개발자명>:<수정내용>
 *****************************************************************************/
package org.unlogical.dev.demo.news.common.abs;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class AbstractBaseController<T> {

	@SuppressWarnings("unchecked")
	final protected Logger log = LoggerFactory.getLogger(((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
	
	/**
	 *  setting session attribute
	 *	@param key
	 *	@param value
	 *	@param request
	 */
	protected void setSessionAttr(String key, Object value, HttpServletRequest request){
		request.getSession().setAttribute(key, value);
		request.getSession().setMaxInactiveInterval(-1);
	}
	
	/**
	 *  getting session attribute
	 *	@param key
	 *	@param request
	 *	@return
	 */
	protected Object getSessionAttr(String key, HttpServletRequest request){
		return request.getSession().getAttribute(key);
	}
		
	/**
	 *  remove session attribute
	 *	@param key
	 *	@param request
	 */
	protected void removeSession(String key, HttpServletRequest request){
		request.getSession().removeAttribute(key);
	}
	
	/**
	 *  session invalidate
	 *	@param key
	 *	@param request
	 */
	protected void resetSession(String key, HttpServletRequest request){
		request.getSession().invalidate();
	}

	/**
	 *  validation result check
	 *	@param result
	 *	@param model
	 *	@return
	 */
	protected boolean validResult(BindingResult result, Model model) {
		if(result.hasErrors()){
		 	StringBuilder sb = new StringBuilder();
            List<ObjectError> errorList = result.getAllErrors(); 
            for(int i=0; i< errorList.size();i++){
                ObjectError error = errorList.get(i);
                sb.append(error.getDefaultMessage()).append("\\r\\n");
            }
			model.addAttribute("error", "fail");
			model.addAttribute("message", sb.toString());
			return false;
        }else return true;
	}
	
	/**
	 * 모든 에러케이스에 전부 적용시키는 에러헨들러 이다.
	 * Exception을 다시 던지는 이유 --> 모든 ajax요청에 Error처리가 되어있음.
	 * WAS에 접속하기 전 Error가 발생하면 WS에서 처리하도록 되어있다.
	 * @param e
	 * @param request
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	protected void handleException(Exception e, HttpServletRequest request) throws Exception{
		StackTraceElement[] st = e.getStackTrace();
		StringBuffer sb = new StringBuffer();
		sb.append(new Date()).append("\r\n");
		sb.append(log.getName()).append("\r\n");
		for(StackTraceElement s: st){
			sb.append(s.toString()).append("\r\n");
		}
		log.error(sb.toString());
		//TODO 에러를 관리자 메일로 발송한다. 발송에 실패할 경우 DM 날린다.
		throw new Exception();
	}
}
