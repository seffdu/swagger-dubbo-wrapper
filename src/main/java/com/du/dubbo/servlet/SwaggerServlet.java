package com.du.dubbo.servlet;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.container.spring.SpringContainer;
import com.deepoove.swagger.dubbo.web.DubboHttpController;
import com.deepoove.swagger.dubbo.web.SwaggerDubboController;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SwaggerServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(SwaggerServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCors(resp);
        String uri = req.getRequestURI();
        if ("/swagger-dubbo/swagger.json".equals(uri)) {
            SwaggerDubboController controller = SpringContainer.getContext().getBean(SwaggerDubboController.class);
            ResponseEntity<String> responseEntity = controller.getApiList();
            sendJsonOfMessageResp(resp, responseEntity);
        } else {
            String[] params = uri.split("/");
            String interfaceClass = params.length > 2 ? params[2] : null;
            String methodName = params.length > 3 ? params[3] : null;
            String operationId = params.length > 4 ? params[4] : null;
            DubboHttpController controller = SpringContainer.getContext().getBean(DubboHttpController.class);
            ResponseEntity<String> responseEntity;
            try {
                responseEntity = controller.invokeDubbo(interfaceClass, methodName, operationId, req, resp);
            } catch (Exception e) {
                logger.error("jettyContainer invokeDubbo error", e);
                responseEntity = ResponseEntity.ok(e.getMessage());
            }
            sendJsonOfMessageResp(resp, responseEntity);
        }
    }

    private void addCors(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    private static void sendJsonOfMessageResp(HttpServletResponse response, ResponseEntity<String> responseEntity) throws IOException {
        //在IE10以下版本，必须设置成text/html，不然传到前台，ie浏览器弹出下载保存的对话框
        //getResponse().setContentType("text/json; charset=utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.setHeader("Cache-Control", "no-cache"); //取消浏览器缓存
        response.setCharacterEncoding("utf-8");
        response.setStatus(responseEntity.getStatusCodeValue());
        PrintWriter pw = response.getWriter();
        pw.print(responseEntity.getBody());
        pw.flush();
        pw.close();
    }
}
