package com.sample.skywalking.alarm.controller;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.sample.skywalking.alarm.dto.SwAlarmDTO;
import com.sample.skywalking.alarm.service.DingTalkAlarmService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class SwAlarmController {
	
	private final JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;
    
    @Autowired
    private DingTalkAlarmService dingTalkAlarmService;
    
    /**
     * 接收skywalking服务的告警通知并发送至邮箱
     */
    @PostMapping("/webhook/mail")
    public void receive(@RequestBody List<SwAlarmDTO> alarmList) {
        SimpleMailMessage message = new SimpleMailMessage();
        // 发送者邮箱
        message.setFrom(from);
        // 接收者邮箱
        message.setTo(from);
        // 主题
        message.setSubject("告警邮件");
        String content = getContent(alarmList);
        // 邮件内容
        message.setText(content);
        sender.send(message);
        log.info("告警邮件已发送...");
    }

    private String getContent(List<SwAlarmDTO> alarmList) {
        StringBuilder sb = new StringBuilder();
        for (SwAlarmDTO dto : alarmList) {
            sb.append("scopeId: ").append(dto.getScopeId())
                    .append("\nscope: ").append(dto.getScope())
                    .append("\n目标 Scope 的实体名称: ").append(dto.getName())
                    .append("\nScope 实体的 ID: ").append(dto.getId0())
                    .append("\nid1: ").append(dto.getId1())
                    .append("\n告警规则名称: ").append(dto.getRuleName())
                    .append("\n告警消息内容: ").append(dto.getAlarmMessage())
                    .append("\n告警时间: ").append(dto.getStartTime())
                    .append("\n\n---------------\n\n");
        }

        return sb.toString();
    }
    
    @PostMapping("/webhook/ding")
    public void alarm(@RequestBody List<SwAlarmDTO> alarmMessageDTOList) {
       log.info("收到告警信息: {}", JSON.toJSONString(alarmMessageDTOList));
       if (null != alarmMessageDTOList) {
           alarmMessageDTOList.forEach(e->dingTalkAlarmService.sendMessage(MessageFormat.format("-----来自SkyWalking的告警-----\n【名称】: {0}\n【消息】: {1}\n", e.getName(), e.getAlarmMessage())));
       }
    }

}
