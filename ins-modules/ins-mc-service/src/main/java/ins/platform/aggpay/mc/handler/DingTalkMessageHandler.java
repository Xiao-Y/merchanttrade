package ins.platform.aggpay.mc.handler;

import ins.platform.aggpay.common.util.template.DingTalkMsgTemplate;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import ins.platform.aggpay.mc.config.DingTalkPropertiesConfig;
import com.xiaoleilu.hutool.http.HttpUtil;
import com.xiaoleilu.hutool.util.StrUtil;

/**
 * @author lengleng
 * @date 2018/4/22
 * 发送钉钉消息逻辑
 */
@Slf4j
@Component
public class DingTalkMessageHandler {
    @Autowired
    private DingTalkPropertiesConfig dingTalkPropertiesConfig;

    /**
     * 业务处理
     *
     * @param text 消息
     */
    public boolean process(String text) {
        String webhook = dingTalkPropertiesConfig.getWebhook();
        if (StrUtil.isBlank(webhook)) {
            log.error("钉钉配置错误，webhook为空");
            return false;
        }

        DingTalkMsgTemplate dingTalkMsgTemplate = new DingTalkMsgTemplate();
        dingTalkMsgTemplate.setMsgtype("text");
        DingTalkMsgTemplate.TextBean textBean = new DingTalkMsgTemplate.TextBean();
        textBean.setContent(text);
        dingTalkMsgTemplate.setText(textBean);
        String result = HttpUtil.post(webhook, JSONObject.toJSONString(dingTalkMsgTemplate));
        log.info("钉钉提醒成功,报文响应:{}", result);
        return true;
    }

}
