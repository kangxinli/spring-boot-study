/*
 * Copyright (c) 2019-2029, Dreamlu 卢春梦 (596392912@qq.com & www.dreamlu.net).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sample.app.aspect;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.InputStreamSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.sample.app.aspect.util.ClassUtil;
import com.sample.app.aspect.util.JsonUtil;
import com.sample.app.aspect.util.StringUtil;
import com.sample.app.aspect.util.WebUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Spring boot controller自动日志
 *
 *
 */
@Slf4j
@Aspect
@Configuration
@RequiredArgsConstructor
public class RequestLogAspect {

    @SuppressWarnings("unchecked")
	@Around(
            // "execution(com.sample.app *(..)) && " +
			"(@within(org.springframework.stereotype.Controller) || " +
            "@within(org.springframework.web.bind.annotation.RestController))"
    )
    public Object aroundApi(ProceedingJoinPoint point) throws Throwable {

        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        Object[] args = point.getArgs();
        final Map<String, Object> paraMap = new HashMap<>(16);
        for (int i = 0; i < args.length; i++) {
            MethodParameter methodParam = ClassUtil.getMethodParameter(method, i);
            PathVariable pathVariable = methodParam.getParameterAnnotation(PathVariable.class);
            if (pathVariable != null) {
                continue;
            }
            RequestBody requestBody = methodParam.getParameterAnnotation(RequestBody.class);
            String parameterName = methodParam.getParameterName();
            Object value = args[i];
            if (requestBody != null) {
                if (value == null) {
                    paraMap.put(parameterName, null);
                } else if (ClassUtil.isPrimitiveOrWrapper(value.getClass())) {
                    paraMap.put(parameterName, value);
                } else {
                    paraMap.putAll(BeanMap.create(value));
                }
                continue;
            }
            if (value instanceof HttpServletRequest) {
                paraMap.putAll(((HttpServletRequest) value).getParameterMap());
                continue;
            } else if (value instanceof WebRequest) {
                paraMap.putAll(((WebRequest) value).getParameterMap());
                continue;
            } else if (value instanceof HttpServletResponse) {
                continue;
            } else if (value instanceof MultipartFile) {
                MultipartFile multipartFile = (MultipartFile) value;
                String name = multipartFile.getName();
                String fileName = multipartFile.getOriginalFilename();
                paraMap.put(name, fileName);
                continue;
            }
            RequestParam requestParam = methodParam.getParameterAnnotation(RequestParam.class);
            String paraName = parameterName;
            if (requestParam != null && StringUtil.isNotBlank(requestParam.value())) {
                paraName = requestParam.value();
            }
            if (value == null) {
                paraMap.put(paraName, null);
            } else if (ClassUtil.isPrimitiveOrWrapper(value.getClass())) {
                paraMap.put(paraName, value);
            } else if (value instanceof InputStream) {
                paraMap.put(paraName, "InputStream");
            } else if (value instanceof InputStreamSource) {
                paraMap.put(paraName, "InputStreamSource");
            } else if (canJsonSerialize(value)) {
                paraMap.put(paraName, value);
            } else {
                paraMap.put(paraName, "【注意】不能序列化为json");
            }
        }
        HttpServletRequest request = WebUtil.getRequest();
        String requestUrl = request.getRequestURI();
        String requestMethod = request.getMethod();

        StringBuilder beforeReqLog = new StringBuilder(300);
        List<Object> beforeReqArgs = new ArrayList<>();
        beforeReqLog.append("\n\n================  接口请求 开始  ================\n");
        beforeReqLog.append("===> 请求方法{}: 请求url{}");
        beforeReqArgs.add(requestMethod);
        beforeReqArgs.add(requestUrl);
        if (paraMap.isEmpty()) {
            beforeReqLog.append("\n");
        } else {
            beforeReqLog.append(" 请求参数: {}\n");
            beforeReqArgs.add(JsonUtil.toJson(paraMap));
        }
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            String headerValue = request.getHeader(headerName);
            beforeReqLog.append("===Headers===  {}: {}\n");
            beforeReqArgs.add(headerName);
            beforeReqArgs.add(headerValue);
        }
        beforeReqLog.append("================   接口请求 结束   ================\n");
        long startNs = System.nanoTime();
        log.info(beforeReqLog.toString(), beforeReqArgs.toArray());
        StringBuilder afterReqLog = new StringBuilder(200);
        List<Object> afterReqArgs = new ArrayList<>();
        afterReqLog.append("\n\n================  接口返回 开始  ================\n");
        try {
            Object result = point.proceed();
            afterReqLog.append("===结果===  {}\n");
            afterReqArgs.add(JsonUtil.toJson(result));
            return result;
        } finally {
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
            afterReqLog.append("<=== 请求方法{}: 请求url{} 耗时({} ms)\n");
            afterReqArgs.add(requestMethod);
            afterReqArgs.add(requestUrl);
            afterReqArgs.add(tookMs);
            afterReqLog.append("================  接口返回 结束   ================\n");
            log.info(afterReqLog.toString(), afterReqArgs.toArray());
        }
    }

    private boolean canJsonSerialize(Object value) {
        @SuppressWarnings("unused")
		byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
