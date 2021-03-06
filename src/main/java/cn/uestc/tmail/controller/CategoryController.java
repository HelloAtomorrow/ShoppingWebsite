package cn.uestc.tmail.controller;

import cn.uestc.tmail.pojo.Category;
import cn.uestc.tmail.util.ImageUtil;
import cn.uestc.tmail.pojo.UploadedImageFile;
import cn.uestc.tmail.service.CategoryService;
import cn.uestc.tmail.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/admin_category_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getCurrentLocation(), page.getEachPageDataNumber());
        List<Category> categories = categoryService.list();
        Integer total = (int) new PageInfo<>(categories).getTotal();
        page.setTotalDataNumber(total);
        model.addAttribute("categories", categories);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    @RequestMapping(value = "/admin_category_add")
    public String add(Category category, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
//        System.out.println(category.getId());
//        System.out.println(category.getName());
        categoryService.add(category);
//        System.out.println(category.getId());
//        System.out.println(category.getName());
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, category.getId() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
//        System.out.println(uploadedImageFile);
//        System.out.println(uploadedImageFile.getImage());
//        System.out.println(file);
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage image = ImageUtil.change2jpg(file);
        ImageIO.write(image, "jpg", file);
        return "redirect:admin_category_list";
    }

    @RequestMapping("admin_category_delete")
    public String delete(Integer id, HttpSession session) {
        categoryService.delete(id);
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id + ".jpg");
        file.delete();
        return "redirect:admin_category_list";
    }

    @RequestMapping("admin_category_edit")
    public String edit(Integer id, Model model) {
        Category category = categoryService.get(id);
        model.addAttribute("category", category);
        return "admin/editCategory";
    }

    @RequestMapping("admin_category_update")
    public String update(Category category, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.update(category);
        MultipartFile image = uploadedImageFile.getImage();
        if (null != image && !image.isEmpty()) {
            File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder, category.getId() + ".jpg");
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }
        return "redirect:admin_category_list";
    }
}
