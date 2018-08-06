package priv.rabbit.vio.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import priv.rabbit.vio.common.ResultInfo;
import priv.rabbit.vio.entity.ProgressInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
 * 上传文件控制类
 *
 * @author LuoFuMin
 * @data 2018/8/6
 */
@Controller
public class UploadController {

    @ApiOperation(value = "上传文件view", notes = "上传文件view")
    @GetMapping("/v1/web/upload-html")
    public ModelAndView uploadView() {
        return new ModelAndView("/upload");
    }

    @ApiOperation(value = "上传文件", notes = "上传文件")
    @PostMapping("/v1/web/upload")
    @ResponseBody
    public ResultInfo<?> upload(@RequestParam("file") MultipartFile multipartFile, Model model) throws IOException {
        System.out.println("name==" + multipartFile.getOriginalFilename());
        File file = new File("D:/file/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
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
