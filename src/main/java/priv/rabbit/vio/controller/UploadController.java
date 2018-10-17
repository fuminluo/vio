package priv.rabbit.vio.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import priv.rabbit.vio.common.ResultInfo;
import priv.rabbit.vio.entity.ProgressInfo;
import priv.rabbit.vio.service.UploadFileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;


/**
 * 上传文件控制类
 *
 * @author LuoFuMin
 * @data 2018/8/6
 */

@EnableAsync
@Controller
public class UploadController {

    @Autowired
    UploadFileService uploadFileService;

    @ApiOperation(value = "上传文件view", notes = "上传文件view")
    @GetMapping("/v1/web/upload-html")
    public ModelAndView uploadView() throws IOException {
        for (int i = 0; i < 20; i++) {
            uploadFileService.syncTest();
        }
        return new ModelAndView("upload");
    }

    @ApiOperation(value = "上传文件", notes = "上传文件")
    @PostMapping("/v1/web/upload")
    @ResponseBody
    public ResultInfo<?> upload(@RequestParam("file") MultipartFile multipartFile, Model model) throws IOException {
        File filedir = new File("D:/file/");
        //如果文件夹不存在则创建
        if (!filedir.exists() && !filedir.isDirectory()) {
            System.out.println("》》》不存在");
            System.out.println("创建成功" + filedir.mkdir());
        }
        System.out.println("线程名称 ：" + Thread.currentThread().getName());
        uploadFileService.pushFile(multipartFile);
        model.addAttribute("msg", "上传成功");
        return new ResultInfo(ResultInfo.SUCCESS, "上传成功");
    }

    /**
     * process 获取进度
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/v1/web/upload/progress")
    @ResponseBody
    public Object process(HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        return (ProgressInfo) request.getSession().getAttribute("proInfo");
    }

}
