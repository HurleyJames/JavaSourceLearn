package ac.hurley.springboot.controller;

import ac.hurley.springboot.model.LearnResource;
import ac.hurley.springboot.model.User;
import ac.hurley.springboot.service.LearnService;
import ac.hurley.util.StringUtil;
import com.github.pagehelper.PageInfo;
import org.apache.hadoop.util.ServletUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/learn")
public class LearnController {

    @Autowired
    private LearnService learnService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("")
    public String learn() {
        return "learn-resource";
    }

    /**
     * 登录操作
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if (!userName.equals("") && password != "") {
            User user = new User(userName, password);
            request.getSession().setAttribute("user", user);
            map.put("result", "1");
        } else {
            map.put("result", "0");
        }
        return map;
    }

    /**
     * 查询所有记录，以列表显示，并分页处理
     *
     * @param request
     * @param response
     * @throws JSONException
     */
    @RequestMapping(value = "/queryLearnList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public void queryLearnList(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String author = request.getParameter("author");
        String title = request.getParameter("title");
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("rows", rows);
        params.put("author", author);
        params.put("title", title);
        List<LearnResource> learnResourceList = learnService.queryLearnResourceList(params);
        PageInfo<LearnResource> pageInfo = new PageInfo<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rows", learnResourceList);
        // 查询出的总页数
        jsonObject.put("total", pageInfo.getPages());
        // 查询出的记录总数
        jsonObject.put("records", pageInfo.getTotal());
//        ServletUtil.createSuccessResponse(200, jsonObject, response);
    }

    /**
     * 添加课程
     *
     * @param request
     * @param response
     */
    public void addLearnResource(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String author = request.getParameter("author");
        String title = request.getParameter("title");
        String url = request.getParameter("url");
        if (StringUtil.isNull(author)) {
            jsonObject.put("message", "作者不能为空！");
            jsonObject.put("flag", false);
//            ServletUtil.createSuccessResponse(200, jsonObject, response);
            return;
        }
        if (StringUtil.isNull(title)) {
            jsonObject.put("message", "课程名称不能为空");
            jsonObject.put("flag", false);
//            ServletUtil.createSuccessResponse(200, jsonObject, response);
            return;
        }
        if (StringUtil.isNull(url)) {
            jsonObject.put("message", "地址不能为空！");
            jsonObject.put("flag", false);
//            ServletUtil.createSuccessResponse(200, jsonObject, response);
            return;
        }
        LearnResource learnResource = new LearnResource();
        learnResource.setAuthor(author);
        learnResource.setTitle(title);
        learnResource.setUrl(url);
        int index = learnService.add(learnResource);
        if (index > 0) {
            jsonObject.put("message", "课程信息添加成功！");
            jsonObject.put("flag", true);
        } else {
            jsonObject.put("message", "课程信息添加失败！");
            jsonObject.put("flag", false);
        }
//        ServletUtil.createSuccessResponse(200, jsonObject, response);
    }

    /**
     * 更新课程
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateLearnResource(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id");
        LearnResource learnResource = learnService.queryLearnResourceById(Long.valueOf(id));
        String author = request.getParameter("author");
        String title = request.getParameter("title");
        String url = request.getParameter("url");
        if (StringUtil.isNull(author)) {
            jsonObject.put("message", "作者不能为空！");
            jsonObject.put("flag", false);
//            ServletUtil.createSuccessResponse(200, jsonObject, response);
        }
        if (StringUtil.isNull(title)) {
            jsonObject.put("message", "课程名称不能为空");
            jsonObject.put("flag", false);
//            ServletUtil.createSuccessResponse(200, jsonObject, response);
        }
        if (StringUtil.isNull(title)) {
            jsonObject.put("message", "课程名称不能为空");
            jsonObject.put("flag", false);
//            ServletUtil.createSuccessResponse(200, jsonObject, response);
        }
        learnResource.setAuthor(author);
        learnResource.setTitle(title);
        learnResource.setUrl(url);
        int index = learnService.update(learnResource);
        System.out.println("修改结果=" + index);
        if (index > 0) {
            jsonObject.put("message", "课程信息修改成功！");
            jsonObject.put("flag", true);
        } else {
            jsonObject.put("message", "课程信息修改失败！");
            jsonObject.put("flag", false);
        }
//        ServletUtil.createSuccessResponse(200, jsonObject, response);
    }

    /**
     * 根据用户删除
     *
     * @param request
     * @param response
     * @throws JSONException
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        String ids = request.getParameter("ids");
        System.out.println("ids===" + ids);
        JSONObject jsonObject = new JSONObject();
        int index = learnService.deleteByIds(ids.split(","));
        if (index > 0) {
            jsonObject.put("message", "课程信息删除成功！");
            jsonObject.put("flag", true);
        } else {
            jsonObject.put("message", "课程信息删除失败！");
            jsonObject.put("flag", false);
        }
//        ServletUtil.createSuccessResponse(200, jsonObject, response);
    }
}