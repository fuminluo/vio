package priv.rabbit.vio.config.uploadprogress;


import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import priv.rabbit.vio.entity.ProgressInfo;

/**
 * 长传进度监听器
 *
 * @author LuoFuMin
 * @data 2018/8/6
 */
@Component
public class UploadProgressListener implements ProgressListener {

    private static Logger LOG = LoggerFactory.getLogger(UploadProgressListener.class);

    private HttpSession session;

    public void setSession(HttpSession session) {
        this.session = session;
        ProgressInfo status = new ProgressInfo();
        session.setAttribute("progressInfo", status);
    }

    /**
     * pBytesRead 到目前为止读取文件的比特数 pContentLength 文件总大小 pItems 目前正在读取第几个文件
     */
    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        LOG.info(">>> 正在上传>>> 字节bytes =="+pBytesRead);
        ProgressInfo progressInfo = (ProgressInfo) session.getAttribute("progressInfo");
        progressInfo.setpBytesRead(pBytesRead);
        progressInfo.setpContentLength(pContentLength);
        progressInfo.setpItems(pItems);
    }
}
