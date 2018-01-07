//package com.aptech.itblog.config;
//
//import com.aptech.itblog.model.CommonResponseBody;
//import org.springframework.core.MethodParameter;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
//@ControllerAdvice
//public class ResourceSizeAdvice implements ResponseBodyAdvice<Page<?>> {
//
//    @Override
//    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//        //Checks if this advice is applicable.
//        //In this case it applies to any endpoint which returns a page.
//        return Page.class.isAssignableFrom(returnType.getParameterType());
//    }
//
//    @Override
//    public Page<?> beforeBodyWrite(Page<?> page, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
//
//        HttpHeaders headers = serverHttpResponse.getHeaders();
//        headers.add("Access-Control-Expose-Headers", "Content-Range");
//        headers.add("Content-Range", String.valueOf(page.getTotalElements()));
//        return page;
//    }
//
//}
