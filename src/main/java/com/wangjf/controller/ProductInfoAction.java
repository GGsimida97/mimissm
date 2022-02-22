package com.wangjf.controller;

import com.github.pagehelper.PageInfo;
import com.wangjf.pojo.ProductInfo;
import com.wangjf.pojo.vo.ProductInfoVo;
import com.wangjf.service.ProductInfoService;
import com.wangjf.service.ProductSaveService;
import com.wangjf.utils.FileNameUtil;
import javafx.beans.binding.ObjectExpression;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    //异步上传图片的文件名称
    String saveFileName = "";
    public static final int PAGE_SIZE = 5;
    @Autowired
    ProductInfoService productInfoService;
    @Autowired
    ProductSaveService productSaveService;

    //显示全部商品（不分页）
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request) {
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list", list);
        return "product";
    }

    //显示第一页的5条记录
    @RequestMapping("/split")
    public String split(HttpServletRequest request) {

        PageInfo info = null;
        Object vo = request.getSession().getAttribute("prodVo");
        if (vo != null) {
            //说明是带条件过来的
            info = productInfoService.selectConditionPage((ProductInfoVo) vo, PAGE_SIZE);
            request.getSession().removeAttribute("prodVo");
        } else {
            //得到第一页数据
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }


        request.setAttribute("info", info);
        return "product";
    }

    //Ajax分页处理（已将多条件查询整合）
    @ResponseBody
    @RequestMapping("/ajaxsplit")
    public void ajaxSplit(ProductInfoVo vo, HttpSession session) {
        //Integer page = Integer.valueOf(request.getParameter("page"));
        System.out.println(vo);
        PageInfo info = productInfoService.selectConditionPage(vo, PAGE_SIZE);
        session.setAttribute("info", info);
    }

    //异步Ajax文件上传处理
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage, HttpServletRequest request) {
        //提取生成文件名UUID+上传文件的后缀 .jpg .png
        saveFileName = FileNameUtil.getUUIDFileName() +
                FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到预将图片存储的路径
        String path = request.getServletContext().getRealPath("/image_big");

        try {
            //将服务器端得到的pimage图片对象转存
            pimage.transferTo(new File(path + File.separator + saveFileName));
            System.out.println(new File(path + File.separator + saveFileName));
            //D:\mimissm\target\mimissm-1.0\image_big\32365936ee6245b28ff2639ab63a38b9.png
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回给浏览器端一个已经封装了图片路径的json对象，响应ajax请求。实现在页面立即回显
        JSONObject object = new JSONObject();
        object.put("imgurl", saveFileName);
        return object.toString();
    }

    //新增商品信息提交
    @RequestMapping("/save")
    public String save(ProductInfo info, HttpServletRequest request) {

        //补充获取ProductInfo中缺少的数据（图片名称、当前日期）其他都会自动注入
        info.setpImage(saveFileName);
        info.setpDate(new Date());
        int nums = -1;
        try {
            //像这种更新数据的操作，建议都要加上try catch
            nums = productSaveService.saveProduct(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (nums > 0) {
            request.setAttribute("msg", "商品添加成功！");
        } else {
            request.setAttribute("msg", "商品添加失败！");
        }

        /**
         * 清空saveFileName变量中的内容
         * 目的：
         * 当提交表单数据后，将saveFileName变量中储存的内容清空，因为saveFileName中存过的值已经保存至info中
         * 点击“编辑”按钮后跳转至one.action，将info的信息保存至prod中
         * 在后续商品信息更新操作时，通过判断saveFileName是否为空
         * 若为空，则在update.jsp页面中利用隐藏表单域<input type="hidden" value="${prod.pImage}" name="pImage">取出已经存储的图片名称
         * 若不为空，则表明用户重新点击了“选择文件”按钮，实现图片异步ajax上传，则使用更新的图片名称
         *
         * */
        saveFileName = "";
        //返回至商品分页显示页面
        return "forward:/prod/split.action";
    }

    //编辑商品，实现商品信息回显至update.jsp页面
    @RequestMapping("/one")
    public String one(int pid, ProductInfoVo vo, Model model, HttpSession session) {
        ProductInfo info = productInfoService.editProduct(pid);
        model.addAttribute("prod", info);
        //将这些商品信息以及页码放入session中，更新处理结束后，进行分页时直接从session中读取商品信息和页码
        session.setAttribute("prodVo", vo);
        return "update";
    }

    //更新商品信息
    @RequestMapping("/update")
    public String update(ProductInfo info, HttpServletRequest request) {
        //判断saveFileName是否为空
        if (!saveFileName.equals("")) {
            //不为空，表明用户在更新商品信息时，更新了图片
            info.setpImage(saveFileName);
        }
        //完成更新商品信息操作
        int nums = -1;
        try {
            nums = productInfoService.updateProduct(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (nums > 0) {
            request.setAttribute("msg", "商品信息更新成功！");
        } else {
            request.setAttribute("msg", "商品信息更新失败！");
        }
        //处理完更新后，再次将aveFileName清空
        saveFileName = "";
        return "forward:/prod/split.action";
    }

    //删除单个商品
    @RequestMapping("/delete")
    public String delete(int pid, ProductInfoVo vo, HttpServletRequest request) {
        int nums = -1;
        try {
            nums = productInfoService.deleteProduct(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (nums > 0) {
            request.setAttribute("msg", "删除成功！");
            request.getSession().setAttribute("deleteProdVo", vo);
        } else {
            request.setAttribute("msg", "删除失败！");
        }
        /**
         * 由于现有的分页方法split()返回值是void
         * 我们需要重新定义一个可以接收返回值的分页方法
         * */
        return "forward:/prod/deleteSplitAjax.action";
    }

    //可以接收返回值的分页方法
    @ResponseBody
    @RequestMapping(value = "/deleteSplitAjax", produces = "text/html;charset=utf-8")
    public Object deleteSplitAjax(HttpServletRequest request) {
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("deleteProdVo");
        if (vo != null) {
            //表明这是带着条件过来的删除请求
            info = productInfoService.selectConditionPage((ProductInfoVo) vo, PAGE_SIZE);
        } else {
            //取第一页数据
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }

        /**
         * 将info放入session作用域中是因为
         * 单条删除操作完成后的ajax请求中的load函数 $("#table").load("http://localhost:8080/admin/product.jsp #table");
         * 若将info放到请求作用域中，会读不到数据
         * */
        request.getSession().setAttribute("info", info);
        return request.getAttribute("msg");
    }

    //批量删除
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String ids, HttpServletRequest request) {
        //将浏览器通过ajax请求传过来的字符串根据“逗号”拆分，并构成数值
        String[] ps = ids.split(",");

        try {
            int nums = productInfoService.deleteBatch(ps);
            if (nums > 0) {
                request.setAttribute("msg", "删除成功！");
            } else {
                request.setAttribute("msg", "删除失败！");
            }
        } catch (Exception e) {
            request.setAttribute("msg", "删除失败！");
        }
        return "forward:/prod/deleteSplitAjax.action";
    }

    //多条件查询（没有带分页）
    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo, HttpSession session) {
        List<ProductInfo> list = productInfoService.selectCondition(vo);
        session.setAttribute("list", list);
    }
}
